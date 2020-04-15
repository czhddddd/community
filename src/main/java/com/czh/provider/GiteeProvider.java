package com.czh.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.czh.dto.AccessTokenDTO;
import com.czh.dto.GitHubUser;
import com.czh.dto.GiteeUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {

    @Value("${gitee.client.id}")
    private String client_id;

    @Value("${gitee.client.secret}")
    private String client_secret;

    @Value("${gitee.redirect.uri}")
    private String redirect_uri;



    public String getAccessToken(AccessTokenDTO accessTokenDTO,String code){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
       /* System.out.println(client_id);
        System.out.println(client_secret);
        System.out.println(redirect_uri);
        System.out.println("https://gitee.com/oauth/token?grant_type=authorization_code&code=" + code +
                "&client_id=" + client_id +
                "&redirect_uri=" + client_secret +
                "&client_secret=" + redirect_uri);*/

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&code=" + code +
                        "&client_id=" + client_id +
                        "&redirect_uri=" + redirect_uri +
                        "&client_secret=" + client_secret)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            JSONObject obj=JSONObject.parseObject(string);
            return obj.get("access_token").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiteeUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        System.out.println(accessToken);
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public String getAccessToken(AccessTokenDTO accessTokenDTO,String code){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&" +
                        "code=" + code +
                        "&client_id=ac31ebf60c26ff18c6db18c64b654849fcffc760635a53692da846d8ab02d899&" +
                        "redirect_uri=http://localhost:8887/callbackgitee&" +
                        "client_secret=e51b4e0e09190272f874add14499fb56a105c7f4445685c68810625351f2c45d")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();

            JSONObject obj=JSONObject.parseObject(string);
            return obj.get("access_token").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        System.out.println(accessToken);
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
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
    }*/
}
