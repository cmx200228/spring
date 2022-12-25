package com.imooc.imooc_mall.controller;

import com.imooc.imooc_mall.common.ApiRestResponse;
import com.imooc.imooc_mall.model.request.CreateOrderRequest;
import com.imooc.imooc_mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("前台订单详情")
    @GetMapping("/order/detail")
    public ApiRestResponse detail(@RequestParam String orderNo) {
        return ApiRestResponse.success(orderService.detail(orderNo));
    }

    @ApiOperation("前台订单列表")
    @GetMapping("/order/list")
    public ApiRestResponse list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return ApiRestResponse.success(orderService.listForCustomer(pageNum, pageSize));
    }

    @ApiOperation("前台取消订单")
    @PostMapping ("/order/cancel")
    public ApiRestResponse cancel(@RequestParam String orderNo) {
        orderService.cancel(orderNo);
        return ApiRestResponse.success();
    }

    @ApiOperation("生成支付二维码")
    @PostMapping ("/order/qrcode")
    public ApiRestResponse qrCode(@RequestParam String orderNo) {
        return ApiRestResponse.success( orderService.QRCode(orderNo));
    }

    @GetMapping("/order/pay")
    @ApiOperation("支付订单")
    public ApiRestResponse pay(@RequestParam String orderNo) {
        orderService.pay(orderNo);
        return ApiRestResponse.success();
    }
}
