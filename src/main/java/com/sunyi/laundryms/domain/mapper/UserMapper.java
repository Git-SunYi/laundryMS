package com.sunyi.laundryms.domain.mapper;

import com.sunyi.laundryms.domain.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    //获取所有用户信息
    @Select("select * from user")
    List<User> getAllUser();
    //根据账户获取用户的所有信息
    @Select("select * from user where account=#{account}")
    User getUserByAccount(@Param("account") String account);
    //根据手机号获取用户的所有信息
    @Select("select * from user where phone=#{phone}")
    User getUserByPhone(@Param("phone") String phone);
    //注册用户添加用户信息
    @Insert("insert into user(name,phone,password) values(#{name},#{phone},#{password}) ")
    int addUser(@Param("name")String name,@Param("phone")String phone,@Param("password")String password);
    //根据手机号码修改密码
    @Update("update user set password=#{password} where phone=#{phone}")
    int updatePassword(@Param("password")String password,@Param("phone")String phone);
    //根据手机号码修改头像
    @Update("update user set head_portrait=#{head_portrait} where phone=#{phone}")
    int updateHeadPortrait(@Param("head_portrait")String head_portrait,@Param("phone")String phone);
    //根据手机号码修改用户信息
    @Update("update user set name=#{name},account=#{account},gender=#{gender},password=#{password},phone=#{phone} where phone=#{byPhone}")
    int updateUserInfo(@Param("name")String name,@Param("account")String account,@Param("gender")String gender,@Param("phone")String phone,@Param("password")String password,@Param("byPhone")String byPhone);
    //根据手机号码删除用户信息
    @Delete("delete from user where phone=#{phone}")
    int deleteUserInfo(@Param("phone")String phone);

    //根据手机号修改用户余额
    boolean updateUserByPhone(User user);
}
