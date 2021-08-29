package org.skyrain.cloud.mall.cartorder.model.dao;

import org.skyrain.cloud.mall.cartorder.model.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByOrderNo(String orderNo);

    List<String> selectOrderNoByUserId(Integer userId);

    List<String> selectOrders();
}