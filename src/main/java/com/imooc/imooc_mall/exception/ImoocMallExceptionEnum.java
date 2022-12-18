package com.imooc.imooc_mall.exception;

/**
 * 异常枚举
 * @author 陈蒙欣
 * @date 2022/11/30 21:50
 */
public enum ImoocMallExceptionEnum {
    /**
     * 用户名判空
     */
    NEED_USER_NAME(10001, "用户名不能为空"),

    NEED_PASSWORD(10002 , "密码不能为空"),

    PASSWORD_TOO_SORT(10003 , "密码不能少于8位"),
    NAME_EXISTED(10004 , "不允许重名"),
    INSERT_FAILED(10005 , "注册失败，请重试"),
    WRONG_PASSWORD(10006 , "密码错误"),
    NEED_LOGIN(10007 , "还未登录，请先登录"),
    UPDATE_FAILED(10008 , "更新失败"),
    NEED_ADMIN(10009 , "无管理员权限"),
    PARA_NOT_NULL(10010 , "参数不能为空"),
    CREAT_FAILED(10011 , "新增失败"),
    REQUEST_PARAM_ERROR(10012 , "参数错误"),
    DELETE_FAILED(10013 , "删除失败"),
    MKDIR_FAILED(10014 , "文件夹创建失败"),
    UPLOAD_FAILED(10015 , "图片上传失败"),
    NOT_SALE(10016 , "商品状态不可售"),
    NOT_ENOUGH(10017 , "商品库存不足"),
    NOT_FOND(11001 , "不存在该记录"),


    SYSTEM_ERROR(20000, "系统错误");
    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code= code;
        this.msg= msg;
    }

    public Integer getCode() {
        return code;
    }

    void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    void setMsg(String msg) {
        this.msg = msg;
    }
}
