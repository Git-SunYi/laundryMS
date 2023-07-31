package com.sunyi.laundryms.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.sunyi.laundryms.common.dto.CommonDataDTO;
import com.sunyi.laundryms.common.result.utils.PageUtils;
import com.sunyi.laundryms.common.result.utils.PageUtils2;
import com.sunyi.laundryms.common.utils.DateUtils;
import com.sunyi.laundryms.domain.bo.CheckInBO;
import com.sunyi.laundryms.domain.bo.PurchaseBO;
import com.sunyi.laundryms.domain.entity.*;
import com.sunyi.laundryms.domain.mapper.IndexMapper;
import com.sunyi.laundryms.domain.vo.*;
import com.sunyi.laundryms.service.IIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class IndexServiceImpl implements IIndexService {
    @Autowired
    IndexMapper indexMapper;

    @Override
    public PageUtils getAllOrderByPageAndPhone(PageUtils page, Order order) {
        page.setCountSum(indexMapper.countOrder(order));
        page.setData(indexMapper.getAllOrderByPageAndPhone(order,(page.getPageNo()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    public PageUtils getOrderByKeyWordAndPhone(String keyWord, String phone, PageUtils page) {
        Order order = new Order();
        order.setPhone(phone);
        order.setKeyWord(keyWord);
        order.setStart((page.getPageNo()-1)*page.getPageSize());
        order.setSize(page.getPageSize());
        page.setCountSum(indexMapper.countOrder(order));
        page.setData(indexMapper.getOrderByKeyWordAndPhone(order));
        return page;
    }

    @Override
    public int addOrderByPone(Order order) {
        User userByPhone = indexMapper.findUserByPhone(order.getPhone());
        order.setName(userByPhone.getName());
        order.setDate(new Date());
        double money=0.00;
        BigDecimal totalMoney = new BigDecimal(Double.toString(money));
        totalMoney=totalMoney.add(BigDecimal.valueOf(order.getClothes_num()).multiply(BigDecimal.valueOf(2)));
        if (order.getCategory().equals("滚筒洗衣机")){
            totalMoney=totalMoney.add(BigDecimal.valueOf(1));
        }
        if (order.getCollect().equals("上门")){
            totalMoney=totalMoney.add(BigDecimal.valueOf(2));
        }
        if (order.getDelivery().equals("上门")){
            totalMoney=totalMoney.add(BigDecimal.valueOf(2));
        }
        if (userByPhone.getUser_type().equals("普通用户") || userByPhone.getUser_type().equals("管理员")) {
            BigDecimal prefTotalMoney=totalMoney.multiply(BigDecimal.valueOf(1));
            order.setMoney(String.valueOf(prefTotalMoney));
            if (Double.parseDouble(userByPhone.getUser_money()) >= Double.parseDouble(String.valueOf(prefTotalMoney))) {
                BigDecimal user_money = new BigDecimal(userByPhone.getUser_money());
                user_money = user_money.subtract(prefTotalMoney);
                User user = new User();
                user.setUser_money(String.valueOf(user_money));
                user.setPhone(order.getPhone());
                indexMapper.updateUserMoneyByPhone(user);
                if (indexMapper.addOrderByPhone(order) > 0) {
                    return 1;
                }
            }
        }
        if (userByPhone.getUser_type().equals("会员")) {
            BigDecimal prefTotalMoney=totalMoney.multiply(BigDecimal.valueOf(0.8));
            order.setMoney(String.valueOf(prefTotalMoney));
            if (Double.parseDouble(userByPhone.getUser_money()) >= Double.parseDouble(String.valueOf(prefTotalMoney))) {
                BigDecimal user_money = new BigDecimal(userByPhone.getUser_money());
                user_money = user_money.subtract(prefTotalMoney);
                User user = new User();
                user.setUser_money(String.valueOf(user_money));
                user.setPhone(order.getPhone());
                indexMapper.updateUserMoneyByPhone(user);
                if (indexMapper.addOrderByPhone(order) > 0) {
                    return 1;
                }
            }
        }
        if (userByPhone.getUser_type().equals("超级会员")) {
            BigDecimal prefTotalMoney=totalMoney.multiply(BigDecimal.valueOf(0.5));
            order.setMoney(String.valueOf(prefTotalMoney));
            if (Double.parseDouble(userByPhone.getUser_money()) >= Double.parseDouble(String.valueOf(prefTotalMoney))) {
                BigDecimal user_money = new BigDecimal(userByPhone.getUser_money());
                user_money = user_money.subtract(prefTotalMoney);
                User user = new User();
                user.setUser_money(String.valueOf(user_money));
                user.setPhone(order.getPhone());
                indexMapper.updateUserMoneyByPhone(user);
                if (indexMapper.addOrderByPhone(order) > 0) {
                    return 1;
                }
            }
        }
        return 0;
    }


    @Override
    public PageUtils2 findAllComment(PageUtils2 page2) {
        page2.setCountSum(indexMapper.countComment());
        CommonDataDTO commonDataDTO = new CommonDataDTO();
        commonDataDTO.setStart((page2.getPageNo2()-1)*page2.getPageSize());
        commonDataDTO.setSize(page2.getPageSize());
        List<CommentVO> allComment = indexMapper.findAllComment(commonDataDTO);
        for(CommentVO commentVO:allComment){
            try {
                commentVO.setTimeDifference(DateUtils.calculateTimeDifferenceBySimpleDateFormat(commentVO.getCreate_time()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        page2.setData2(allComment);
        return page2;
    }

    @Override
    public int addComment(Comment comment) {
        comment.setCreateTime(new Date());
        return indexMapper.addComment(comment);
    }

    @Override
    public StatisticsVO statistics() {
        StatisticsVO statisticsVO = new StatisticsVO();
        statisticsVO.setToDaysOrderNum(indexMapper.toDaysOrderNum());
        statisticsVO.setToDaysMoney(indexMapper.toDaysMoney());
        statisticsVO.setTotalOrderNum(indexMapper.countOrder2());
        statisticsVO.setTotalMoney(indexMapper.totalMoney());
        return statisticsVO;
    }

    @Override
    public boolean purchase(PurchaseBO bo) {
        User user = indexMapper.findUserByPhone(bo.getPhone());
        if (StringUtil.isNotEmpty(bo.getUserType())) {
            if (bo.getUserType().equals("管理员")) {
                return false;
            }
            if (bo.getUserType().equals("普通用户")) {
                if (Double.parseDouble(user.getUser_money()) >= Double.parseDouble(bo.getCardMoney())) {
                    BigDecimal userMoney = new BigDecimal(user.getUser_money());
                    userMoney = userMoney.subtract(BigDecimal.valueOf(Double.parseDouble(bo.getCardMoney())));
                    User user1 = new User();
                    user1.setUser_money(String.valueOf(userMoney));
                    user1.setUser_type(bo.getCardType());
                    user1.setPhone(bo.getPhone());
                    indexMapper.updateUserMoneyByPhone(user1);
                    return true;
                }
            }
            if (bo.getUserType().equals("会员") && bo.getCardType().equals("超级会员")) {
                if (Double.parseDouble(user.getUser_money()) >= Double.parseDouble(bo.getCardMoney())) {
                    BigDecimal userMoney = new BigDecimal(user.getUser_money());
                    userMoney = userMoney.subtract(BigDecimal.valueOf(Double.parseDouble(bo.getCardMoney())));
                    User user1 = new User();
                    user1.setUser_money(String.valueOf(userMoney));
                    user1.setUser_type(bo.getCardType());
                    user1.setPhone(bo.getPhone());
                    indexMapper.updateUserMoneyByPhone(user1);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public User findUserByPhone(PurchaseBO bo) {
        return indexMapper.findUserByPhone(bo.getPhone());
    }


    @Override
    public List<CommentVO> findComment() {
        List<CommentVO> allComment = indexMapper.findComment();
        for(CommentVO commentVO:allComment){
            try {
                commentVO.setTimeDifference(DateUtils.calculateTimeDifferenceBySimpleDateFormat(commentVO.getCreate_time()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return allComment;
    }

    @Override
    public PageUtils findOrder(PageUtils page) {
        page.setCountSum(indexMapper.countOrder2());
        CommonDataDTO commonDataDTO = new CommonDataDTO();
        commonDataDTO.setStart((page.getPageNo()-1)*page.getPageSize());
        commonDataDTO.setSize(page.getPageSize());
        List<OrderVO> allOrder = indexMapper.findAllOrder(commonDataDTO);
        for(OrderVO orderVO:allOrder){
            try {
                orderVO.setTimeDifference(DateUtils.calculateTimeDifferenceBySimpleDateFormat(orderVO.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        page.setData(allOrder);
        return page;
    }


    @Override
    public String addCheckInDay(CheckInBO bo) {
        if (StringUtils.isEmpty(bo.getPhone())){
            return "LOGIN_FAIL";
        }
        CheckIn checkIn = new CheckIn();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BeanUtils.copyProperties(bo, checkIn);
        try {
            List<CheckIn> checkInList = indexMapper.findCheckIn(checkIn);
            for (CheckIn check:checkInList){
                if (sdf.format(check.getCheckInDate()).equals(sdf.format(sdf.parse(bo.getCheckInDate())))){
                    return "CHECK_IN_FAIL";
                }
            }
            checkIn.setCheckInDate(sdf.parse(bo.getCheckInDate()));
            indexMapper.addCheckInDay(checkIn);
            int continuousDays=0;
            for (int i=0;i<2;i++){
                if (Objects.nonNull(indexMapper.continuousCheckInDays(checkIn))) {
                    continuousDays = indexMapper.continuousCheckInDays(checkIn).getContinuousDays();
                }
            }
            BigDecimal reward = new BigDecimal("0.00");
            CheckInAdmin chekInAdmin = indexMapper.findChekInAdmin();
            for (int checkIns=1;checkIns<=continuousDays;checkIns++){
                if (checkIns == 1){
                    reward = reward.add(BigDecimal.valueOf(chekInAdmin.getFirstReward()));
                }
                if (checkIns > 1 && checkIns <= chekInAdmin.getContinuousDays()){
                    reward = reward.add(BigDecimal.valueOf(chekInAdmin.getRewardAdd()));
                }
                if (checkIns > chekInAdmin.getContinuousDays()){
                    reward = reward.add(BigDecimal.valueOf(chekInAdmin.getContinuousReward()));
                }
            }
            checkIn.setReward(Double.parseDouble(String.valueOf(reward)));
            User userByPhone = indexMapper.findUserByPhone(bo.getPhone());
            if (Objects.nonNull(userByPhone)) {
                BigDecimal userMoney = new BigDecimal(userByPhone.getUser_money());
                userMoney = userMoney.add(reward);
                User user = new User();
                user.setPhone(bo.getPhone());
                user.setUser_money(String.valueOf(userMoney));
                indexMapper.updateUserMoneyByPhone(user);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        indexMapper.addCheckInDay(checkIn);
        return "SUCCESS";
    }

    @Override
    public CheckInVO findCheckInDate(CheckInBO bo) {
        if (StringUtils.isEmpty(bo.getPhone())){
            return new CheckInVO();
        }
        CheckIn checkIn = new CheckIn();
        BeanUtils.copyProperties(bo, checkIn);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<CheckIn> checkInList = indexMapper.findCheckIn(checkIn);
        List<Integer> voList = new ArrayList<>();
        List<String> voList2 = new ArrayList<>();
        List<Double> voList3 = new ArrayList<>();
        for (CheckIn check:checkInList){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(check.getCheckInDate());
            int day = calendar.get(Calendar.DATE);
            voList.add(day);
            voList2.add(sdf.format(check.getCheckInDate()));
            voList3.add(check.getReward());
        }
        List<CheckInVO> checkInVOList=null;
        for (int i=0;i<2;i++) {
            checkInVOList = indexMapper.continuousCheckInDaysList(checkIn);
        }
        List<Integer> voList4 = new ArrayList<>();
        for (CheckInVO check:checkInVOList){
            voList4.add(check.getContinuousDaysList());
        }
        CheckInVO checkInVO = new CheckInVO();
        checkInVO.setDays(voList);
        checkInVO.setCheckInDates(voList2);
        checkInVO.setRewards(voList3);
        checkInVO.setTotalReward(indexMapper.findTotalReward(checkIn).getTotalReward());
        checkInVO.setContinuousDaysLists(voList4);
        for (int i=0;i<2;i++){
            if (indexMapper.continuousCheckInDays(checkIn)==null){
                break;
            }
            checkInVO.setContinuousDays(indexMapper.continuousCheckInDays(checkIn).getContinuousDays());
        }
        checkInVO.setTotalDays(indexMapper.totalCheckInDays(checkIn).getTotalDays());
        checkInVO.setCurrentMonthDays(indexMapper.currentMonthCheckInDays(checkIn).getCurrentMonthDays());
        return checkInVO;
    }

    @Override
    public CheckInVO findTodayReward(CheckInBO bo) {
        if (StringUtils.isEmpty(bo.getPhone())){
            return new CheckInVO();
        }
        CheckIn checkIn = new CheckIn();
        BeanUtils.copyProperties(bo, checkIn);
        CheckInVO checkInVO = new CheckInVO();
        if (Objects.isNull(indexMapper.findTodayReward(checkIn)) || Objects.isNull(indexMapper.continuousCheckInDays(checkIn))){
            return new CheckInVO();
        }
        for (int i=0;i<2;i++){
            checkInVO.setContinuousDays(indexMapper.continuousCheckInDays(checkIn).getContinuousDays());
        }
        checkInVO.setTodayReward(indexMapper.findTodayReward(checkIn).getTodayReward());
        return checkInVO;
    }

    @Override
    public CheckInAdminVO findChekInAdmin() {
        CheckInAdmin chekInAdmin = indexMapper.findChekInAdmin();
        CheckInAdminVO checkInAdminVO = new CheckInAdminVO();
        BeanUtils.copyProperties(chekInAdmin,checkInAdminVO);
        return checkInAdminVO;
    }

}
