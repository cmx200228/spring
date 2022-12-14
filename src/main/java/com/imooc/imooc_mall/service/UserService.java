package com.imooc.imooc_mall.service;

import com.imooc.imooc_mall.exception.ImoocMallException;
import com.imooc.imooc_mall.model.pojo.User;

/**
 * @author 陈蒙欣
 * @date 2022/11/29 16:29
 */
public interface UserService {
    /**
     * 获取用户
     * @return 用户对象
     */
    User getUser();

    /**
     * 注册用户
     * @param username 用户名
     * @param password 密码
     * @throws ImoocMallException 自定义异常
     */
    void register(String username, String password) throws ImoocMallException;

    User login(String username, String password) throws ImoocMallException;

    void updateInformation(User user) throws ImoocMallException;

    boolean checkAdmin(User user);
}
