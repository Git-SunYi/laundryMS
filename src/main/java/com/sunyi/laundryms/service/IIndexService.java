package com.sunyi.laundryms.service;

import com.sunyi.laundryms.domain.bo.CheckInBO;
import com.sunyi.laundryms.domain.bo.PurchaseBO;
import com.sunyi.laundryms.common.result.utils.PageUtils;
import com.sunyi.laundryms.common.result.utils.PageUtils2;
import com.sunyi.laundryms.domain.entity.Comment;
import com.sunyi.laundryms.domain.entity.Order;
import com.sunyi.laundryms.domain.entity.User;
import com.sunyi.laundryms.domain.vo.CheckInAdminVO;
import com.sunyi.laundryms.domain.vo.CheckInVO;
import com.sunyi.laundryms.domain.vo.CommentVO;
import com.sunyi.laundryms.domain.vo.StatisticsVO;

import java.text.ParseException;
import java.util.List;

public interface IIndexService {
    //根据手机号分页查询订单信息
    PageUtils getAllOrderByPageAndPhone(PageUtils page, Order order);
    //搜索框根据手机号和关键字搜索订单信息
    PageUtils getOrderByKeyWordAndPhone(String keyWord, String phone, PageUtils page);
    //查询所有评论
    List<CommentVO> findComment();
    //查询所有订单
    PageUtils findOrder(PageUtils page);

    //用户根据手机号下单
    int addOrderByPone(Order order);

    //分页查询所有评论
    PageUtils2 findAllComment(PageUtils2 page2);
    //发评论
    int addComment(Comment comment);

    //统计订单数量和销售额
    StatisticsVO statistics();

    //购买会员卡
    boolean purchase(PurchaseBO purchaseBO);
    User findUserByPhone(PurchaseBO purchaseBO);

    //增加签到日期
    String addCheckInDay(CheckInBO bo) throws ParseException;
    //查询签到日期
    CheckInVO findCheckInDate(CheckInBO bo);
    //查询签到今日奖励
    CheckInVO findTodayReward(CheckInBO bo);

    //签到奖励规则
    CheckInAdminVO findChekInAdmin();
}
