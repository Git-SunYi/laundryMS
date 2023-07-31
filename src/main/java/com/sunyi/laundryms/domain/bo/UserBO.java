package com.sunyi.laundryms.domain.bo;

import lombok.Data;

@Data
public class UserBO {
    /**
     * 电话号码
     */
    private String phone;

    /**
     * 充值金额
     */
    private String money;

    /**
     * 电话号码
     */
    private String phoneOrAccount;

    /**
     * 密码
     */
    private String password;
}
