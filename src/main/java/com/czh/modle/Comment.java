package com.czh.modle;

import lombok.Data;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/14 9:45 下午
 */
@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String comment;

}
