package com.czh.service;

import com.czh.dto.NotificationDTO;
import com.czh.dto.NotificationPageDTO;
import com.czh.dto.PageDTO;
import com.czh.dto.QuestionDTO;
import com.czh.mapper.NotificationMapper;
import com.czh.mapper.QuestionMapper;
import com.czh.mapper.UserMapper;
import com.czh.modle.Notification;
import com.czh.modle.Question;
import com.czh.modle.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/20 6:11 下午
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public NotificationPageDTO selectAllNotificationByReceicer(User user, Integer page, Integer size) {
        //获取列表总数
        Integer titlecount = notificationMapper.countAllNotificationByReceicer(user.getId());
        //计算分页数
        Integer titlepage;
        if (titlecount % size == 0){
            titlepage = titlecount / size;
        }else {
            titlepage = (titlecount / size) + 1;
        }
        //容错处理
        if (page < 1){
            page = 1;
        }
        if (page > titlepage){
            page = titlepage;
        }

        //通过questionmapper将数据查询出来
        Integer offset = size * (page - 1);
        List<Notification> notifications = notificationMapper.selectAllNotificationByReceicer(user.getId(),offset,size);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        //展示效果
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            if (notification.getType() == 1){//问题回复
                BeanUtils.copyProperties(notification,notificationDTO);
                //回复人昵称
                User byId = userMapper.findById(notification.getNotification());
                String name = byId.getName();
                notificationDTO.setName(name);
                //回复的问题
                Question byId1 = questionMapper.findById(notification.getQuestionId());
                String title = byId1.getTitle();
                notificationDTO.setQuestionTitle(title);
                notificationDTOS.add(notificationDTO);
            }else if (notification.getType() == 2){//评论回复
                BeanUtils.copyProperties(notification,notificationDTO);
                //回复人昵称
                User byId = userMapper.findById(notification.getNotification());
                String name = byId.getName();
                notificationDTO.setName(name);
                //回复的问题
                Question byId1 = questionMapper.findById(notification.getQuestionId());
                String title = byId1.getTitle();
                notificationDTO.setQuestionTitle(title);
                notificationDTOS.add(notificationDTO);
            }
        }

        NotificationPageDTO notificationPageDTO = new NotificationPageDTO();

        notificationPageDTO.setNotificationDTOS(notificationDTOS);
        notificationPageDTO.setTitlePage(titlepage);
        notificationPageDTO.setPagination(page,size);

        return notificationPageDTO;

    }

    public void updateStatusByReceicer(User user) {
        notificationMapper.updateStatusByReceicer(user.getId());
    }
}
