package com.czh.dto;

import com.czh.modle.User;
import lombok.Data;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/19 2:39 下午
 */
@Data
public class CommentResultDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String comment;
    private User user;
}
