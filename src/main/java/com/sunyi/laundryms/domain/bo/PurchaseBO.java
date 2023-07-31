package com.sunyi.laundryms.domain.bo;

import lombok.Data;

@Data
public class PurchaseBO {
    /**
     * 会员卡类型
     */
    private String cardType;

    /**
     * 会员卡金额
     */
    private String cardMoney;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户类型
     */
    private String userType;
}
