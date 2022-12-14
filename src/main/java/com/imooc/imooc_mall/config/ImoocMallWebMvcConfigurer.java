package com.imooc.imooc_mall.config;

import com.imooc.imooc_mall.common.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 *
 * @author 陈蒙欣
 * @date 2022/12/10
 */

@Configuration
@EnableWebMvc
public class ImoocMallWebMvcConfigurer implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);

        registry.addResourceHandler("/images/**").addResourceLocations("file:"
                + Constant.FILE_UPLOAD_DIR);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:/swagger-ui/index.html");
    }

    /**
     * 这个是跨域配置 不需要的可以不配
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
