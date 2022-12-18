package com.imooc.imooc_mall.controller;

import com.imooc.imooc_mall.common.ApiRestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈蒙欣
 * @date 2022/12/18 17:58
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId , @RequestParam Integer count){
        return ApiRestResponse.success();
    }
}
