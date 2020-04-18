package com.czh.advice;

import com.alibaba.fastjson.JSON;
import com.czh.dto.ResultDTO;
import com.czh.exception.CustomizeErrorCode;
import com.czh.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/14 7:11 下午
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            //返回json
            ResultDTO resultDTO = null;
            if (e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
            }


        }else {
            //返回错误跳转页面吧
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message","要不回个主页？");
            }
        }
        return new ModelAndView("error");

    }

}
