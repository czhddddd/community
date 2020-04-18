package com.czh.enums;

import sun.dc.pr.PRError;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/18 4:44 下午
 */
public enum  CommentType {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    CommentType(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentType value : CommentType.values()) {
            if(value.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
