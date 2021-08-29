package org.skyrain.cloud.mall.cartorder.service;

import org.skyrain.cloud.mall.cartorder.model.vo.CartListVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    int add(Integer productId, Integer quantity);

    List<CartListVO> list();

    int update(Integer productId, Integer quantity);

    int delete(Integer productId);

    int selectOne(Integer productId, Integer selected);

    int selectAll(Integer selected);
}
