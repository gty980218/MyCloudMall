package org.skyrain.cloud.mall.cartorder.service;

import com.github.pagehelper.PageInfo;
import org.skyrain.cloud.mall.cartorder.model.request.OrderRequest;
import org.skyrain.cloud.mall.cartorder.model.vo.OrderDetail;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    String create(OrderRequest orderRequest);

    OrderDetail detail(String orderNo);

    PageInfo list(Integer pageNum, Integer pageSize);

    int cancel(String orderNo);

    String qrcode(String orderNo);

    int pay(String orderNo);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    int delivered(String orderNo);

    int finish(String orderNo);
}
