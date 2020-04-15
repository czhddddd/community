package com.czh.controller;

import com.czh.mapper.QuestionMapper;
import com.czh.mapper.UserMapper;
import com.czh.modle.Question;
import com.czh.modle.User;
import com.czh.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    //如果是get方法那就渲染页面，如果是post方法那就执行请求
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            HttpServletRequest request,
                            Model model,
                            @RequestParam(value = "id",required = false) Integer id){
        //用于回显数据
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("error","问题描述不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }



        //post请求发过来的表单为发布问题，应将表单信息存入数据库中
        User user = null;
        //在cookies中拿到token，通过token得到user信息
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String value = cookie.getValue();
                    user = userMapper.findByToken(value);
                    break;
                }
            }
        }else {
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        /*User user1 = (User)request.getSession().getAttribute("user");
        System.out.println(user.getId()+"=="+user1.getId());
        System.out.println(user.getName()+"=="+user1.getName());*/

        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setCreatorId(user.getId());
        question.setTag(tag);
        question.setId(id);

       questionService.insertOrUpdate(question);

        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Integer id,Model model){
        //编辑
        Question byId = questionMapper.findById(id);
        //回显数据
        model.addAttribute("title",byId.getTitle());
        model.addAttribute("description",byId.getDescription());
        model.addAttribute("tag",byId.getTag());
        model.addAttribute("id",id);
        return "publish";
    }


    /*@PostMapping("/publish/{id}")
    public String doedit(@PathVariable("id")Integer id,Model model,
                         @RequestParam(value = "title",required = false) String title,
                         @RequestParam(value = "description",required = false) String description,
                         @RequestParam(value = "tag",required = false) String tag){
        //用于回显数据
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("id",id);

        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("error","问题描述不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setId(id);

        questionService.insertOrUpdate(question);

        return "redirect:/";

    }*/

}
