package org.skyrain.cloud.mall.categoryproducts.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.skyrain.cloud.mall.categoryproducts.model.dao.CategoryMapper;
import org.skyrain.cloud.mall.categoryproducts.model.pojo.Category;
import org.skyrain.cloud.mall.categoryproducts.model.request.AddCategoryRequest;
import org.skyrain.cloud.mall.categoryproducts.model.request.UpdateCategoryRequest;
import org.skyrain.cloud.mall.categoryproducts.model.vo.CategoryVO;
import org.skyrain.cloud.mall.categoryproducts.service.CategoryService;
import org.skyrain.cloud.mall.common.exception.CloudMallException;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service(value = "CategoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public int add(AddCategoryRequest addCategoryRequest){
        if(addCategoryRequest.getType()>3){
            throw new CloudMallException(CloudMallExceptionEnum.TYPE_ERROR);
        }
        Category category = categoryMapper.selectByName(addCategoryRequest.getName());
        if(category!=null){
            throw new CloudMallException(CloudMallExceptionEnum.CATEGORY_EXISTED);
        }
        category=new Category();
        BeanUtils.copyProperties(addCategoryRequest,category);
        return categoryMapper.insertSelective(category);
    }

    @Override
    public int update(UpdateCategoryRequest updateCategoryRequest){
        Category category = categoryMapper.selectByPrimaryKey(updateCategoryRequest.getId());
        if(category==null){
            throw new CloudMallException(CloudMallExceptionEnum.CATEGORY_NOT_EXISTED);
        }
        BeanUtils.copyProperties(updateCategoryRequest,category);
        return categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public int delete(Integer id){
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize, "type, order_num");
        List<Category> categoryList = categoryMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    public List<CategoryVO> listForUser(){
        List<Category> categories=categoryMapper.selectByParentId(0);
        List<CategoryVO> categoryVOS=new ArrayList<>();
        for(Category category:categories){
            CategoryVO categoryVO=new CategoryVO();
            BeanUtils.copyProperties(category,categoryVO);
            List<Category> subCategories=categoryMapper.selectByParentId(category.getId());
            List<CategoryVO> subCategoryVOS=new ArrayList<>();
            for(Category subCategory:subCategories){
                CategoryVO subCategoryVO=new CategoryVO();
                BeanUtils.copyProperties(subCategory,subCategoryVO);
                List<Category> ssubCategories=categoryMapper.selectByParentId(subCategory.getId());
                List<CategoryVO> ssubCategoryVOS=new ArrayList<>();
                for(Category ssubCategory:ssubCategories){
                    CategoryVO ssubCateogryVO=new CategoryVO();
                    BeanUtils.copyProperties(ssubCategory,ssubCateogryVO);
                    ssubCategoryVOS.add(ssubCateogryVO);
                }
                subCategoryVO.setChildCategory(ssubCategoryVOS);
                subCategoryVOS.add(subCategoryVO);
            }
            categoryVO.setChildCategory(subCategoryVOS);
            categoryVOS.add(categoryVO);
        }
        return categoryVOS;
    }

}
