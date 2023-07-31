package com.sunyi.laundryms.domain.bo;

import lombok.Data;

@Data
public class CheckInAdminBO {
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
     * 连续签到天数
     */
    private Integer continuousDays;
}
