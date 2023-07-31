package com.sunyi.laundryms.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LaundryKnowledge implements Serializable {
    private int id;

    /**
     * 洗衣常识url
     */
    private String laundryKnowledgeUrl;

    /**
     * 选中状态
     */
    private int state;
}
