package com.czh.dto;

import com.czh.modle.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Long creatorId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
