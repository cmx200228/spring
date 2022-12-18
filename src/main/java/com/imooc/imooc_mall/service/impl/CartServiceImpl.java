package com.imooc.imooc_mall.service.impl;

import com.imooc.imooc_mall.common.Constant;
import com.imooc.imooc_mall.exception.ImoocMallException;
import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;
import com.imooc.imooc_mall.model.dao.CartMapper;
import com.imooc.imooc_mall.model.dao.ProductMapper;
import com.imooc.imooc_mall.model.pojo.Cart;
import com.imooc.imooc_mall.model.pojo.Product;
import com.imooc.imooc_mall.model.vo.CartVO;
import com.imooc.imooc_mall.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈蒙欣
 * @date 2022/12/18 21:02
 */
@Service("cartService")
public class CartServiceImpl implements CartService {

    @Resource
    ProductMapper productMapper;

    @Resource
    CartMapper cartMapper;

    /**
     * 添加购物车
     *
     * @param userId    用户id
     * @param productId 商品id
     * @param count     商品数量
     * @return 前端显示的购物车对象
     */
    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品不在购物车里，需要新增一个记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            //这个商品已经在购物车里了，数量相加
            Cart cartNew = new Cart();
            cartNew.setQuantity(cart.getQuantity() + count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return null;
    }

    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
        //判断商品是否存在，商品是否上架
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
        }

        //判断商品库存
        if (count > product.getStock()) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }
    }
}
