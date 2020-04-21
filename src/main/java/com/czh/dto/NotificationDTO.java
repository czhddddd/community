package com.czh.dto;

import lombok.Data;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/20 6:24 下午
 */
@Data
public class NotificationDTO {
    private Long notification;
    private Long gmtCreate;
    private Long type;
    private Integer status;//为读消息
    private Long questionId;
    private String name;
    private String questionTitle;

}
