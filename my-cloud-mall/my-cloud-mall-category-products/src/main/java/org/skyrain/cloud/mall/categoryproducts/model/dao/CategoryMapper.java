package org.skyrain.cloud.mall.categoryproducts.model.dao;

import org.skyrain.cloud.mall.categoryproducts.model.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Category selectByName(String name);

    List<Category> selectListForAdmin();

    List<Category> selectByParentId(Integer parentId);
}