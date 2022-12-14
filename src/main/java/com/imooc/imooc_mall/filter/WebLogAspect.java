package com.imooc.imooc_mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 陈蒙欣
 * @date 2022/11/29 22:39
 * 打印请求和响应信息
 */
@Aspect
@Component
public class WebLogAspect {
    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    @Pointcut("execution(public * com.imooc.imooc_mall.controller.*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        //收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        logger.info("URL : {}" , request.getRequestURL());
        logger.info("HTTP_METHOD : {}" , request.getMethod());
        logger.info("IP : {}" , request.getRemoteAddr());
        logger.info("CLASS_METHOD : {}" , joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        logger.info("ARGS + {}" , joinPoint.getArgs());
    }

    @AfterReturning(returning = "res" , pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        //处理完请求打印内容
        logger.info("RESPONSE : {}" , new ObjectMapper().writeValueAsString(res));

    }
}
