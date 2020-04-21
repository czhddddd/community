package com.czh.mapper;

import com.czh.modle.Notification;
import com.czh.modle.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface NotificationMapper {
    @Insert("insert into notification (notification,receiver,comment_id,type,gmt_create,status,question_id) values (#{notification},#{receiver},#{commentId},#{type},#{gmtCreate},#{status},#{questionId})")
    void insert(Notification notification);

    @Select("select * from notification where receiver = #{id} order by GMT_CREATE desc limit #{offset},#{size}")
    List<Notification> selectAllNotificationByReceicer(@Param("id") Long id,@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from notification where receiver = #{id}")
    Integer countAllNotificationByReceicer(@Param("id") Long id);

    @Select("select count(1) from notification where receiver = #{id} and status = 0")
    Integer countByreceiver(@Param("id") Long id);

    @Update("update notification set status = 1 where receiver = #{id} and status = 0")
    void updateStatusByReceicer(@Param("id") Long id);
}
