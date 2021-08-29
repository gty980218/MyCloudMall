package org.skyrain.cloud.mall.categoryproducts.model.dao;

import org.apache.ibatis.annotations.Param;
import org.skyrain.cloud.mall.categoryproducts.model.pojo.Product;
import org.skyrain.cloud.mall.categoryproducts.model.quiry.ListQuiry;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product selectByName(String name);

    List<Product> listForAdmin();

    List<Integer> selectParentIdById(Integer id);

    List<Product> listForCustomer(@Param("listQuiry") ListQuiry listQuiry);

}