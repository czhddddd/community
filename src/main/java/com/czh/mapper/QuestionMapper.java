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

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer titlecount();

    @Select("select * from question where creator_id = #{id} limit #{offset},#{size}")
    List<Question> findByCreateId(@Param("id") Integer id,@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator_id = #{id}")
    Integer questioncount(@Param("id") Integer id);

    @Select("select * from question where id = #{id}")
    Question findById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},tag=#{tag} where id=#{id}")
    void updateById(Question question);

    @Update("update question set view_count = view_count + 1 where id = #{id}")
    void updateByIdIncView(Integer id);
}