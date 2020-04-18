package com.czh.modle;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creatorId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

}
