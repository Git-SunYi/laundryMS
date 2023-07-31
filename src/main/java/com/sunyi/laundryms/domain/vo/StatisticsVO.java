package com.sunyi.laundryms.domain.vo;

import lombok.Data;

@Data
public class StatisticsVO {
    /**
     * 今日订单数
     */
    private int toDaysOrderNum;
    /**
     * 今日销售额
     */
    private Double toDaysMoney;
    /**
     * 总销售额
     */
    private Double totalMoney;
    /**
     * 总订单数
     */
    private int totalOrderNum;
}
