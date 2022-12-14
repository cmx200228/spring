package com.imooc.imooc_mall.model.dao;

import com.imooc.imooc_mall.model.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cmx
 */
@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category row);

    int insertSelective(Category row);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category row);

    int updateByPrimaryKey(Category row);

    Category selectByName(String name);

    /**
     * 用于调整删除后数据库id自增不连续
     * @param id 目录id
     */
    int updateAfterDelete(Integer id);
    int updateAfter(@Param(value="id") Integer id);

    List<Category> selectList();

    List<Category> selectCategoriesByParentId(Integer parentId);
}
