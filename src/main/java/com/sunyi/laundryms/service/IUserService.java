package com.sunyi.laundryms.service;

import com.sunyi.laundryms.domain.bo.UserBO;
import com.sunyi.laundryms.domain.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserService {
    //获取所有用户信息
    List<User> getAllUser();
    //根据账户获取用户的所有信息
    User getUserByAccount(@Param("account") String account);
    //根据手机号获取用户的所有信息
    User getUserByPhone(@Param("phone") String phone);
    //注册用户添加用户信息
    int addUser(@Param("name")String name,@Param("phone")String phone,@Param("password")String password);
    //根据手机号码修改密码
    int updatePassword(@Param("password")String password,@Param("phone")String phone);
    //根据手机号码修改头像
    int updateHeadPortrait(@Param("head_portrait")String head_portrait,@Param("phone")String phone);
    //根据手机号码修改用户信息
    int updateUserInfo(User user, String byPhone);
    //根据手机号码删除用户信息
    int deleteUserInfo(@Param("phone")String phone);

    //充值
    boolean updateUserByPhone(UserBO bo);

    //更新Redis用户数据库
    void updateUserRedis();
}
