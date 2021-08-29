package org.skyrain.cloud.mall.categoryproducts.service;

import com.github.pagehelper.PageInfo;
import org.skyrain.cloud.mall.categoryproducts.model.pojo.Product;
import org.skyrain.cloud.mall.categoryproducts.model.request.AddProductRequest;
import org.skyrain.cloud.mall.categoryproducts.model.request.UpdateCategoryRequest;
import org.skyrain.cloud.mall.categoryproducts.model.request.UpdateProductRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    int add(AddProductRequest addProductRequest);

    int update(UpdateProductRequest updateProductRequest);

    int delete(Integer id);

    int batch(String[] ids, Integer status);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo listForCustomer(String orderBy, Integer categoryId, String keyword, Integer pageNum, Integer pageSize);

    boolean isStockEnough(Integer id, Integer count);

    boolean idProductExisted(Integer id);

    boolean isOnSale(Integer id);

    Product getProduct(Integer id);

    int updateProduct(Product product);


    int updateStock(Integer productId, Integer stock);
}
