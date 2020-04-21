package com.czh.modle;

import lombok.Data;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/20 4:18 下午
 */
@Data
public class Notification {
    private Long id;
    private Long notification;//发起人
    private Long receiver;//接受人
    private Long commentId;//动作作用id
    private Integer type;//动作类型
    private Long gmtCreate;
    private Integer status;//为读消息
    private Long questionId;

}
