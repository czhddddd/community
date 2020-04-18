package com.czh.dto;

import com.alibaba.fastjson.serializer.IntegerCodec;
import com.czh.exception.CustomizeErrorCode;
import com.czh.exception.CustomizeException;
import lombok.Data;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/18 4:37 下午
 */
@Data
public class ResultDTO {
    private Integer code;
    private String message;
    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode noLogin) {
        return errorOf(noLogin.getCode(),noLogin.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("ok");
        return resultDTO;
    }


}
