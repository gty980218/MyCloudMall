package org.skyrain.cloud.mall.cartorder.feign;

import org.skyrain.cloud.mall.categoryproducts.model.pojo.Product;
import org.skyrain.cloud.mall.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "my-cloud-mall-category-products")
public interface ProductFeignClient {

    @PostMapping("/admin/product/getPro")
    Product getProduct(@RequestParam Integer id);

    @PostMapping("/admin/product/updateStock")
    int updateStock(@RequestParam("productId") Integer productId,@RequestParam("stock") Integer stock);
}
