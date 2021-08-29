package org.skyrain.cloud.mall.cartorder.controller;

import com.github.pagehelper.PageInfo;
import org.skyrain.cloud.mall.cartorder.service.OrderService;
import org.skyrain.cloud.mall.common.common.ApiRestResponse;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/order")
public class OrderAdminController {

    @Resource
    OrderService orderService;

    @GetMapping("/list")
    public ApiRestResponse listForAdmin(Integer pageNum,Integer pageSize){
        PageInfo pageInfo = orderService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/delivered")
    public ApiRestResponse delivered(String orderNo){
        int res = orderService.delivered(orderNo);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.ORDER_DELIVERED_FAILED);
        }
        return ApiRestResponse.success();
    }

}
