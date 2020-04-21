package com.czh.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/20 6:55 下午
 */
@Data
public class NotificationPageDTO {
    private List<NotificationDTO> notificationDTOS;
    private boolean showPrevious;//是否有上一页
    private boolean showFirstPage;//是否有第一页
    private boolean showNext;//是否有下一页
    private boolean showEndPage;//是否有最后一页
    private Integer page;
    private List pages = new ArrayList();
    private Integer titlePage;

    public void setPagination(Integer page, Integer size) {

        this.page = page;

        //分页目录
        pages.add(page);
        for (int j = 1; j <= 3 ; j++) {
            if (page - j > 0){
                pages.add(0,page - j);
            }
            if (page + j <=  titlePage){
                pages.add(page + j);
            }
        }

        //是否有上一页
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }

        //是否有第一页
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        //是否有下一页
        if (page == titlePage){
            showNext = false;
        }else {
            showNext = true;
        }

        //是否有最后一页
        if (pages.contains(titlePage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }


    }
}
