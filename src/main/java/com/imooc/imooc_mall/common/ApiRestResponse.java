package com.imooc.imooc_mall.common;

import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;

/**
 * 通用返回对象
 *
 * @author 陈蒙欣
 * @date 2022/11/30 17:21
 */
public class ApiRestResponse<T> {
    private Integer status;

    private String msg;

    private T date;

    private static final int OK_CODE = 10000;

    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String msg, T date) {
        this.status = status;
        this.msg = msg;
        this.date = date;
    }

    public ApiRestResponse(Integer status) {
        this.status = status;
    }

    public ApiRestResponse(String msg) {
        this.msg = msg;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }

    /**
     * static<T>表明是一个静态的泛型方法
     * @param <T> 传入的参数
     * @return 统一返回对象
     */
    public static <T> ApiRestResponse<T> success() {
        return new ApiRestResponse<>();
    }

    public static <T> ApiRestResponse<T> success(T res) {
        ApiRestResponse<T> apiRestResponse = new ApiRestResponse<>();
        apiRestResponse.setDate(res);
        return apiRestResponse;
    }

    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse<>(code , msg);
    }

    public static <T> ApiRestResponse<T> error(ImoocMallExceptionEnum exception) {
        return new ApiRestResponse<>(exception.getCode() , exception.getMsg());
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                '}';
    }
}
