package com.sunyi.laundryms.domain.mapper;

import com.sunyi.laundryms.domain.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper {
    //分页模糊查询用户类型为管理员的信息
    @Select("select * from user where user_type!='管理员' and concat(name,account,gender,phone,user_type) like concat('%',#{keyWord},'%') limit #{start},#{size}")
    List<User> getUserByKeyWord(@Param("keyWord")String keyWord,@Param("start")int start,@Param("size")int size);
    //分页查询用户所有信息
    @Select("select * from user where user_type!='管理员' limit #{start},#{size}")
    List<User> getAllUserByPage(@Param("start")int start, @Param("size")int size);
    //查询用户的总数量
    @Select("select count(*) from user where user_type!='管理员'")
    int countUser();
    //根据手机号删除用户
    @Delete("delete from user where phone=#{phone}")
    int deleteUser(@Param("phone")String phone);

    //分页模糊查询用户类型为管理员的信息
    @Select("select * from user where user_type='管理员' and concat(name,account,gender,phone,user_type) like concat('%',#{keyWord},'%') limit #{start},#{size}")
    List<User> getClerkByKeyWord(@Param("keyWord")String keyWord,@Param("start")int start,@Param("size")int size);
    //查询用户类型为管理员的所有信息
    @Select("select * from user where user_type='管理员'")
    List<User> getAllAdmin();
    //查询用户类型为管理员的总数量
    @Select("select count(*) from user where user_type='管理员'")
    int countAdmin();
    //分页查询用户类型为管理员的所有信息
    @Select("select * from user where user_type='管理员' limit #{start},#{size}")
    List<User> getAllAdminByPage(@Param("start")int start, @Param("size")int size);
    //根据手机号删除用户类型为管理员的信息
    @Delete("delete from user where user_type='管理员' and phone=#{phone}")
    int deleteAdmin(@Param("phone") String phone);
    //添加用户类型为管理员的用户
    @Insert("insert into user(name,phone,password,user_type) values(#{name},#{phone},#{password},'管理员') ")
    int addAdmin(@Param("name")String name,@Param("phone")String phone,@Param("password")String password);

    //分页模糊查询设备所有信息
    @Select("select * from device where concat(category,automation,drainage,capacity,energy_efficiency) like concat('%',#{keyWord},'%') limit #{start},#{size}")
    List<Device> getDeviceByKeyWord(@Param("keyWord")String keyWord,@Param("start")int start,@Param("size")int size);
    //分页查询设备信息
    @Select("select * from device where 1=1 limit #{start},#{size}")
    List<Device> getAllDeviceByPage(@Param("start")int start,@Param("size")int size);
    //设备总数量
    @Select("select count(*) from device")
    int countDevice();
    //删除设备
    @Delete("delete from device where id=#{id}")
    int deleteDevice(@Param("id")int id);
    //添加设备
    @Insert("insert into device(category,automation,drainage,capacity,energy_efficiency) values(#{category},#{automation},#{drainage},#{capacity},#{energy_efficiency})")
    int addDevice(@Param("category")String category,@Param("automation")String automation,@Param("drainage")String drainage,@Param("capacity")String capacity,@Param("energy_efficiency")String energy_efficiency);

    //分页模糊查询订单所有信息
    @Select("select * from `order` where concat(name,phone,category,address,clothes_num,date,money,collect,delivery,state) like concat('%',#{keyWord},'%') order by date desc limit #{start},#{size}")
    List<Order> getOrderByKeyWord(@Param("keyWord")String keyWord,@Param("start")int start,@Param("size")int size);
    //分页查询订单信息
    @Select("select * from `order` where 1=1 order by date desc limit #{start},#{size}")
    List<Order> getAllOrderByPage(@Param("start")int start,@Param("size")int size);
    //查询订单总数量
    @Select("select count(*) from `order`")
    int countOrder();
    //删除订单记录
    @Delete("delete from `order` where id=#{id}")
    int deleteOrder(int id);
    //根据id查询订单信息
    @Select("select * from `order` where id=#{id}")
    Order findOrderById(int id);
    //更新洗后状态
    @Update("update `order` set state=#{state} where id=#{id}")
    void updateOrderState(Order order);

    //增加洗衣常识url
    int addLaundryKnowledgeUrl(LaundryKnowledge laundryKnowledge);
    //设置所有洗衣常识的选中状态为0
    int updateAllLaundryKnowledge();
    //查询当前的洗衣常识url
    LaundryKnowledge findLaundryKnowledgeUrl();

    //增加签到规则
    int addCheckInAdmin(CheckInAdmin checkInAdmin);
}
