package com.sunyi.laundryms.service.impl;

import com.sunyi.laundryms.common.utils.RedisUtils;
import com.sunyi.laundryms.domain.bo.UserBO;
import com.sunyi.laundryms.domain.entity.User;
import com.sunyi.laundryms.domain.mapper.IndexMapper;
import com.sunyi.laundryms.domain.mapper.UserMapper;
import com.sunyi.laundryms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    IndexMapper indexMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public User getUserByAccount(String phoneOrAccount) {
        long starTime = System.currentTimeMillis();
        User userByAccount = userMapper.getUserByAccount(phoneOrAccount);
        if (Objects.isNull(userByAccount)){
            return null;
        }

        Map<Object, Object> userMap1 = RedisUtils.hmget("user");
        Object user = null;
        for (Map.Entry<Object,Object> entry: userMap1.entrySet()){
            Integer userId= Integer.valueOf(entry.getKey().toString());
            if (userId.equals(userByAccount.getId())){
                user = entry.getValue();
            }
        }
        if (user != null) {
            User user1 = new User();
            BeanUtils.copyProperties(user,user1);
            log.info("Redis查询总耗时:{}",System.currentTimeMillis() - starTime);
            return user1;
        }

        List<User> allUser = userMapper.getAllUser();
        Map<Object, Object> userMap = new HashMap<>();
        for (User user1:allUser){
            userMap.put(String.valueOf(user1.getId()),user1);
        }
        RedisUtils.hmset("user",userMap,43200);

        log.info("MySQL查询总耗时:{}",System.currentTimeMillis() - starTime);
        return userByAccount;
    }

    @Override
    public User getUserByPhone(String phoneOrAccount) {
        long starTime = System.currentTimeMillis();

        User userByPhone = userMapper.getUserByPhone(phoneOrAccount);

//        JwtSysUser jwtSysUser = new JwtSysUser();
//        jwtSysUser.setId(userByPhone.getId());
//        jwtSysUser.setPhone(userByPhone.getPhone());
//        jwtSysUser.setPassword(userByPhone.getPassword());
//        JwtTokenUtil.generateToken(jwtSysUser);

        if (Objects.isNull(userByPhone)){
            return null;
        }

        Map<Object, Object> userMap1 = RedisUtils.hmget("user");
        Object user = null;
        for (Map.Entry<Object,Object> entry: userMap1.entrySet()){
            Integer userId= Integer.valueOf(entry.getKey().toString());
            if (userId.equals(userByPhone.getId())){
                user = entry.getValue();
            }
        }
        if (user != null) {
            User user1 = new User();
            BeanUtils.copyProperties(user,user1);
            log.info("Redis查询总耗时:{}",System.currentTimeMillis() - starTime);
            return user1;
        }

        List<User> allUser = userMapper.getAllUser();
        Map<Object, Object> userMap = new HashMap<>();
        for (User user1:allUser){
            userMap.put(String.valueOf(user1.getId()),user1);
        }
        RedisUtils.hmset("user",userMap,43200);

        log.info("MySQL查询总耗时:{}",System.currentTimeMillis() - starTime);
        return userByPhone;
    }

    @Override
    public int addUser(String name, String phone, String password) {
        return userMapper.addUser(name,phone,password);
    }

    @Override
    public int updatePassword(String password, String phone) {
        if (userMapper.updatePassword(password,phone)>0) {
            RedisUtils.del("user");
            List<User> allUser = userMapper.getAllUser();
            Map<Object, Object> userMap = new HashMap<>();
            for (User user1 : allUser) {
                userMap.put(String.valueOf(user1.getId()), user1);
            }
            RedisUtils.hmset("user", userMap, 43200);
            return 1;
        }
        return 0;
    }

    @Override
    public int updateHeadPortrait(String head_portrait,String phone) {
        if (userMapper.updateHeadPortrait(head_portrait,phone)>0) {
            RedisUtils.del("user");
            List<User> allUser = userMapper.getAllUser();
            Map<Object, Object> userMap = new HashMap<>();
            for (User user1 : allUser) {
                userMap.put(String.valueOf(user1.getId()), user1);
            }
            RedisUtils.hmset("user", userMap, 43200);
            return 1;
        }
        return 0;
    }

    @Override
    public int updateUserInfo(User user,String byPhone) {
        if (userMapper.updateUserInfo(user.getName(),user.getAccount(),user.getGender(),user.getPhone(),user.getPassword(),byPhone) > 0){

            RedisUtils.del("user");
            List<User> allUser = userMapper.getAllUser();
            Map<Object, Object> userMap = new HashMap<>();
            for (User user1:allUser){
                userMap.put(String.valueOf(user1.getId()),user1);
            }
            RedisUtils.hmset("user",userMap,43200);

            return 1;
        }
        return 0;
    }

    @Override
    public int deleteUserInfo(String phone) {
        if (userMapper.deleteUserInfo(phone) > 0){
            return 1;
        }
        return 0;
    }

    @Override
    public boolean updateUserByPhone(UserBO bo) {
        User userByPhone = indexMapper.findUserByPhone(bo.getPhone());
        if (Objects.nonNull(userByPhone)){
            BigDecimal user_money = new BigDecimal(userByPhone.getUser_money());
            user_money = user_money.add(BigDecimal.valueOf(Double.parseDouble(bo.getMoney())));
            User user = new User();
            user.setPhone(bo.getPhone());
            user.setUser_money(String.valueOf(user_money));
            userMapper.updateUserByPhone(user);

            RedisUtils.del("user");
            List<User> allUser = userMapper.getAllUser();
            Map<Object, Object> userMap = new HashMap<>();
            for (User user1:allUser){
                userMap.put(String.valueOf(user1.getId()),user1);
            }
            RedisUtils.hmset("user",userMap,43200);

            return true;
        }
        return false;
    }


    @Override
    public void updateUserRedis() {
        RedisUtils.del("user");
        List<User> allUser = userMapper.getAllUser();
        Map<Object, Object> userMap = new HashMap<>();
        for (User user1:allUser){
            userMap.put(String.valueOf(user1.getId()),user1);
        }
        RedisUtils.hmset("user",userMap,43200);
    }


}
