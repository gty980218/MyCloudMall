package org.skyrain.cloud.mall.categoryproducts.service;

import com.github.pagehelper.PageInfo;
import org.skyrain.cloud.mall.categoryproducts.model.request.AddCategoryRequest;
import org.skyrain.cloud.mall.categoryproducts.model.request.UpdateCategoryRequest;
import org.skyrain.cloud.mall.categoryproducts.model.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    int add(AddCategoryRequest addCategoryRequest);

    int update(UpdateCategoryRequest updateCategoryRequest);

    int delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listForUser();
}
