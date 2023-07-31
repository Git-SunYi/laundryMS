package com.sunyi.laundryms.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private int id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 电话号码
     */
    private String phone;

}
