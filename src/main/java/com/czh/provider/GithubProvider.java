package com.czh.provider;

import com.alibaba.fastjson.JSON;
import com.czh.dto.AccessTokenDTO;
import com.czh.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component //把当前的类初始化到spring的上下文，即将当前类放入到spring的ioc容器中
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        //okhttp post请求
        OkHttpClient client = new OkHttpClient();
        //okhttp post请求方法
        /*create源码
        * public static RequestBody create(MediaType contentType, String content) {
            Charset charset = Util.UTF_8;
            if (contentType != null) {
              charset = contentType.charset();
              if (charset == null) {
                charset = Util.UTF_8;
                contentType = MediaType.parse(contentType + "; charset=utf-8");
              }
            }
            byte[] bytes = content.getBytes(charset);
            return create(contentType, bytes);
        }
        * */
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String token = split[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        System.out.println(accessToken);
        //okhttp get请求
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
