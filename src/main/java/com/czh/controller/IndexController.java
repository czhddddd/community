package com.czh.controller;

import com.czh.dto.PageDTO;
import com.czh.dto.QuestionDTO;
import com.czh.mapper.QuestionMapper;
import com.czh.mapper.UserMapper;
import com.czh.modle.Question;
import com.czh.modle.User;
import com.czh.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,//前台当前页数
                        @RequestParam(value = "size",defaultValue = "5") Integer size){//显示条数
        /*//在request中获取cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {//在cookies Map集合中遍历
                if (cookie.getName().equals("token")){//找键名为"token"的值
                    //cookies是一个map集合，token的键名为"token"，值为我们所需要的token值
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    //如果返回值不为null
                    if (user != null){
                        //在spring ioc中拿到HttpServletRequest，将获取到的user对象放入session中
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }*/

        //希望在index页面展示出问题列表，在上面完成登录验证之后，可以进行一个总查询将数据返回到index页面
        //进行分页操作，此时前台传输page当前页和显示条数size
        //此时需要分页查询
        //List<QuestionDTO> list = questionService.list(page,size);
        //上面的语句可以进行分页，但还有一些其他数据，故需要重新封装一个DTO进行传输
        PageDTO pageDTO = questionService.list(page,size);
        //此时可以查询出问题列表但是因为需要将图片资源所以需要将user表与question表相关联
        model.addAttribute("pagDTO",pageDTO);



        return "index";
    }
}
