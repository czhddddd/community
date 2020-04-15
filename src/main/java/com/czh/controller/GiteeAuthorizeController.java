package com.czh.controller;

import com.czh.dto.AccessTokenDTO;
import com.czh.dto.GitHubUser;
import com.czh.dto.GiteeUser;
import com.czh.mapper.UserMapper;
import com.czh.modle.User;
import com.czh.provider.GiteeProvider;
import com.czh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class GiteeAuthorizeController {

    @Autowired
    private GiteeProvider giteeProvider;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Value("${gitee.client.id}")
    private String client_id;

    @Value("${gitee.client.secret}")
    private String client_secret;

    @Value("${gitee.redirect.uri}")
    private String redirect_uri;

    @GetMapping("/callbackgitee")
    public String callbackgitee(@RequestParam(name = "code") String code,
                                @RequestParam(name = "state") String state,
                                HttpServletResponse response,
                                HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(redirect_uri);
       /* accessTokenDTO.setClient_id("ac31ebf60c26ff18c6db18c64b654849fcffc760635a53692da846d8ab02d899");
        accessTokenDTO.setClient_secret("e51b4e0e09190272f874add14499fb56a105c7f4445685c68810625351f2c45d");
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callbackgitee");*/
        accessTokenDTO.setCode(code);

        accessTokenDTO.setState(state);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO,code);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        if (giteeUser != null){//如果user有内容则说明已经获取到gitee的
            //将用户信息存储到数据库中
            User user = new User();
            user.setName(giteeUser.getName());
            user.setAccountId(String.valueOf(giteeUser.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAvatarUrl(giteeUser.getAvatarUrl());
            user.setUsername(giteeUser.getLogin());
            User creatOrUpdate = userService.creatOrUpdate(user);

            //将token放入cookies中
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {//第三方登录失败，重新登录
            return "redirect:/";
        }
    }
}
