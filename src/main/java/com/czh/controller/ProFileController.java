package com.czh.controller;

import com.czh.dto.NotificationPageDTO;
import com.czh.dto.PageDTO;
import com.czh.dto.QuestionDTO;
import com.czh.mapper.NotificationMapper;
import com.czh.mapper.UserMapper;
import com.czh.modle.User;
import com.czh.service.NotificationService;
import com.czh.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/13 10:29 上午
 */
@Controller
public class ProFileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/question")
    public String profile(HttpServletRequest request,
                          Model model,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size" ,defaultValue = "5") Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        model.addAttribute("section","question");
        model.addAttribute("sectionName","我的提问");
        //我的提问
        PageDTO question = questionService.profileQuestionList(user,page,size);
        model.addAttribute("questionpagDTO",question);


       /* //我的问题内容
        if ("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
            //我的提问
            PageDTO question = questionService.profileQuestionList(user,page,size);
            model.addAttribute("questionpagDTO",question);
        }
        //我的回复
        if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回复");
            //查询所有我收的到回复，通过receiver
            NotificationPageDTO notificationPageDTO = notificationService.selectAllNotificationByReceicer(user, page, size);
            model.addAttribute("notificationPageSTO",notificationPageDTO);
        }*/
        return "profile";
    }
    @GetMapping("/profile/replies")
    public String replies(HttpServletRequest request,
                          Model model,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size" ,defaultValue = "5") Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        model.addAttribute("section","replies");
        model.addAttribute("sectionName","消息");
        //查询所有我收的到回复，通过receiver
        NotificationPageDTO notificationPageDTO = notificationService.selectAllNotificationByReceicer(user, page, size);
        model.addAttribute("notificationPageDTO",notificationPageDTO);
        notificationService.updateStatusByReceicer(user);


        return "replies";
    }

}
