package org.skyrain.cloud.mall.categoryproducts.controller;

import org.skyrain.cloud.mall.categoryproducts.model.dao.CategoryMapper;
import org.skyrain.cloud.mall.categoryproducts.model.vo.CategoryVO;
import org.skyrain.cloud.mall.categoryproducts.service.CategoryService;
import org.skyrain.cloud.mall.common.common.ApiRestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CategoryController {
    @Resource
    CategoryService categoryService;

    @GetMapping("/category/list")
    @ResponseBody
    public ApiRestResponse listForUser(){
        List<CategoryVO> categoryVOList= categoryService.listForUser();
        return ApiRestResponse.success(categoryVOList);
    }
}
