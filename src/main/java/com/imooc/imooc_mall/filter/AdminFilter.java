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
 *验证是不是管理员
 * @author 陈蒙欣
 * @date 2022/12/10 22:24
 */
public class AdminFilter implements Filter {

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
        User user = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
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
        boolean admin = userService.checkAdmin(user);
        if (admin){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.println("{\n" +
                    "    \"status\": 10009,\n" +
                    "    \"msg\": \"无管理员权限\",\n" +
                    "    \"date\": null\n" +
                    "}");
            out.flush();
            out.close();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
