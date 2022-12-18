package com.imooc.imooc_mall.service;

import com.imooc.imooc_mall.model.vo.CartVO;

import java.util.List;

/**
 * 购物车Service
 * @author 陈蒙欣
 * @date 2022/12/18 20:43
 */
public interface CartService {

    /**
     * 添加购物车
     * @param userId 用户id
     * @param productId 商品id
     * @param count 商品数量
     * @return 前端显示的购物车对象
     */
    List<CartVO> add(Integer userId , Integer productId, Integer count);
}
