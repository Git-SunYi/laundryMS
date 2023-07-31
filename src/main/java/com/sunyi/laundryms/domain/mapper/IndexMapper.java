package com.sunyi.laundryms.domain.mapper;

import com.sunyi.laundryms.common.dto.CommonDataDTO;
import com.sunyi.laundryms.domain.entity.*;
import com.sunyi.laundryms.domain.vo.CheckInVO;
import com.sunyi.laundryms.domain.vo.CommentVO;
import com.sunyi.laundryms.domain.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IndexMapper {
    //根据手机号分页模糊查询订单所有信息
//    @Select("select * from `order` where phone=#{phone} and concat(name,phone,category,address,clothes_num,date,money,collect,delivery,state) like concat('%',#{keyWord},'%') limit #{start},#{size}")
    List<Order> getOrderByKeyWordAndPhone(Order order);
    //根据手机号分页查询订单信息
//    @Select("select * from `order` where phone=#{phone} limit #{start},#{size}")
    List<Order> getAllOrderByPageAndPhone(Order order,@Param("start")int start,@Param("size")int size);
    //根据手机号查询订单总数量
    @Select("select count(*) from `order` where phone=#{phone}")
    int countOrder(Order order);
    //查询订单总数
    int countOrder2();
    //分页查询所有订单信息
    List<OrderVO> findAllOrder(CommonDataDTO commonDataDTO);

    //用户根据手机号下单
    int addOrderByPhone(Order order);
    //根据手机号查用户信息
    User findUserByPhone(@Param("phone") String phone);
    //根据手机号更新余额
    void updateUserMoneyByPhone(User user);

    //分页查询所有评论
    List<CommentVO> findAllComment(CommonDataDTO commonDataDTO);
    //查询评论数量
    int countComment();
    //发评论
    int addComment(Comment comment);
    //查询所有评论
    List<CommentVO> findComment();

    int toDaysOrderNum();
    Double toDaysMoney();
    Double totalMoney();

    //根据手机号查签到信息
    List<CheckIn> findCheckIn(CheckIn checkIn);
    //根据手机号查今日签到奖励
    CheckInVO findTodayReward(CheckIn checkIn);
    //根据手机号查总签到奖励
    CheckInVO findTotalReward(CheckIn checkIn);
    //本月签到天数
    CheckInVO currentMonthCheckInDays(CheckIn checkIn);
    //总签到天数
    CheckInVO totalCheckInDays(CheckIn checkIn);
    //本月连续签到天数
    CheckInVO continuousCheckInDays(CheckIn checkIn);
    //增加签到日期
    int addCheckInDay(CheckIn checkIn);
    //本月连续签到天数列表
    List<CheckInVO> continuousCheckInDaysList(CheckIn checkIn);

    //签到奖励规则
    CheckInAdmin findChekInAdmin();
}
