package com.czh.controller;

import com.czh.dto.QuestionDTO;
import com.czh.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String MyQuestion(@PathVariable("id") Long id, Model model){
        //我的问题页面，通过文章的id查询，返回详情
        QuestionDTO byId = questionService.findById(id);
        questionService.incView(id);
        model.addAttribute("myquestion",byId);
        return "myquestion";
    }
}
