package com.czh.service;

import com.czh.enums.CommentType;
import com.czh.enums.NotificationEnum;
import com.czh.enums.NotificationStatusEunm;
import com.czh.exception.CustomizeErrorCode;
import com.czh.exception.CustomizeException;
import com.czh.mapper.CommentMapper;
import com.czh.mapper.NotificationMapper;
import com.czh.mapper.QuestionMapper;
import com.czh.modle.Comment;
import com.czh.modle.Notification;
import com.czh.modle.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/18 4:47 下午
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentType.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() ==  CommentType.COMMENT.getType()){
            //回复评论
            Comment byParentId = commentMapper.findByParentId(comment.getParentId());
            if (byParentId == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FIND);
            }
            commentMapper.insert(comment);
            //创建
            createNotification(comment, byParentId.getParentId(), NotificationEnum.REPLY_COMMENT.getType());

        }else {
            //回复问题
            Question byId = questionMapper.findById(comment.getParentId());
            if (byId == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateByIdIncComment(comment.getParentId());

            //创建通知
            createNotification(comment,byId.getCreatorId(), NotificationEnum.REPLY_QUESTION.getType());


        }
    }

    private void createNotification(Comment comment, long receiver, int type) {
        Notification notification = new Notification();
        notification.setNotification(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setCommentId(comment.getId());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type);
        notification.setStatus(NotificationStatusEunm.UNREAD.getStatus());
        notification.setQuestionId(comment.getParentId());
        notificationMapper.insert(notification);
    }
}
