package com.sunyi.laundryms.domain.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sunyi.laundryms.common.dto.CommonDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Order extends CommonDataDTO {
    private int id;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 洗衣机产品类别
     */
    private String category;
    /**
     * 衣服数量
     */
    private int clothes_num;
    /**
     * 地址
     */
    private String address;
    /**
     * 下单日期
     */
    @JSONField(name = "date",format = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    /**
     * 交货日期
     */
    @JSONField(name = "delivery_time",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date delivery_time;
    /**
     * 订单金额
     */
    private String money;
    /**
     * 洗前配送方式
     */
    private String collect;
    /**
     * 洗后配送方式
     */
    private String delivery;
    /**
     * 状态
     */
    private String state;
}
