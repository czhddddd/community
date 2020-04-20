package com.czh.exception;

import org.apache.logging.log4j.message.StringFormattedMessage;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"您找的问题不存在了"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何评论进行回复"),
    NO_LOGIN(2003,"请登录"),
    SYS_ERROR(2004,"服务器冒烟了"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FIND(2006,"你找的评论不存在了"),
    CONENT_IS_EMPTY(2007,"输入评论不能为空");


    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }




}
