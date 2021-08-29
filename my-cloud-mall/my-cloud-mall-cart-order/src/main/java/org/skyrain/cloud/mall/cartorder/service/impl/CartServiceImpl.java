package org.skyrain.cloud.mall.cartorder.service.impl;

import com.netflix.discovery.converters.Auto;
import org.skyrain.cloud.mall.cartorder.feign.ProductFeignClient;
import org.skyrain.cloud.mall.cartorder.feign.UserFeignClient;
import org.skyrain.cloud.mall.cartorder.model.dao.CartMapper;
import org.skyrain.cloud.mall.cartorder.model.pojo.Cart;
import org.skyrain.cloud.mall.cartorder.model.vo.CartListVO;
import org.skyrain.cloud.mall.cartorder.service.CartService;
import org.skyrain.cloud.mall.common.common.Constant;
import org.skyrain.cloud.mall.common.exception.CloudMallException;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.skyrain.cloud.mall.user.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "CartService")
public class CartServiceImpl implements CartService {
    @Resource
    UserFeignClient userFeignClient;

    @Resource
    ProductFeignClient productFeignClient;

    @Resource
    CartMapper cartMapper;

    @Override
    public int add(Integer productId, Integer quantity){
        if(productFeignClient.getProduct(productId)==null){
            throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        User user=userFeignClient.getCurUser();
        Cart cart = cartMapper.selectByUserIdAndProductId(user.getId(), productId);
        if(cart==null){
            cart=new Cart();
            cart.setQuantity(quantity);
            cart.setUserId( user.getId());
            cart.setProductId(productId);
            cart.setSelected(Constant.SELECTED);
            return cartMapper.insertSelective(cart);
        }else{
            return 0;
        }
    }

    @Override
    public List<CartListVO> list(){
        User user = userFeignClient.getCurUser();
        List<CartListVO> cartListVOS = cartMapper.cartList(user.getId());
        for(CartListVO c:cartListVOS){
            c.setTotalPrice(c.getPrice()*c.getQuantity());
        }
        return cartListVOS;
    }

    @Override
    public int update(Integer productId, Integer quantity){
        User user = userFeignClient.getCurUser();
        Cart cart = cartMapper.selectByUserIdAndProductId(user.getId(), productId);
        if(cart==null){
            throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        cart.setQuantity(cart.getQuantity()+quantity);
        return cartMapper.updateByPrimaryKeySelective(cart);
    }

    @Override
    public int delete(Integer productId){
        User user = userFeignClient.getCurUser();
        Cart cart = cartMapper.selectByUserIdAndProductId(user.getId(), productId);
        if(cart==null){
            throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        return cartMapper.deleteByProductIdAndUserId(user.getId(),productId);
    }

    @Override
    public int selectOne(Integer productId, Integer selected){
        User user = userFeignClient.getCurUser();
        Cart cart = cartMapper.selectByUserIdAndProductId(user.getId(), productId);
        if(cart==null){
            throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        cart.setSelected(selected);
        return cartMapper.updateByPrimaryKeySelective(cart);
    }

    @Override
    public int selectAll(Integer selected){
        User user = userFeignClient.getCurUser();
        return cartMapper.updateSelectAll(user.getId(),selected);
    }
}
