package com.imooc.imooc_mall.model.dao;

import com.imooc.imooc_mall.model.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author cmx
 */
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(Integer id);

    /**
     * 只更新不为空的字段
     * @param row 要更新的对象
     * @return 影响的行数
     */
    int updateByPrimaryKeySelective(User row);

    /**
     * 更新全部字段
     * @param row 要更新的对象
     * @return 影响到行数
     */
    int updateByPrimaryKey(User row);

    User selectByName(String username);

    User selectLogin(@Param("username") String username ,
                     @Param("password") String password);
}
