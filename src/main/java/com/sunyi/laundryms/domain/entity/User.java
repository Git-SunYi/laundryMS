package com.sunyi.laundryms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 性别
     */
    private String gender;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 头像
     */
    private String head_portrait;
    /**
     * 用户类型
     */
    private String user_type;
    /**
     * 用户余额
     */
    private String user_money;


}
