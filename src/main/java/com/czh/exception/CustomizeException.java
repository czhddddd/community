package com.czh.exception;

import javax.imageio.spi.ImageInputStreamSpi;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/14 7:24 下午
 */
public class CustomizeException extends RuntimeException {
    private Integer code;
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
