package com.czh.controller;

import com.czh.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/21 3:13 下午
 */
@Controller
public class FileController {
    @GetMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/logos/editormd-logo-16x16.png");
        return fileDTO;
    }
}
