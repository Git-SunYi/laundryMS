package com.sunyi.laundryms.controller;

import com.sunyi.laundryms.common.result.ResponseData;
import com.sunyi.laundryms.common.result.utils.PageUtils;
import com.sunyi.laundryms.common.result.utils.PageUtils2;
import com.sunyi.laundryms.domain.bo.CheckInBO;
import com.sunyi.laundryms.domain.bo.PurchaseBO;
import com.sunyi.laundryms.domain.entity.Comment;
import com.sunyi.laundryms.domain.entity.LaundryKnowledge;
import com.sunyi.laundryms.domain.entity.Order;
import com.sunyi.laundryms.domain.entity.User;
import com.sunyi.laundryms.service.IAdminService;
import com.sunyi.laundryms.service.IIndexService;
import com.sunyi.laundryms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    IIndexService indexService;

    @Autowired
    IAdminService adminService;

    @Autowired
    IUserService userService;

    //跳转到首页
    @GetMapping("")
    public String index(HttpServletRequest request,PageUtils page){
        request.setAttribute("statistics",indexService.statistics());
        page.setPageSize(8);
        request.setAttribute("orderListByPage",adminService.getAllOrderByPage(page));
        return "index";
    }


    //跳转到会员卡页
    @GetMapping("/membership_card")
    public String membership_card(){
        return "membership_card";
    }

    //会员卡购买
    @PostMapping(value = "/purchase",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResponseData purchase(@RequestBody PurchaseBO bo, HttpSession session){
        if (indexService.purchase(bo)){
            session.setAttribute("user_type",indexService.findUserByPhone(bo).getUser_type());
            userService.updateUserRedis();
            return ResponseData.ok();
        }
        User user = indexService.findUserByPhone(bo);
        if (Double.parseDouble(user.getUser_money()) < Double.parseDouble(bo.getCardMoney())) {
            return ResponseData.fail("余额不足！");
        }
        return ResponseData.fail("您的身份类型为【"+user.getUser_type()+"】，暂时不能购买该会员卡！");
    }


    //搜索框搜索设备信息
    @GetMapping("/getDeviceByKeyWord")
    public String getDeviceByKeyWord(String keyWord,PageUtils page,HttpServletRequest request){
        page.setPageSize(10);
        request.setAttribute("deviceListByPage",adminService.getDeviceByKeyWord(keyWord,page));
        return "device_information";
    }

    //跳转到设备信息页
    @GetMapping("/device_information")
    public String device_information(PageUtils page,HttpServletRequest request){
        page.setPageSize(10);
        request.setAttribute("deviceListByPage",adminService.getAllDeviceByPage(page));
        return "device_information";
    }


    //搜索框根据手机号和关键字搜索订单信息
    @GetMapping("/getOrderByKeyWordAndPhone")
    public String getOrderByKeyWord(String keyWord, String phone,PageUtils page,PageUtils2 page2,HttpServletRequest request){
        page.setPageSize(10);
        request.setAttribute("orderListByPageAndPhone",indexService.getOrderByKeyWordAndPhone(keyWord,phone,page));
        page2.setPageSize(3);
        request.setAttribute("commentPageInfo",indexService.findAllComment(page2));
        return "laundry_service";
    }

    //跳转到洗衣服务页
    @GetMapping("/laundry_service")
    public String laundry_service(PageUtils page, PageUtils2 page2, HttpServletRequest request, Order order){
        page.setPageSize(5);
        request.setAttribute("orderListByPageAndPhone",indexService.getAllOrderByPageAndPhone(page, order));
        page2.setPageSize(3);
        request.setAttribute("commentPageInfo",indexService.findAllComment(page2));
        return "laundry_service";
    }

    //下单
    @PostMapping(value = "/addOrderByPone",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData addOrderByPone(Order order){ //@RequestBody只能接收json格式的数据
        if (indexService.addOrderByPone(order)>0){
            userService.updateUserRedis();
            return ResponseData.ok();
        }
        return ResponseData.fail();
    }

    //发评论
    @PostMapping(value = "/addComment",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData addComment(@RequestBody Comment comment){
        if (indexService.addComment(comment)>0){
            return ResponseData.ok();
        }
        return ResponseData.fail();
    }

    //查询所有评论
    @PostMapping(value = "/findComment",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData findComment(PageUtils2 page2){
        page2.setPageSize(4);
        return ResponseData.ok(indexService.findAllComment(page2));
    }

    //查询所有订单信息
    @PostMapping(value = "/findOrder",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData findOrder(PageUtils page){
        page.setPageSize(4);
        return ResponseData.ok(indexService.findOrder(page));
    }


    //跳转到优惠活动页
    @GetMapping("/preferential_activities")
    public String preferential_activities(@RequestParam String phone,HttpSession session){
        session.setAttribute("phone",phone);
        return "preferential_activities";
    }

    //查询签到日期
    @PostMapping(value = "/findCheckInDate",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResponseData findCheckInDate(HttpSession session){
        CheckInBO bo = new CheckInBO();
        String phone = (String) session.getAttribute("phone");
        bo.setPhone(phone);
        return ResponseData.ok(indexService.findCheckInDate(bo));
    }

    //增加签到日期
    @PostMapping("/addCheckInDay")
    @ResponseBody
    public ResponseData addCheckInDay(@RequestBody CheckInBO bo,HttpSession session) throws ParseException {
        String phone = (String) session.getAttribute("phone");
        bo.setPhone(phone);
        if (indexService.addCheckInDay(bo).equals("SUCCESS")){
            userService.updateUserRedis();
            return ResponseData.ok();
        }
        if (indexService.addCheckInDay(bo).equals("LOGIN_FAIL")){
            return ResponseData.fail("签到失败，请先登录！");
        }
        return ResponseData.fail("今日已签到，请勿重复签到！");
    }

    //查询签到今日奖励
    @PostMapping(value = "/findTodayReward",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResponseData findTodayReward(HttpSession session){
        CheckInBO bo = new CheckInBO();
        String phone = (String) session.getAttribute("phone");
        bo.setPhone(phone);
        return ResponseData.ok(indexService.findTodayReward(bo));
    }

    //签到奖励规则
    @PostMapping(value = "/findChekInAdmin",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResponseData findChekInAdmin(){
        return ResponseData.ok(indexService.findChekInAdmin());
    }


    //跳转到洗衣常识页
    @GetMapping("/laundry_knowledge")
    public String laundry_knowledge(HttpServletRequest request){
        LaundryKnowledge laundryKnowledgeUrl = adminService.findLaundryKnowledgeUrl();
        request.setAttribute("LaundryKnowledge",laundryKnowledgeUrl);
        return "laundry_knowledge";
    }

}
