package com.sunyi.laundryms.controller;

import com.sunyi.laundryms.domain.bo.CheckInAdminBO;
import com.sunyi.laundryms.domain.entity.Device;
import com.sunyi.laundryms.domain.entity.LaundryKnowledge;
import com.sunyi.laundryms.domain.entity.User;
import com.sunyi.laundryms.common.result.ResponseData;
import com.sunyi.laundryms.service.IAdminService;
import com.sunyi.laundryms.service.IIndexService;
import com.sunyi.laundryms.service.IUserService;
import com.sunyi.laundryms.common.result.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IUserService userService;
    @Autowired
    IAdminService adminService;
    @Autowired
    IIndexService indexService;

    //搜索框搜索用户
    @GetMapping("/getUserByKeyWord")
    public String getUserByKeyWord(String keyWord,HttpServletRequest request,PageUtils page){
        page.setPageSize(10);
        request.setAttribute("userListByPage",adminService.getUserByKeyWord(keyWord,page));
        return "admin_user";
    }

    //跳转到用户管理页
    @GetMapping("/adminUser")
    public String adminUser(HttpServletRequest request,PageUtils page){
        page.setPageSize(10);
        request.setAttribute("userListByPage",adminService.getAllUserByPage(page));
        return "admin_user";
    }

    //删除用户
    @PostMapping("/deleteUser")
    @ResponseBody
    public ResponseData deleteUser(String phone){
        if (adminService.deleteUser(phone)>0){
            return ResponseData.ok("删除成功！");
        }
        return ResponseData.fail("删除失败！");
    }


    //搜索框搜索店员
    @GetMapping("/getClerkByKeyWord")
    public String getClerkByKeyWord(String keyWord,HttpServletRequest request,PageUtils page){
        page.setPageSize(10);
        request.setAttribute("adminListByPage",adminService.getClerkByKeyWord(keyWord,page));
        return "admin_clerk";
    }

    //跳转到店员管理页
    @GetMapping("/adminClerk")
    public String toClerk(HttpServletRequest request, PageUtils page) {
//        request.setAttribute("adminList",userService.getAllAdmin());
        page.setPageSize(10);
        request.setAttribute("adminListByPage", adminService.getAllAdminByPage(page));
        return "admin_clerk";
    }

    //解雇店员
    @PostMapping("/deleteAdmin")
    @ResponseBody
    public ResponseData deleteAdmin(String phone) {
        if (adminService.deleteAdmin(phone) > 0) {
            return ResponseData.ok("解雇成功！");
        }
        return ResponseData.fail("解雇失败！");
    }

    //跳转到店员注册页
    @GetMapping("/adminRegister")
    public String adminRegister(HttpSession session){
        return "admin_register";
    }

    //添加店员
    @PostMapping("/addAdmin")
    public String addAdmin(String name, String phone2, String password3, String password4, User user, HttpSession session){
        user.setName(name);
        user.setPhone(phone2);
        user.setPassword(password3);
        session.setAttribute("user",userService.getUserByPhone(phone2));
        session.setAttribute("password3",password3);
        session.setAttribute("password4",password4);
        if (userService.getUserByPhone(phone2)==null){
            if (password3.equals(password4)){
                if (adminService.addAdmin(user)>0){
                    System.out.println("注册成功");
                    return "redirect:/admin/adminClerk";
                }
            }
        }
        System.out.println("注册失败");
        return "redirect:/admin/adminRegister";
    }


    //搜索框搜索设备
    @GetMapping("/getDeviceByKeyWord")
    public String getDeviceByKeyWord(String keyWord,PageUtils page,HttpServletRequest request){
        page.setPageSize(10);
        request.setAttribute("deviceListByPage",adminService.getDeviceByKeyWord(keyWord,page));
        return "admin_device";
    }

    //跳转到设备管理页
    @GetMapping("/adminDevice")
    public String adminDevice(PageUtils page,HttpServletRequest request){
        page.setPageSize(10);
        request.setAttribute("deviceListByPage",adminService.getAllDeviceByPage(page));
        return "admin_device";
    }

    //删除设备
    @PostMapping("/deleteDevice")
    @ResponseBody
    public ResponseData deleteDevice(int id){
        if (adminService.deleteDevice(id)>0){
            return ResponseData.ok("删除成功！");
        }
        return ResponseData.fail("删除失败！");
    }

    //跳转到设备添加页
    @GetMapping("/toAddDevice")
    public String toAddDevice(){
        return "admin_deviceAdd";
    }

    //添加设备
    @PostMapping("/addDevice")
    @ResponseBody
    public ResponseData addDevice(String category,String automation,String drainage,
                                  String capacity,String energy_efficiency){
        Device device = new Device(0,category,automation,drainage,capacity,energy_efficiency);
        if (adminService.addDevice(device)>0){
            return ResponseData.ok("添加成功！");
        }
        return ResponseData.fail("添加失败！");
    }


    //搜索框搜索订单
    @GetMapping("/getOrderByKeyWord")
    public String getOrderByKeyWord(String keyWord,PageUtils page,HttpServletRequest request){
        page.setPageSize(10);
        request.setAttribute("orderListByPage",adminService.getOrderByKeyWord(keyWord,page));
        return "admin_order";
    }

    //跳转到订单页
    @GetMapping("/adminOrder")
    public String adminOrder(HttpServletRequest request,PageUtils page){
        page.setPageSize(10);
        request.setAttribute("orderListByPage",adminService.getAllOrderByPage(page));
        return "admin_order";
    }

    //删除订单记录
    @PostMapping("/deleteOrder")
    @ResponseBody
    public ResponseData deleteOrder(int id){
        return ResponseData.ok(adminService.deleteOrder(id));
    }

    //洗衣结束后delivery为上门的state->洗后待送货，delivery为到店的state->洗后待取货
    //取货结束state->洗前取货完成，上们签收state->洗后送货完成，到店签收state->洗后取货完成
    @PostMapping("/updateOrderState")
    @ResponseBody
    public ResponseData updateOrderState(@RequestParam int id){
        return ResponseData.ok(adminService.updateOrderState(id));
    }


    //搜索框搜索配送
    @GetMapping("/getDistributionByKeyWord")
    public String getDistributionByKeyWord(String keyWord,PageUtils page,HttpServletRequest request){
        page.setPageSize(10);
        request.setAttribute("orderListByPage",adminService.getOrderByKeyWord(keyWord,page));
        return "admin_distribution";
    }
    //跳转到衣物配送页
    @GetMapping("/adminDistribution")
    public String adminDistribution(PageUtils page,HttpServletRequest request){
        page.setPageSize(10);
        request.setAttribute("orderListByPage",adminService.getAllOrderByPage(page));
        return "admin_distribution";
    }


    //跳转到管理洗衣常识页
    @GetMapping("/adminLaundryKnowledge")
    public String adminLaundryKnowledge(HttpServletRequest request){
        LaundryKnowledge laundryKnowledgeUrl = adminService.findLaundryKnowledgeUrl();
        request.setAttribute("LaundryKnowledge",laundryKnowledgeUrl);
        return "admin_laundryKnowledge";
    }

    //增加洗衣常识url
    @PostMapping(value = "/addLaundryKnowledgeUrl",produces = {"application/json;charset=UTF-8"})
    @ResponseBody                                    //produces:返回给前端的数据类型
    public ResponseData addLaundryKnowledgeUrl(@RequestBody LaundryKnowledge laundryKnowledge) {
                                        //@RequestBody不支持application/x-www-form-urlencoded键值对的形式
                                       //@RequestBody只能接收json格式的数据
                                        //@RequestParam postman需要用form-data传值，不能用raw的json传值
        if (adminService.addLaundryKnowledgeUrl(laundryKnowledge)>0){
            return ResponseData.ok(laundryKnowledge);
        }else {
            return ResponseData.fail();
        }
    }


    //跳转优惠活动管理页
    @GetMapping("/adminPreferentialActivities")
    public String toAdminPreferentialActivities(HttpServletRequest request){
        request.setAttribute("checkInAdminVO",indexService.findChekInAdmin());
        return "admin_preferentialActivities";
    }

    //增加签到规则
    @RequestMapping(value = "/addCheckInAdmin",produces = {"application/json;charset=utf-8"})
    public String addCheckInAdmin(CheckInAdminBO bo){
        adminService.addCheckInAdmin(bo);
        return "redirect:/admin/adminPreferentialActivities";
    }

}

