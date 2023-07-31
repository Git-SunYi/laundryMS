package com.sunyi.laundryms.service;

import com.sunyi.laundryms.domain.bo.CheckInAdminBO;
import com.sunyi.laundryms.domain.entity.Device;
import com.sunyi.laundryms.domain.entity.LaundryKnowledge;
import com.sunyi.laundryms.domain.entity.User;
import com.sunyi.laundryms.common.result.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAdminService {
    //分页模糊查询用户类型为管理员的信息
    PageUtils getUserByKeyWord(@Param("keyWord")String keyWord,PageUtils page);
    //分页查询用户所有信息
    PageUtils getAllUserByPage(PageUtils page);
    //根据手机号删除用户
    int deleteUser(@Param("phone")String phone);

    //分页模糊查询用户类型为管理员的信息
    PageUtils getClerkByKeyWord(@Param("keyWord")String keyWord,PageUtils page);
    //查询用户类型为管理员的所有信息
    List<User> getAllAdmin();
    //分页查询用户类型为管理员的所有信息
    PageUtils getAllAdminByPage(PageUtils page);
    //根据手机号删除用户类型为管理员的信息
    int deleteAdmin(@Param("phone") String phone);
    //添加用户类型为管理员的用户
    int addAdmin(User user);

    //分页模糊查询设备所有信息
    PageUtils getDeviceByKeyWord(@Param("keyWord")String keyWord,PageUtils page);
    //分页查询设备信息
    PageUtils getAllDeviceByPage(PageUtils page);
    //删除设备
    int deleteDevice(@Param("id")int id);
    //添加设备
    int addDevice(Device device);

    //分页模糊查询设备所有信息
    PageUtils getOrderByKeyWord(String keyWord,PageUtils page);
    //分页查询订单信息
    PageUtils getAllOrderByPage(PageUtils page);
    //删除订单记录
    boolean deleteOrder(int id);
    //洗衣结束后delivery为上门的state->洗后待送货，delivery为到店的state->洗后待取货
    //取货结束state->洗前取货完成，上们签收state->洗后送货完成，到店签收state->洗后取货完成
    boolean updateOrderState(int id);

    //增加洗衣常识url
    int addLaundryKnowledgeUrl(LaundryKnowledge laundryKnowledge);
    //查询当前的洗衣常识url
    LaundryKnowledge findLaundryKnowledgeUrl();

    //增加签到规则
    int addCheckInAdmin(CheckInAdminBO bo);
}
