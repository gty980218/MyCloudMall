package org.skyrain.cloud.mall.cartorder.controller;

import org.skyrain.cloud.mall.cartorder.model.vo.CartListVO;
import org.skyrain.cloud.mall.cartorder.service.CartService;
import org.skyrain.cloud.mall.common.common.ApiRestResponse;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    CartService cartService;

    @PostMapping("/list")
    public ApiRestResponse list(){
        List<CartListVO> list = cartService.list();
        return ApiRestResponse.success(list);
    }

    @PostMapping("/add")
    public ApiRestResponse add(Integer productId,Integer quantity){
        int res = cartService.add(productId, quantity);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.INSERT_FAILED);
        }
        return list();
    }

    @PostMapping("/update")
    public ApiRestResponse update(Integer productId,Integer quantity){
        int res = cartService.update(productId, quantity);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.UPDATE_FAILED);
        }
        return list();
    }

    @PostMapping("/delete")
    public ApiRestResponse delete(Integer productId){
        int res = cartService.delete(productId);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.DELETE_FAILED);
        }
        return list();
    }

    @PostMapping("/select")
    public ApiRestResponse selectOne(Integer productId,Integer selected){
        int res = cartService.selectOne(productId,selected);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.UPDATE_FAILED);
        }
        return list();
    }

    @PostMapping("/selectAll")
    public ApiRestResponse selectAll(Integer selected){
        cartService.selectAll(selected);
        return list();
    }



}
