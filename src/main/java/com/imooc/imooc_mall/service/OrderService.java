package com.imooc.imooc_mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.imooc_mall.model.request.CreateOrderRequest;
import com.imooc.imooc_mall.model.vo.OrderVO;

/**
 * @author 陈蒙欣
 * @date 2022/12/19 22:22
 */
public interface OrderService {

    /**
     * 创建订单
     * @param createOrderRequest 订单请求
     * @return 订单号
     */
    String create(CreateOrderRequest createOrderRequest);

    /**
     * 订单详情
     * @param orderNo 订单号
     * @return 前端展示订单详情对象
     */
    OrderVO detail(String orderNo);

    /**
     * 前台订单列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 订单列表
     */
    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    /**
     * 前台取消订单
     * @param orderNo 订单号
     */
    void cancel(String orderNo);

    /**
     * 生成支付二维码
     * @param orderNo 订单号
     * @return 二维码地址
     */
    String QRCode(String orderNo);

    /**
     * 后台订单列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 订单列表
     */
    PageInfo listAllForAdmin(Integer pageNum, Integer pageSize);

    /**
     * 支付订单
     * @param orderNo 订单号
     */
    void pay(String orderNo);

    /**
     * 发货
     * @param orderNo 订单号
     */
    void deliver(String orderNo);

    /**
     * 完成订单
     * @param orderNo 订单号
     */
    void finish(String orderNo);
}
