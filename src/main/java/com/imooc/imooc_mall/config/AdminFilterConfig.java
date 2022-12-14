package com.imooc.imooc_mall.config;

import com.imooc.imooc_mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * adminFilter配置
 *
 * @author 陈蒙欣
 * @date 2022/12/10 22:40
 */
@Configuration
public class AdminFilterConfig {

    @Bean
    public AdminFilter adminFilter() {
        return new AdminFilter();
    }

    @Bean(name = "adminFilterCfg")
    public FilterRegistrationBean<AdminFilter> adminFilterConfig() {
        FilterRegistrationBean<AdminFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(adminFilter());
        filterRegistrationBean.addUrlPatterns("/admin/category/*");
        filterRegistrationBean.addUrlPatterns("/admin/product/*");
        filterRegistrationBean.addUrlPatterns("/admin/order/*");
        filterRegistrationBean.setName("adminFilterCfg");
        return filterRegistrationBean;
    }
}
