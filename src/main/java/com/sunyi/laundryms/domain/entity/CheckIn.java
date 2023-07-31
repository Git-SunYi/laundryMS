package com.sunyi.laundryms.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CheckIn {
    private int id;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 签到日期
     */
    private Date checkInDate;

    /**
     * 奖励收入
     */
    private Double reward;
}
