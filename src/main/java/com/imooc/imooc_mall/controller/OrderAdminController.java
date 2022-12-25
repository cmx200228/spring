package com.imooc.imooc_mall.controller;

import com.imooc.imooc_mall.common.ApiRestResponse;
import com.imooc.imooc_mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台管理订单控制器
 * @author 陈蒙欣
 * @date 2022/12/23 22:09
 */
@RestController
public class OrderAdminController {

    @Resource
    OrderService orderService;

    @GetMapping("/admin/order/list")
    @ApiOperation("后台订单列表")
    public ApiRestResponse selectOrderList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return ApiRestResponse.success(orderService.listAllForAdmin(pageNum, pageSize));
    }

    @PostMapping("/admin/order/delivered")
    @ApiOperation("管理员发货")
    public ApiRestResponse delivered(@RequestParam String orderNo) {
        orderService.deliver(orderNo);
        return ApiRestResponse.success();
    }

    @PostMapping("/order/finish")
    @ApiOperation("完结订单")
    public ApiRestResponse finish(@RequestParam String orderNo) {
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }
}
