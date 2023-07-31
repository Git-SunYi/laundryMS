package com.sunyi.laundryms.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class OrderVO {
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 下单日期
     */
    @JSONField(name = "date")
    private Date date;
    /**
     * 时间差
     */
    private String timeDifference;

}
