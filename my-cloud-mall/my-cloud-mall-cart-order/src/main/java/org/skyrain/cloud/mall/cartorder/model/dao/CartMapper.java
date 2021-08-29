package org.skyrain.cloud.mall.cartorder.model.dao;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.skyrain.cloud.mall.cartorder.model.pojo.Cart;
import org.skyrain.cloud.mall.cartorder.model.vo.CartListVO;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId")Integer productId);

    List<CartListVO> cartList(@Param("userId") Integer userId);

    int deleteByProductIdAndUserId(@Param("userId") Integer userId, @Param("productId")Integer productId);

    int updateSelectAll(@Param("userId") Integer userId,@Param("selected") Integer selected);

    List<Cart> selectCartSelected(@Param("userId") Integer userId,@Param("selected") Integer selected);

}