package com.czh.modle;

import lombok.Data;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/14 9:45 下午
 */
@Data
public class Comment {
    private long id;
    private long parentId;
    private Integer type;
    private Integer commentator;
    private long gmtCreate;
    private long gmtModified;
    private long likeCount;
    private String comment;

}
