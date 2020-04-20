package com.czh.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/20 11:29 上午
 */
@Data
public class TagDTO {
    private String CategoryName;
    private List<String> Tags;
}
