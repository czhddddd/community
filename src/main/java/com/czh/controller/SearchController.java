package com.czh.controller;

import com.czh.dto.PageDTO;
import com.czh.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/21 8:18 下午
 */
@Controller
public class SearchController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/search")
    public String search(HttpServletRequest request, Model model,
                         @RequestParam(value = "page",defaultValue = "1") Integer page,//前台当前页数
                         @RequestParam(value = "size",defaultValue = "5") Integer size,
                         @RequestParam(value = "search",required = false) String search){
        PageDTO questions = questionService.selectLikeTag(search,page,size);
        int type = 0;
        if (questions.getQuestionDTO().size()==0){
            type = 1;
        }

        model.addAttribute("tag",search);
        model.addAttribute("samequestion",questions);
        model.addAttribute("type",type);

        return "sametagquestion";
    }
}
