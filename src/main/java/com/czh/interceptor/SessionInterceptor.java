package com.czh.interceptor;

import com.czh.mapper.UserMapper;
import com.czh.modle.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ref.PhantomReference;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/13 3:02 下午
 */
@Configuration
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在request中获取cookies
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
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
