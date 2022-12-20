package com.imooc.imooc_mall.controller;

import com.imooc.imooc_mall.common.ApiRestResponse;
import com.imooc.imooc_mall.filter.UserFilter;
import com.imooc.imooc_mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 陈蒙欣
 * @date 2022/12/18 17:58
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    CartService cartService;

    @ApiOperation("购物车列表")
    @GetMapping("/list")
    public ApiRestResponse list(){
        //内部获取用户id，防止横向越权（即其他用户非法查看）
        return ApiRestResponse.success(cartService.list(UserFilter.user.getId()));
    }

    @ApiOperation("添加商品到购物车")
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId , @RequestParam Integer count){
        return ApiRestResponse.success(cartService.add(UserFilter.user.getId(), productId, count));
    }

    @ApiOperation("更新购物车")
    @PostMapping("/update")
    public ApiRestResponse update(@RequestParam Integer productId , @RequestParam Integer count){
        return ApiRestResponse.success(cartService.update(UserFilter.user.getId(), productId, count));
    }

    @ApiOperation("删除购物车")
    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam Integer productId){
        //更新、删除都不应该从前端传回用户id和购物车id，因为用户可以通过修改前端代码来实现横向越权，前端传回的数据默认不可信
        return ApiRestResponse.success(cartService.delete(UserFilter.user.getId(), productId));
    }

    @ApiOperation("选中/不选中购物车中的某件商品")
    @PostMapping("/select")
    public ApiRestResponse select(@RequestParam Integer productId , @RequestParam Integer selected){
        return ApiRestResponse.success(cartService.selectOrNot(UserFilter.user.getId(), productId , selected));
    }

    @ApiOperation("全选中/全不选中购物车中的某件商品")
    @PostMapping("/selectAll")
    public ApiRestResponse select( @RequestParam Integer selected){
        return ApiRestResponse.success(cartService.selectAllOrNot(UserFilter.user.getId() , selected));
    }
}
