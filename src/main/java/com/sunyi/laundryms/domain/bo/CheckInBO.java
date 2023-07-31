package com.sunyi.laundryms.domain.bo;

import lombok.Data;

@Data
public class CheckInBO {
    /**
     * 手机号码
     */
    private String phone;

    /**
     * 签到日期
     */
    private String checkInDate;
}
