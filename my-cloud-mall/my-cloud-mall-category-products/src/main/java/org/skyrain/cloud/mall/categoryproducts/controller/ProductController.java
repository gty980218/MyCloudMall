package org.skyrain.cloud.mall.categoryproducts.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.skyrain.cloud.mall.categoryproducts.model.pojo.Product;
import org.skyrain.cloud.mall.categoryproducts.model.quiry.ListRequest;
import org.skyrain.cloud.mall.categoryproducts.service.ProductService;
import org.skyrain.cloud.mall.common.common.ApiRestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Resource
    ProductService productService;

    @GetMapping("/detail")
    @ResponseBody
    public ApiRestResponse detail(Integer id){
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    @GetMapping("/list")
    @ResponseBody
    public ApiRestResponse listForCustomer(ListRequest listRequest){
        PageInfo pageInfo=productService.listForCustomer(listRequest.getOrderBy(), listRequest.getCategoryId(), listRequest.getKeyword(), listRequest.getPageNum(), listRequest.getPageSize());
        return ApiRestResponse.success(pageInfo);
    }

}
