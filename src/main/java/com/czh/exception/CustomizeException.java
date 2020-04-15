package com.czh.exception;

import javax.imageio.spi.ImageInputStreamSpi;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/14 7:24 下午
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
