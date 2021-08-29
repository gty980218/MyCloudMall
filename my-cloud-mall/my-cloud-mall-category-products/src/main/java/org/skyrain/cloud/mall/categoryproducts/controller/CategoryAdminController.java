package org.skyrain.cloud.mall.categoryproducts.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.skyrain.cloud.mall.categoryproducts.model.pojo.Category;
import org.skyrain.cloud.mall.categoryproducts.model.request.AddCategoryRequest;
import org.skyrain.cloud.mall.categoryproducts.model.request.UpdateCategoryRequest;
import org.skyrain.cloud.mall.categoryproducts.service.CategoryService;
import org.skyrain.cloud.mall.common.common.ApiRestResponse;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
@RequestMapping("/admin/category")
public class CategoryAdminController {
    @Resource
    CategoryService categoryService;

    @PostMapping("/add")
    @ResponseBody
    public ApiRestResponse add(@RequestBody AddCategoryRequest addCategoryRequest){
        int res=categoryService.add(addCategoryRequest);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.INSERT_FAILED);
        }
        return ApiRestResponse.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public ApiRestResponse update(@RequestBody UpdateCategoryRequest updateCategoryRequest){
        int res=categoryService.update(updateCategoryRequest);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.UPDATE_FAILED);
        }
        return ApiRestResponse.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public ApiRestResponse delete(Integer id){
        int res=categoryService.delete(id);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.DELETE_FAILED);
        }
        return ApiRestResponse.success();
    }

    @GetMapping("/list")
    @ResponseBody
    public ApiRestResponse listForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo pageInfo=categoryService.listForAdmin(pageNum,pageSize);
        return ApiRestResponse.success(pageInfo);
    }

}
