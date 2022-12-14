package com.imooc.imooc_mall.controller;

import com.imooc.imooc_mall.common.ApiRestResponse;
import com.imooc.imooc_mall.common.Constant;
import com.imooc.imooc_mall.exception.ImoocMallException;
import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;
import com.imooc.imooc_mall.model.pojo.User;
import com.imooc.imooc_mall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author 陈蒙欣
 * @date 2022/11/29 16:24
 * 用户控制器
 */
@Controller
public class UserController {
    private static final int PASSWORD_LENGTH = 8;
    @Resource
    UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public User getUser() {
        return userService.getUser();
    }

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @return 通用返回对象
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("username") String username, @RequestParam("password") String password) throws ImoocMallException {
        if (username.isEmpty()) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        if (password.isEmpty()) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        if (password.length() < PASSWORD_LENGTH) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.PASSWORD_TOO_SORT);
        }

        userService.register(username, password);
        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws ImoocMallException {
        if (username.isEmpty()) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        if (password.isEmpty()) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(username, password);
        //不将用户敏感信息返回，保存用户信息时不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.IMOOC_MALL_USER, user);
        return ApiRestResponse.success(user);
    }

    /**
     * 更新用户签名
     *
     * @param session   session对象
     * @param signature 用户输入的签名
     * @return 统一返回对象
     * @throws ImoocMallException 用户未登录异常
     */
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam String signature) throws ImoocMallException {
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    /**
     * 退出登录
     * @param session 当前会话
     * @return 统一返回对象
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.IMOOC_MALL_USER);
        return ApiRestResponse.success();
    }

    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @param session 当前会话
     * @return 统一返回接口
     * @throws ImoocMallException 可能存在的异常情况
     */
    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws ImoocMallException {
        if (username.isEmpty()) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        if (password.isEmpty()) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(username, password);
        if (userService.checkAdmin(user)) {
            //是管理员，执行操作
            //不将用户敏感信息返回，保存用户信息时不保存密码
            user.setPassword(null);
            session.setAttribute(Constant.IMOOC_MALL_USER, user);
            return ApiRestResponse.success(user);
        }else {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }

    }
}
