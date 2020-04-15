package com.czh.mapper;

import com.czh.modle.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,comment) values (parenrId,type,commentator,gmtCreate,gmtModified,likeCount,comment)")
    void insert(Comment comment);
}
