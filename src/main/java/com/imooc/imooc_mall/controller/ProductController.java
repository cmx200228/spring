package com.imooc.imooc_mall.controller;

import com.imooc.imooc_mall.common.ApiRestResponse;
import com.imooc.imooc_mall.model.request.ProductListRequest;
import com.imooc.imooc_mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 前台商品管理
 * @author 陈蒙欣
 * @date 2022/12/13 21:56
 */
@RestController
public class ProductController {

    @Resource
    ProductService productService;

    @ApiOperation("商品详情")
    @GetMapping("/product/detail")
    public ApiRestResponse detail(@RequestParam Integer id) {
        return ApiRestResponse.success(productService.detail(id));
    }

    @ApiOperation("商品列表")
    @GetMapping("/product/list")
    public ApiRestResponse listProduct(ProductListRequest productListRequest) {
        return ApiRestResponse.success(productService.listForCustomer(productListRequest));
    }
}
