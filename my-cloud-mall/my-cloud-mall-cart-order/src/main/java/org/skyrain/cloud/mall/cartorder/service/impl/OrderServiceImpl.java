package org.skyrain.cloud.mall.cartorder.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import org.skyrain.cloud.mall.cartorder.feign.ProductFeignClient;
import org.skyrain.cloud.mall.cartorder.feign.UserFeignClient;
import org.skyrain.cloud.mall.cartorder.model.dao.CartMapper;
import org.skyrain.cloud.mall.cartorder.model.dao.OrderItemMapper;
import org.skyrain.cloud.mall.cartorder.model.dao.OrderMapper;
import org.skyrain.cloud.mall.cartorder.model.pojo.Cart;
import org.skyrain.cloud.mall.cartorder.model.pojo.Order;
import org.skyrain.cloud.mall.cartorder.model.pojo.OrderItem;
import org.skyrain.cloud.mall.cartorder.model.request.OrderRequest;
import org.skyrain.cloud.mall.cartorder.model.vo.OrderDetail;
import org.skyrain.cloud.mall.cartorder.model.vo.OrderItemVO;
import org.skyrain.cloud.mall.cartorder.service.OrderService;
import org.skyrain.cloud.mall.categoryproducts.model.pojo.Product;
import org.skyrain.cloud.mall.common.common.Constant;
import org.skyrain.cloud.mall.common.exception.CloudMallException;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.skyrain.cloud.mall.common.utils.QRCodeGenerator;
import org.skyrain.cloud.mall.user.model.pojo.User;
import org.skyrain.cloud.mall.util.OrderCodeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "OrderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    UserFeignClient userFeignClient;

    @Resource
    ProductFeignClient productFeignClient;

    @Resource
    CartMapper cartMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    @Value("${file.upload.ip}")
    String ip;

    @Value("${file.upload.port}")
    String port;

    @Value("${file.upload.dir}")
    String FILE_UPLOAD_DIR;

    @Override
    @Transactional
    public String create(OrderRequest orderRequest){
        User user = userFeignClient.getCurUser();

        //????????????ID
        List<Cart> carts = cartMapper.selectCartSelected(user.getId(), Constant.SELECTED);
        if(carts==null || carts.size()==0){
            throw new CloudMallException(CloudMallExceptionEnum.CART_EMPTY);
        }
        //???????????????????????????????????????
        //??????????????????????????????????????????
        for(Cart cart:carts){
            checkCart(cart);
        }
        //???????????????????????????????????????????????????
        List<OrderItem> orderItems=cartToOrderItem(carts);
        //??????????????????????????????item??????
        for(Cart cart:carts){
            productFeignClient.updateStock(cart.getProductId(),cart.getQuantity());
            cartMapper.deleteByProductIdAndUserId(cart.getUserId(),cart.getProductId());
        }
        //?????????
        //???????????????????????????????????????

        //????????????
        String orderNo= OrderCodeFactory.getOrderCode((long)user.getId());

        //????????????????????????????????????


        Integer totalPrice=0;
        for (OrderItem orderItem:orderItems){
            orderItem.setOrderNo(orderNo);
            orderItemMapper.insertSelective(orderItem);
            totalPrice+=orderItem.getTotalPrice();
        }
        Order order=new Order();
        order.setUserId(user.getId());
        order.setOrderNo(orderNo);
        order.setReceiverAddress(orderRequest.getReceiverAddress());
        order.setReceiverMobile(orderRequest.getReceiverMobile());
        order.setReceiverName(orderRequest.getReceiverName());
        order.setTotalPrice(totalPrice);

        orderMapper.insertSelective(order);
        //?????????Order???
        //???????????????????????????order_item???
        //???????????????
        return orderNo;
    }

    public void checkCart(Cart cart){
        Product product = productFeignClient.getProduct(cart.getProductId());
        if(product==null){
            throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        if(product.getStatus()== Constant.NOTONSALE){
            throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_NOT_ON_SALE);
        }
        if(product.getStock()<cart.getQuantity()){
            throw new CloudMallException(CloudMallExceptionEnum.STOCK_NOT_ENOUGH);
        }
    }

    public List<OrderItem> cartToOrderItem(List<Cart> carts){
        List<OrderItem> orderItems=new ArrayList<>();
        for(Cart cart:carts){
            Product product = productFeignClient.getProduct(cart.getProductId());
            OrderItem orderItem=new OrderItem();
            orderItem.setProductId(cart.getProductId());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setProductImg(product.getImage());
            orderItem.setProductName(product.getName());
            orderItem.setTotalPrice(orderItem.getUnitPrice()*orderItem.getQuantity());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    @Override
    public OrderDetail detail(String orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_NOT_EXISTED);
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderNo(orderNo);
        if(orderItems==null || orderItems.size()==0){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_ITEM_EMPTY);
        }
        OrderDetail orderDetail=new OrderDetail();
        BeanUtils.copyProperties(order,orderDetail);
        orderDetail.setOrderStatusName(Constant.OrderStatusEnum.valueOf(orderDetail.getOrderStatus()).getStatus());
        List<OrderItemVO> orderItemVOS=new ArrayList<>();
        for(OrderItem orderItem:orderItems){
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem,orderItemVO);
            orderItemVOS.add(orderItemVO);
        }
        orderDetail.setOrderItemVOList(orderItemVOS);
        return orderDetail;
    }

    @Override
    public PageInfo list(Integer pageNum, Integer pageSize){
        User user = userFeignClient.getCurUser();
        PageHelper.startPage(pageNum,pageSize,"create_time");
        List<String> orderNos=orderMapper.selectOrderNoByUserId(user.getId());
        List<OrderDetail> orderDetails=new ArrayList<>();
        for(String orderNo:orderNos){
            OrderDetail orderDetail = detail(orderNo);
            orderDetails.add(orderDetail);
        }
        PageInfo pageInfo=new PageInfo(orderDetails);
        return pageInfo;
    }

    @Override
    public int cancel(String orderNo){
        User user = userFeignClient.getCurUser();
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_NOT_EXISTED);
        }
        if(order.getUserId()!=user.getId()){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_USER_NOT_MATCH);
        }
        order.setOrderStatus(Constant.OrderStatusEnum.CANCELED.getCode());
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public String qrcode(String orderNo){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String address = ip + ":" + port;
        String payUrl = "http://" + address + "/cart-order/pay?orderNo=" + orderNo;
        try {
            QRCodeGenerator
                    .generateQRCodeImage(payUrl, 350, 350,
                            FILE_UPLOAD_DIR + orderNo + ".png");
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pngAddress = "http://" + address + "/cart-order/images/" + orderNo + ".png";
        return pngAddress;
    }

    @Override
    public int pay(String orderNo){
        User user = userFeignClient.getCurUser();
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_NOT_EXISTED);
        }
        if(order.getUserId()!=user.getId()){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_USER_NOT_MATCH);
        }
        order.setOrderStatus(Constant.OrderStatusEnum.PAID.getCode());
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"create_time");
        List<String> orderNos=orderMapper.selectOrders();
        List<OrderDetail> orderDetails=new ArrayList<>();
        for(String orderNo:orderNos){
            OrderDetail orderDetail = detail(orderNo);
            orderDetails.add(orderDetail);
        }
        PageInfo pageInfo=new PageInfo(orderDetails);
        return pageInfo;
    }

    @Override
    public int delivered(String orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_NOT_EXISTED);
        }
        if(order.getOrderStatus()!=Constant.OrderStatusEnum.PAID.getCode()){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_NOT_PAID);
        }
        order.setOrderStatus(Constant.OrderStatusEnum.SHIPPED.getCode());
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public int finish(String orderNo){
        User user = userFeignClient.getCurUser();
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_NOT_EXISTED);
        }
        if(user.getRole()==Constant.Role.USER.getRole() && user.getId()!=order.getUserId()){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_USER_NOT_MATCH);
        }
        if(order.getOrderStatus()!=Constant.OrderStatusEnum.SHIPPED.getCode()){
            throw new CloudMallException(CloudMallExceptionEnum.ORDER_NOT_DELIVERED);
        }
        order.setOrderStatus(Constant.OrderStatusEnum.FINISHED.getCode());
        return orderMapper.updateByPrimaryKeySelective(order);
    }

}
