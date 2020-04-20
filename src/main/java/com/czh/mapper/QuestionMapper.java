package com.czh.mapper;

import com.czh.modle.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Mapper
@Repository
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator_id,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creatorId},#{tag})")
    void insert(Question question);

    @Select("select * from question order by GMT_CREATE desc limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer titlecount();

    @Select("select * from question where creator_id = #{id} order by GMT_CREATE desc limit #{offset},#{size}")
    List<Question> findByCreateId(@Param("id") Long id,@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator_id = #{id}")
    Integer questioncount(@Param("id") Long id);

    @Select("select * from question where id = #{id}")
    Question findById(@Param("id") Long id);

    @Update("update question set title=#{title},description=#{description},tag=#{tag} where id=#{id}")
    void updateById(Question question);

    @Update("update question set view_count = view_count + 1 where id = #{id}")
    void updateByIdIncView(@Param("id") Long id);

    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    void updateByIdIncComment(@Param("id") Long id);

    @Select("select * from question where tag REGEXP #{replace} limit #{offset},#{size}")
    List<Question> selectLikeTag(@Param("replace") String replace,@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where tag REGEXP #{replace}")
    Integer samequestioncount(@Param("replace") String replace);

}
