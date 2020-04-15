package com.czh.controller;

import com.czh.mapper.UserMapper;
import com.czh.modle.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String dologin(@RequestParam("username")String username,
                          @RequestParam("password")String password,
                          Model model,
                          HttpServletResponse response){
        //用于回显数据
        model.addAttribute("username",username);
        model.addAttribute("password",password);

        if (username == null || username == ""){
            model.addAttribute("error","用户名不能为空");
            return "login";
        }
        if (password == null || password == ""){
            model.addAttribute("error","密码不能为空");
            return "login";
        }
        //处理登录逻辑
        User passwordByUsername = userMapper.findPasswordByUsername(username);
        if (passwordByUsername == null || ("").equals(passwordByUsername)){
            model.addAttribute("error","用户名不存在");
            return "login";
        }else {
            if (password.equals(passwordByUsername.getPassword())||password == passwordByUsername.getPassword()){
                String token = UUID.randomUUID().toString();
                passwordByUsername.setToken(token);
                userMapper.updateUser(passwordByUsername);
                response.addCookie(new Cookie("token",token));
                System.out.println(passwordByUsername.getToken());
                return "redirect:/";
            }else {
                model.addAttribute("error","密码错误");
                return "login";
            }
        }
    }

    @GetMapping("/registered")
    public String registered(){
        return "registered";
    }

    @PostMapping("/registered")
    public String doregistered(@RequestParam(value = "name",required = false) String name,
                               @RequestParam(value = "username",required = false) String username,
                               @RequestParam(value = "password",required = false) String password,
                               HttpServletResponse response,
                               Model model){
        //用于回显数据
        model.addAttribute("name",name);
        model.addAttribute("username",username);
        model.addAttribute("password",password);

        if (name == null || name == ""){
            model.addAttribute("error","昵称不能为空");
            return "registered";
        }
        if (username == null || username == ""){
            model.addAttribute("error","用户名不能为空");
            return "registered";
        }
        if (password == null || password == ""){
            model.addAttribute("error","密码不能为空");
            return "registered";
        }
        User byUsername = userMapper.findByUsername(username);
        if (byUsername == null){
            User user = new User();
            user.setUsername(username);
            user.setName(name);
            user.setPassword(password);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            return "login";
        }else {
            model.addAttribute("error","用户名已被占用");
            return "registered";
        }
    }

    @GetMapping("/lgout")
    public String lgout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

}
