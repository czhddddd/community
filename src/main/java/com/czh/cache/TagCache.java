package com.czh.cache;

import com.czh.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/20 11:29 上午
 */
public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO tagDTO = new TagDTO();
        tagDTO.setCategoryName("后端");
        tagDTO.setTags(Arrays.asList("php","java","node.js","python","c++","c","go","lang","spring","后端","springboot","django","flask","c#","swoole","ruby","asp.net","ruby-on-rails","scala","rust","lavarel","爬虫"));
        tagDTOS.add(tagDTO);

        TagDTO tagDTO1 = new TagDTO();
        tagDTO1.setCategoryName("数据库");
        tagDTO1.setTags(Arrays.asList("mysql","redis","mongodb","sql数据库","json","elasticsearch","nosql","memcached","postgresql","sqlite","mariadb"));
        tagDTOS.add(tagDTO1);

        return tagDTOS;
    }

}
