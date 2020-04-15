package com.czh.dto;

import lombok.Data;

@Data
public class GiteeUser {
    private String name;
    private long id;
    private String bio;
    private String avatarUrl;
    private String login;

}
