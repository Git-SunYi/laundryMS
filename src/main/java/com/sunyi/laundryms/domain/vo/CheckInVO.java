package com.sunyi.laundryms.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class CheckInVO {

    /**
     * 签到日期
     */
    private List<String> checkInDates;

    /**
     * 历史签到日期
     */
    private List<Integer> days;

    /**
     * 本月连续签到天数
     */
    private int continuousDays;

    /**
     * 本月连续签到天数列表
     */
    private Integer continuousDaysList;
    /**
     * 本月连续签到天数列表
     */
    private List<Integer> continuousDaysLists;

    /**
     * 本月签到天数
     */
    private int currentMonthDays;

    /**
     * 总签到数
     */
    private int totalDays;

    /**
     * 今日奖励收入
     */
    private Double todayReward;

    /**
     * 总奖励
     */
    private Double totalReward;

    /**
     * 奖励收入
     */
    private List<Double> rewards;
}
