package com.imooc.imooc_mall.model.dao;

import com.imooc.imooc_mall.model.pojo.Cart;
import org.springframework.stereotype.Repository;

/**
 * @author cmx
 */
@Repository
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart row);

    int insertSelective(Cart row);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart row);

    int updateByPrimaryKey(Cart row);
}
