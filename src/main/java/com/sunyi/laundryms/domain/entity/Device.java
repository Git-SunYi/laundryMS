package com.sunyi.laundryms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    private int id;
    /**
     * 产品类别
     */
    private String category;
    /**
     * 自动化程度
     */
    private String automation;
    /**
     * 排水方式
     */
    private String drainage;
    /**
     * 容量
     */
    private String capacity;
    /**
     * 能效等级
     */
    private String energy_efficiency;
}
