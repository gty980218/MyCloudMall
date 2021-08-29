package org.skyrain.cloud.mall.common.exception;

/**
 * 描述：     统一异常
 */
public class CloudMallException extends RuntimeException {

    private final Integer code;
    private final String message;

    public CloudMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CloudMallException(CloudMallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
