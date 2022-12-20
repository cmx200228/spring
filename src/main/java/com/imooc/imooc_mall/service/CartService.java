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

    /**
     * 显示购物车
     * @param userId 用户id
     * @return 前端显示的购物车对象
     */
    List<CartVO> list(Integer userId);

    /**
     * 更新购物车
     * @param userId 用户id
     * @param productId 商品id
     * @param count 商品数量
     * @return 前端显示的购物车对象
     */
    List<CartVO> update(Integer userId , Integer productId, Integer count);

    /**
     * 删除购物车
     * @param userId 用户id
     * @param productId 商品id
     * @return 前端显示的购物车对象
     */
    List<CartVO> delete(Integer userId , Integer productId);

    /**
     * 选中/不选中购物车某件商品
     * @param userId 用户id
     * @param productId 商品id
     * @param selected 选中状态
     * @return 前端显示的购物车对象
     */
    List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected);

    List<CartVO> selectAllOrNot(Integer userId, Integer selected);
}
