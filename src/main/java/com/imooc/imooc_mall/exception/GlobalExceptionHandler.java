package com.imooc.imooc_mall.exception;

import com.imooc.imooc_mall.common.ApiRestResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈蒙欣
 * @date 2022/12/8 20:15
 * 统一处理异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default exception ," , e);
        return ApiRestResponse.error(ImoocMallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(ImoocMallException.class)
    @ResponseBody
    public Object handleException(ImoocMallException e) {
        log.error("ImoocMall exception ," , e);
        return ApiRestResponse.error(e.getCode() , e.getMsg());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException ," , e);
        return handleBindingRequest(e.getBindingResult());
    }

    private ApiRestResponse handleBindingRequest(BindingResult result) {
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            allErrors.forEach(error -> {
                String msg = error.getDefaultMessage();
                list.add(msg);
            });
        }
        if (list.isEmpty()) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAM_ERROR);
        }

        return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(),
                list.toString());
    }
}
