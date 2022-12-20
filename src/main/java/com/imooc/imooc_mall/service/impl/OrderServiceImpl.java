package com.imooc.imooc_mall.service.impl;

import com.imooc.imooc_mall.common.Constant;
import com.imooc.imooc_mall.exception.ImoocMallException;
import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;
import com.imooc.imooc_mall.filter.UserFilter;
import com.imooc.imooc_mall.model.dao.CartMapper;
import com.imooc.imooc_mall.model.dao.OrderItemMapper;
import com.imooc.imooc_mall.model.dao.OrderMapper;
import com.imooc.imooc_mall.model.dao.ProductMapper;
import com.imooc.imooc_mall.model.pojo.Order;
import com.imooc.imooc_mall.model.pojo.OrderItem;
import com.imooc.imooc_mall.model.pojo.Product;
import com.imooc.imooc_mall.model.request.CreateOrderRequest;
import com.imooc.imooc_mall.model.vo.CartVO;
import com.imooc.imooc_mall.service.CartService;
import com.imooc.imooc_mall.service.OrderService;
import com.imooc.imooc_mall.util.OrderCodeFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 陈蒙欣
 * @date 2022/12/19 22:22
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    CartService cartService;

    @Resource
    ProductMapper productMapper;

    @Resource
    CartMapper cartMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    /**
     * 创建订单
     *
     * @param createOrderRequest 订单请求
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateOrderRequest createOrderRequest) {
        //拿到用户id
        Integer userId = UserFilter.user.getId();
        //从购物车中拿到已勾选的商品信息
        List<CartVO> cartVOList = cartService.list(userId);
        ArrayList<CartVO> cartVOSTemp = new ArrayList<>();
        cartVOList.forEach(cartVO -> {
                    if (cartVO.getSelected().equals(Constant.Cart.CHECKED)) {
                        cartVOSTemp.add(cartVO);
                    }
                });
        //如果没有购物车或购物车已勾选为空，抛出异常
        if (CollectionUtils.isEmpty(cartVOSTemp)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CART_EMPTY);
        }
        //判断商品状态、库存、是否上下架
        validSaleStatusAndStock(cartVOSTemp);
        //把购物车中已勾选的商品信息，转换为订单商品item信息
        List<OrderItem> orderItems = cartVOListToOrderItemList(cartVOSTemp);
        //扣库存
        orderItems.forEach(orderItem -> {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            int stock = product.getStock() - orderItem.getQuantity();
            if (stock < 0) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
            }
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        });
        //删除购物车中已勾选的商品信息
        cleanCart(cartVOSTemp);
        //生成订单
        Order order = new Order();
        //生成订单号，有独立的规则
        String orderCode = OrderCodeFactory.getOrderCode(Long.valueOf(userId));
        order.setOrderNo(orderCode);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice(orderItems));
        order.setReceiverName(createOrderRequest.getReceiverName());
        order.setReceiverMobile(createOrderRequest.getReceiverMobile());
        order.setReceiverAddress(createOrderRequest.getReceiverAddress());
        order.setOrderStatus(Constant.OrderStatusEum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(1);

        //写入order表
        orderMapper.insertSelective(order);
        //循环保存每一个商品到order_item表

        orderItems.forEach(orderItem -> {
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insertSelective(orderItem);

        });
        //返回结果
        return orderCode;
    }

    /**
     * 计算总价
     *
     * @param orderItems 订单商品列表
     * @return 总价
     */
    private Integer totalPrice(List<OrderItem> orderItems) {
        //保证线程安全
        AtomicReference<Integer> totalPrice = new AtomicReference<>(0);
        orderItems.forEach(orderItem ->
                totalPrice.updateAndGet(v -> v + orderItem.getTotalPrice())
        );
        return totalPrice.get();
    }

    /**
     * 删除购物车中已勾选的商品信息
     *
     * @param cartVOSTemp 购物车中已勾选的商品信息
     */
    private void cleanCart(ArrayList<CartVO> cartVOSTemp) {
        cartVOSTemp.forEach(cartVO->
            cartMapper.deleteByPrimaryKey(cartVO.getId())
        );
    }

    /**
     * 把购物车中已勾选的商品信息，转换为订单商品item信息
     *
     * @param cartVOSTemp 购物车中已勾选的商品信息
     * @return 订单商品item信息
     */
    private List<OrderItem> cartVOListToOrderItemList(ArrayList<CartVO> cartVOSTemp) {
        List<OrderItem> orderItemList = new ArrayList<>();
        cartVOSTemp.forEach(cartVO -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            orderItem.setProductName(cartVO.getProductName());
            orderItem.setProductImg(cartVO.getProductImage());
            orderItem.setUnitPrice(cartVO.getPrice());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setTotalPrice(cartVO.getTotalPrice());
            orderItemList.add(orderItem);
        });
        return orderItemList;
    }

    /**
     * 判断商品状态、库存、是否上下架
     * @param cartVOSTemp 购物车中已勾选的商品信息
     */
    private void validSaleStatusAndStock(ArrayList<CartVO> cartVOSTemp) {
        cartVOSTemp.forEach(cartVO -> {
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            //判断商品是否存在
            if (product == null) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
            }
            //判断商品是否上下架
            if (!product.getStatus().equals(Constant.SaleStatus.SALE)) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
            }
            //判断商品库存
            if (product.getStock() < cartVO.getQuantity()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
            }
        });

    }
}
