package com.czh.mapper;

import com.czh.modle.Comment;
import com.czh.modle.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,comment) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{comment})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id = #{parentId}")
    Comment findByParentId(@Param("parentId") Long parentId);


    @Select("select count(1) from comment where parent_id=#{id}")
    Integer commentCount(@Param("id") Long id);

    @Select("select * from comment where parent_id=#{id} order by GMT_CREATE desc limit #{offset},#{size}")
    List<Comment> list(@Param("id") Long id,@Param("offset") Integer offset, @Param("size") Integer size);
}
