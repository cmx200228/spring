package com.imooc.imooc_mall.controller;

import com.imooc.imooc_mall.common.ApiRestResponse;
import com.imooc.imooc_mall.model.request.CreateOrderRequest;
import com.imooc.imooc_mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单控制器
 * @author 陈蒙欣
 * @date 2022/12/19 22:06
 */
@RestController
public class OrderController {

    @Resource
    OrderService orderService;

    @ApiOperation("创建订单")
    @PostMapping("/order/create")
    public ApiRestResponse create(@RequestBody CreateOrderRequest createOrderRequest) {
        return ApiRestResponse.success(orderService.create(createOrderRequest));
    }
}
