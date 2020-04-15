package com.czh.controller;

import com.alibaba.fastjson.parser.JSONToken;
import com.czh.dto.AccessTokenDTO;
import com.czh.dto.GitHubUser;
import com.czh.provider.GiteeProvider;
import com.czh.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GithubAuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @GetMapping("/callbackgithub")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = githubProvider.getUser(accessToken);
        if (user != null){//如果user有内容则说明已经获取到gitee的
            //在spring ioc中拿到HttpServletRequest，将获取到的user对象放入session中
            request.getSession().setAttribute("user",user);//会如果不主动写入会自动签发JSESSIONID
            return "redirect:/";
        }else {//第三方登录失败，重新登录
            return "redirect:/";
        }
    }


}
