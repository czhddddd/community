package com.czh.service;

import com.czh.dto.CommentPageDTO;
import com.czh.dto.CommentResultDTO;
import com.czh.dto.PageDTO;
import com.czh.dto.QuestionDTO;
import com.czh.mapper.CommentMapper;
import com.czh.mapper.QuestionMapper;
import com.czh.mapper.UserMapper;
import com.czh.modle.Comment;
import com.czh.modle.Question;
import com.czh.modle.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    //因为需要将user与question两张表结合起来，所以需要构建一个新的数据传输DTO，因为DTO不属于任何一个mapper，
    //所以需要构建一个业务层service来处理结合的业务
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    //首页列表
    public PageDTO list(Integer page, Integer size){
        //获取列表总数
        Integer titlecount = questionMapper.titlecount();
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
        List<Question> list = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        //遍历list
        for (Question question : list) {
            //通过question.creatuid获得user对象
            User userbyId = userMapper.findById(question.getCreatorId());
            //将question对象拷贝进questiondto中
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils spring提供的工具类进行类的copy
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(userbyId);
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTO(questionDTOS);
        pageDTO.setTitlePage(titlepage);
        pageDTO.setPagination(page,size);


        return pageDTO;
    }

    //我的问题的列表
    public PageDTO profileQuestionList(User user, Integer page, Integer size) {

        //账号下所有问题的数量
        Integer questioncount = questionMapper.questioncount(user.getId());
        //计算分页数
        Integer questionPage ;
        if (questioncount % size == 0){
            questionPage = questioncount / size;
        }else {
            questionPage = questioncount / size +1;
        }
        //容错处理
        if (page < 1){
            page = 1;
        }
        if (page > questionPage){
            page = questionPage;
        }
        //进行分页
        Integer offset = size * (page - 1);
        //获取账号下所有的问题
        List<Question> questionByCreateId = questionMapper.findByCreateId(user.getId(), offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questionByCreateId) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setQuestionDTO(questionDTOS);
        pageDTO.setTitlePage(questionPage);
        pageDTO.setPagination(page,size);

        return pageDTO;
    }
    //根据id查找
    public QuestionDTO findById(Long id) {
        Question byId = questionMapper.findById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(byId,questionDTO);
        User userbyId = userMapper.findById(byId.getCreatorId());
        questionDTO.setUser(userbyId);
        return questionDTO;
    }
    //插入或更新question
    public void insertOrUpdate(Question question) {
        Long id = question.getId();
        if (id == null){
            //插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //更新
            questionMapper.updateById(question);
            }
    }
    //浏览次数
    public void incView(Long id) {

        questionMapper.updateByIdIncView(id);
    }


    //问题评论列表
    public CommentPageDTO selectByIdList(Long id, Integer page, Integer size) {
        Integer commentCount = commentMapper.commentCount(id);
        //计算分页数
        Integer commentPage;
        if (commentCount % size == 0){
            commentPage = commentCount / size;
        }else {
            commentPage = (commentCount / size) + 1;
        }
        //容错处理
        if (page < 1){
            page = 1;
        }
        if (page > commentPage){
            page = commentPage;
        }

        //通过将数据查询出来
        Integer offset = size * (page - 1);
        List<Comment> list = commentMapper.list(id,offset,size);
        List<CommentResultDTO>  commentResultDTOS = new ArrayList<>();
        CommentPageDTO commentPageDTO = new CommentPageDTO();
        //遍历list
        for (Comment comment : list) {
            //通过question.creatuid获得user对象
            User userbyId = userMapper.findById(comment.getCommentator());
            //将question对象拷贝进questiondto中
            CommentResultDTO commentResultDTO = new CommentResultDTO();
            //BeanUtils spring提供的工具类进行类的copy
            BeanUtils.copyProperties(comment,commentResultDTO);
            commentResultDTO.setUser(userbyId);
            commentResultDTOS.add(commentResultDTO);
        }
        commentPageDTO.setCommentResultDTOS(commentResultDTOS);
        commentPageDTO.setTitlePage(commentPage);
        commentPageDTO.setPagination(page,size);

        return commentPageDTO;
    }

    //根据标签查找类似标签问题
    public PageDTO selectLikeTag(String replace,Integer page,Integer size) {
        //获取列表总数
        Integer titlecount = questionMapper.samequestioncount(replace);
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
        List<Question> list = questionMapper.selectLikeTag(replace,offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        //遍历list
        for (Question question : list) {
            //通过question.creatuid获得user对象
            User userbyId = userMapper.findById(question.getCreatorId());
            //将question对象拷贝进questiondto中
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils spring提供的工具类进行类的copy
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(userbyId);
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTO(questionDTOS);
        pageDTO.setTitlePage(titlepage);
        pageDTO.setPagination(page,size);

        return pageDTO;
    }


}
