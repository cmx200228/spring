package com.imooc.imooc_mall.filter;

import com.imooc.imooc_mall.common.Constant;
import com.imooc.imooc_mall.model.pojo.User;
import com.imooc.imooc_mall.service.UserService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户过滤器
 * @author 陈蒙欣
 * @date 2022/12/10 22:24
 */
public class UserFilter implements Filter {

    public static User user;

    @Resource
    UserService userService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        servletResponse.setContentType("application/json; charset=UTF-8");
        user = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (user == null) {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.println("{\n" +
                    "    \"status\": 10007,\n" +
                    "    \"msg\": \"未登录\",\n" +
                    "    \"date\": null\n" +
                    "}");
            out.flush();
            out.close();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
