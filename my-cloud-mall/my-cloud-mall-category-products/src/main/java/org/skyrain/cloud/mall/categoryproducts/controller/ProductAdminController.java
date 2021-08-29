package org.skyrain.cloud.mall.categoryproducts.controller;

import com.github.pagehelper.PageInfo;
import org.skyrain.cloud.mall.categoryproducts.model.pojo.Product;
import org.skyrain.cloud.mall.categoryproducts.model.request.AddProductRequest;
import org.skyrain.cloud.mall.categoryproducts.model.request.UpdateCategoryRequest;
import org.skyrain.cloud.mall.categoryproducts.model.request.UpdateProductRequest;
import org.skyrain.cloud.mall.categoryproducts.service.ProductService;
import org.skyrain.cloud.mall.common.common.ApiRestResponse;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/product")
public class ProductAdminController {
    @Resource
    ProductService productService;

    @PostMapping("/add")
    @ResponseBody
    public ApiRestResponse add(@RequestBody AddProductRequest addProductRequest){
        int res= productService.add(addProductRequest);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.INSERT_FAILED);
        }
        return ApiRestResponse.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public ApiRestResponse update(@RequestBody UpdateProductRequest updateProductRequest){
        int res= productService.update(updateProductRequest);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.INSERT_FAILED);
        }
        return ApiRestResponse.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public ApiRestResponse delete(Integer id){
        int res= productService.delete(id);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.DELETE_FAILED);
        }
        return ApiRestResponse.success();
    }

    @PostMapping("/batch")
    @ResponseBody
    @Transactional
    public ApiRestResponse batch(@RequestParam("ids") String ids,@RequestParam("sellStatus") Integer status){
        String[] sids=ids.split(",");
        int res= productService.batch(sids,status);
        if(res==0){
            return ApiRestResponse.error(CloudMallExceptionEnum.BATCH_ERROR);
        }
        return ApiRestResponse.success();
    }

    @GetMapping("/list")
    @ResponseBody
    public ApiRestResponse listForAdmin(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        PageInfo pageInfo=productService.listForAdmin(pageNum,pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/isStockEnough")
    @ResponseBody
    public boolean isStockEnough(@RequestParam Integer id,@RequestParam Integer count){
        return productService.isStockEnough(id, count);
    }

    @PostMapping("/idProductExisted")
    @ResponseBody
    public boolean idProductExisted(@RequestParam Integer id){
        return productService.idProductExisted(id);
    }

    @PostMapping("/isOnSale")
    @ResponseBody
    public boolean isOnSale(@RequestParam Integer id){
        return productService.isOnSale(id);
    }

    @PostMapping("/getPro")
    @ResponseBody
    public Product getProduct(@RequestParam Integer id){
        return productService.getProduct(id);
    }

    @PostMapping("/updateStock")
    @ResponseBody
    public int updateStock(@RequestParam("productId") Integer productId,@RequestParam("stock") Integer stock){
        return productService.updateStock(productId, stock);
    }
}
