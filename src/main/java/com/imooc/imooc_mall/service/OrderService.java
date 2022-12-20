package com.imooc.imooc_mall.service;

import com.imooc.imooc_mall.model.request.CreateOrderRequest;

/**
 * @author 陈蒙欣
 * @date 2022/12/19 22:22
 */
public interface OrderService {

    /**
     * 创建订单
     * @param createOrderRequest 订单请求
     */
    String create(CreateOrderRequest createOrderRequest);
}
