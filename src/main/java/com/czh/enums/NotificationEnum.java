package com.czh.enums;

public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    LIKE_QUESTION(3,"喜欢了您的问题"),
    LIKE_COMMENT(4,"点赞了您的评论");
    private int type;
    private String name;

    NotificationEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
