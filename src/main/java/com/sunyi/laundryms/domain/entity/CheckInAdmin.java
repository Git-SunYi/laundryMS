package com.sunyi.laundryms.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CheckInAdmin {
    private Integer id;

    /**
     * 第一次签到奖励
     */
    private Double firstReward;

    /**
     * 签到奖励增加
     */
    private Double rewardAdd;

    /**
     * 连续签到奖励增加
     */
    private Double continuousReward;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 连续签到天数
     */
    private int continuousDays;
}
