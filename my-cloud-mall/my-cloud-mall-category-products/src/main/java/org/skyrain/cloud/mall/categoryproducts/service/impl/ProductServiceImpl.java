package org.skyrain.cloud.mall.categoryproducts.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.skyrain.cloud.mall.categoryproducts.model.dao.ProductMapper;
import org.skyrain.cloud.mall.categoryproducts.model.pojo.Category;
import org.skyrain.cloud.mall.categoryproducts.model.pojo.Product;
import org.skyrain.cloud.mall.categoryproducts.model.quiry.ListQuiry;
import org.skyrain.cloud.mall.categoryproducts.model.request.AddProductRequest;
import org.skyrain.cloud.mall.categoryproducts.model.request.UpdateCategoryRequest;
import org.skyrain.cloud.mall.categoryproducts.model.request.UpdateProductRequest;
import org.skyrain.cloud.mall.categoryproducts.service.ProductService;
import org.skyrain.cloud.mall.common.common.Constant;
import org.skyrain.cloud.mall.common.exception.CloudMallException;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service(value = "ProductService")
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductMapper productMapper;

    @Override
    public int add(AddProductRequest addProductRequest){
        Product product=productMapper.selectByName(addProductRequest.getName());
        if(product!=null){
            throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_EXISTED);
        }
        if(addProductRequest.getPrice()<0){
            throw new CloudMallException(CloudMallExceptionEnum.PRICE_ERROR);
        }
        if(addProductRequest.getStock()<0){
            throw new CloudMallException(CloudMallExceptionEnum.STOCK_ERROR);
        }
        product=new Product();
        BeanUtils.copyProperties(addProductRequest,product);
        return productMapper.insertSelective(product);
    }

    @Override
    public int update(UpdateProductRequest updateProductRequest){
        Product product=productMapper.selectByPrimaryKey(updateProductRequest.getId());
        if(product==null){
            throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        if(updateProductRequest.getPrice()!=null && updateProductRequest.getPrice()<0){
            throw new CloudMallException(CloudMallExceptionEnum.PRICE_ERROR);
        }
        if(updateProductRequest.getStock()!=null && updateProductRequest.getStock()<0){
            throw new CloudMallException(CloudMallExceptionEnum.STOCK_ERROR);
        }
        BeanUtils.copyProperties(updateProductRequest,product);
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int delete(Integer id){
        return productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batch(String[] ids, Integer status){
        int res=0;
        for(String id:ids){
            Product product=productMapper.selectByPrimaryKey(Integer.parseInt(id));
            if(product==null){
                throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_NOT_EXISTED);
            }
            product.setStatus(status);
            productMapper.updateByPrimaryKeySelective(product);
            res++;
        }
        return res;
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"update_time");
        List<Product> products = productMapper.listForAdmin();
        PageInfo pageInfo=new PageInfo(products);
        return pageInfo;
    }

    @Override
    public Product detail(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        if(product==null){
            throw new CloudMallException(CloudMallExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        return product;
    }

    @Override
    public PageInfo listForCustomer(String orderBy, Integer categoryId, String keyword, Integer pageNum, Integer pageSize){
        pageNum=pageNum==null?1:pageNum;
        pageSize=pageSize==null?10:pageSize;
        List<Integer> ids=null;
        if(categoryId!=null){
            ids=new ArrayList<>();
            ids.add(categoryId);
            int index=0;
            int size=ids.size();
            while(index<size) {
                List<Integer> list = productMapper.selectParentIdById(ids.get(index));
                for (Integer i : list) {
                    ids.add(i);
                }
                size = ids.size();
                index++;
            }
        }
        if(orderBy==null){
            PageHelper.startPage(pageNum,pageSize);
        }else{
            PageHelper.startPage(pageNum,pageSize,orderBy);
        }
        ListQuiry listQuiry=new ListQuiry();
        listQuiry.setIds(ids);
        listQuiry.setKeyword(keyword);
        if(keyword!=null){
            String newKeyword="%"+keyword+"%";
            listQuiry.setKeyword(newKeyword);
        }
        List<Product> products = productMapper.listForCustomer(listQuiry);
        return new PageInfo(products);
    }

    @Override
    public boolean isStockEnough(Integer id, Integer count){
        Product product = productMapper.selectByPrimaryKey(id);
        return product.getStock()>=count?true:false;
    }

    @Override
    public boolean idProductExisted(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        return product==null?false:true;
    }

    @Override
    public boolean isOnSale(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        return product.getStatus()== Constant.SELECTED?true:false;
    }

    @Override
    public Product getProduct(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    @Override
    public int updateProduct(Product product){
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int updateStock(Integer productId, Integer stock){
        Product product = productMapper.selectByPrimaryKey(productId);
        product.setStock(product.getStock()-stock);
        return productMapper.updateByPrimaryKeySelective(product);
    }
}
