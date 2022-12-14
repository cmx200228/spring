package com.imooc.imooc_mall.exception;

/**统一异常
 * @author 陈蒙欣
 * @date 2022/11/30 23:14
 */
public class ImoocMallException extends RuntimeException {
    private final Integer code;

    private final String msg;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ImoocMallException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ImoocMallException(ImoocMallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
