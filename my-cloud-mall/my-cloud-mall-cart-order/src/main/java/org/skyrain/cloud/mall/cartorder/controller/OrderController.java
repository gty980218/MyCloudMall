package org.skyrain.cloud.mall.cartorder.controller;

import com.github.pagehelper.PageInfo;
import org.skyrain.cloud.mall.cartorder.model.request.OrderRequest;
import org.skyrain.cloud.mall.cartorder.model.vo.OrderDetail;
import org.skyrain.cloud.mall.cartorder.service.OrderService;
import org.skyrain.cloud.mall.common.common.ApiRestResponse;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @PostMapping("/create")
    public ApiRestResponse create(@RequestBody OrderRequest orderRequest){
        String res = orderService.create(orderRequest);
        return ApiRestResponse.success(res);
    }

    @PostMapping("/detail")
    public ApiRestResponse detail(String orderNo){
        OrderDetail detail = orderService.detail(orderNo);
        return ApiRestResponse.success(detail);
    }

    @PostMapping("/list")
    public ApiRestResponse list(Integer pageNum,Integer pageSize){
        PageInfo list = orderService.list(pageNum, pageSize);
        return ApiRestResponse.success(list);
    }

    @PostMapping("/cancel")
    public ApiRestResponse cancel(String orderNo){
        int res = orderService.cancel(orderNo);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.ORDER_CANCEL_FAILED);
        }
        return ApiRestResponse.success();
    }

    @PostMapping("/qrcode")
    public ApiRestResponse qrcode(String orderNo){
        String qrcode = orderService.qrcode(orderNo);
        return ApiRestResponse.success(qrcode);
    }

    @PostMapping("/pay")
    public ApiRestResponse pay(String orderNo){
        int res = orderService.pay(orderNo);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.ORDER_CANCEL_FAILED);
        }
        return ApiRestResponse.success();
    }

    @PostMapping("/finish")
    public ApiRestResponse finish(String orderNo){
        int res = orderService.finish(orderNo);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.ORDER_FINISHED_FAILED);
        }
        return ApiRestResponse.success();
    }

}
