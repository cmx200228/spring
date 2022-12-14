package com.imooc.imooc_mall.model.dao;

import com.imooc.imooc_mall.model.pojo.Product;
import com.imooc.imooc_mall.model.query.ProductListQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 陈蒙欣
 * @date 2022/11/29
 */
@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product row);

    int insertSelective(Product row);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKey(Product row);

    /**
     * 用于调整删除后数据库id自增不连续
     * @param id 目录id
     */
    int updateAfterDelete(Integer id);
    int updateAfter(@Param(value="id") Integer id);
    Product selectByName(String name);

    int batchUpdateSellStatus(@Param("ids") Integer[] ids , @Param("sellStatus") Integer sellStatus);

    List<Product> selectListForAdmin();

    List<Product> selectList(@Param("query")ProductListQuery query);
}
