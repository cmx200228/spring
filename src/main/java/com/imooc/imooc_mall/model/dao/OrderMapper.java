package com.imooc.imooc_mall.model.dao;

import com.imooc.imooc_mall.model.pojo.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 陈蒙欣
 * @date 2022/11/29
 */
@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order row);

    int insertSelective(Order row);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order row);

    int updateByPrimaryKey(Order row);

    Order selectByOrderNo(String orderNo);

    List<Order> selectByUserId(Integer userId);

    List<Order> selectAllForAdmin();
}
