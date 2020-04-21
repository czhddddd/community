package com.czh.controller;

import com.czh.dto.CommentPageDTO;
import com.czh.dto.PageDTO;
import com.czh.dto.QuestionDTO;
import com.czh.modle.Question;
import com.czh.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/13 4:57 下午
 */
@Controller
public class MyQuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/myquestion/{id}")
    public String MyQuestion(@PathVariable("id") Long id, Model model,
                             @RequestParam(value = "page",defaultValue = "1") Integer page,//前台当前页数
                             @RequestParam(value = "size",defaultValue = "5") Integer size){
        //我的问题页面，通过文章的id查询，返回详情
        QuestionDTO byId = questionService.findById(id);
        questionService.incView(id);
        model.addAttribute("myquestion",byId);

        //根据tag获取相似标签文章
        String tag = byId.getTag();
        String replace = tag.replace(",", "|");
        PageDTO questions = questionService.selectLikeTag(replace,page,size);
        model.addAttribute("likeQuestion",questions);

        //回复列表
        CommentPageDTO commentPageDTO = questionService.selectByIdList(id, page, size);
        model.addAttribute("comments",commentPageDTO);
        return "myquestion";
    }

    @GetMapping("/sametagquestion/{tag}")
    public String sameTagQuestion(@PathVariable("tag") String tag,Model model,
                                  @RequestParam(value = "page",defaultValue = "1") Integer page,//前台当前页数
                                  @RequestParam(value = "size",defaultValue = "5") Integer size){

        PageDTO questions = questionService.selectLikeTag(tag,page,size);

        int type = 0;
        if (questions.equals(null)){
            type = 1;
        }

        model.addAttribute("tag",tag);
        model.addAttribute("samequestion",questions);
        model.addAttribute("type",type);

        return "sametagquestion";
    }
}
