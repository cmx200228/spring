package com.imooc.imooc_mall.model.dao;

import com.imooc.imooc_mall.model.pojo.OrderItem;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 陈蒙欣
 * @date 2022/11/29
 */
@Repository
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem row);

    int insertSelective(OrderItem row);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem row);

    int updateByPrimaryKey(OrderItem row);
}
