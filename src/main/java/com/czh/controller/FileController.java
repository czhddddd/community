package com.czh.controller;

import com.czh.dto.FileDTO;
import com.czh.provider.ALiYunFileOSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/21 3:13 下午
 */
@Controller
public class FileController {

    @Autowired
    private ALiYunFileOSS a;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");


        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/logos/editormd-logo-16x16.png");
        return fileDTO;
    }
}
