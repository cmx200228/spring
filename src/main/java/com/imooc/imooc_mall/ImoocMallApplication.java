package com.imooc.imooc_mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 *
 * @author 陈蒙欣
 * @date 2022/12/7
 */
@EnableCaching
@EnableOpenApi
@SpringBootApplication
@MapperScan(basePackages = "com.imooc.imooc_mall.model.dao")
public class ImoocMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImoocMallApplication.class, args);
    }

}
