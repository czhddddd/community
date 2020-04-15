package com.czh.dto;

import lombok.Data;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/14 9:39 下午
 */
@Data
public class CommentDto {
    private long parentId;
    private String comment;
    private Integer type;
}
