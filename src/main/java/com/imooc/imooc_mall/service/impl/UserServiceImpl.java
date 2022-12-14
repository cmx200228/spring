package com.imooc.imooc_mall.service.impl;

import com.imooc.imooc_mall.exception.ImoocMallException;
import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;
import com.imooc.imooc_mall.model.dao.UserMapper;
import com.imooc.imooc_mall.model.pojo.User;
import com.imooc.imooc_mall.service.UserService;
import com.imooc.imooc_mall.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author 陈蒙欣
 * @date 2022/11/29 16:30
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public void register(String username, String password) throws ImoocMallException {
        User result = userMapper.selectByName(username);
        if (result != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        User user = new User();
        user.setUsername(username);
        try {
            user.setPassword(MD5Utils.getMD5String(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setCreateTime(new Date());
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }
    }

    /**
     * 用户登录
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return 当前用户
     * @throws ImoocMallException 如果未查询到数据，统一返回密码错误
     */
    @Override
    public User login(String username, String password) throws ImoocMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5String(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    /**
     * 更新用户签名
     * @param user 用户
     * @throws ImoocMallException 更新失败
     */
    @Override
    public void updateInformation(User user) throws ImoocMallException {
        int update = userMapper.updateByPrimaryKeySelective(user);
        if (update != 1) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 检查是否是管理员
     * @param user 要登陆的对象
     * @return 是管理员true ， 不是false
     */
    @Override
    public boolean checkAdmin(User user) {
        //1是普通用户，2是管理员
        return user.getRole().equals(2);
    }
}
