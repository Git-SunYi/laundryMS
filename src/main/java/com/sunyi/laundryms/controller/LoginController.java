package com.sunyi.laundryms.controller;

import com.sunyi.laundryms.common.result.ResponseData;
import com.sunyi.laundryms.common.utils.RedisUtils;
import com.sunyi.laundryms.domain.bo.UserBO;
import com.sunyi.laundryms.domain.entity.User;
import com.sunyi.laundryms.service.IUserService;
import com.sunyi.laundryms.web.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    IUserService userService;

    //跳转到登录界面
    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/authLogin")
    @ResponseBody
    public ResponseData authLogin(@RequestBody UserBO bo){
        User userByPhone = userService.getUserByPhone(bo.getPhoneOrAccount());
        String token = TokenUtil.sign(userByPhone);
        RedisUtils.set(String.valueOf(userByPhone.getId()),token);
        return ResponseData.ok(token);
    }

    //用户登录
    @GetMapping(value = "/user_login")
    public String login(UserBO bo,           //account和password是<form>表单里面的name字段内容，并由submit提交而来
                        HttpSession session) {
        userService.updateUserRedis();
        session.setAttribute("phoneOrAccount",bo.getPhoneOrAccount());
        session.setAttribute("password2",bo.getPassword());
        session.setAttribute("userByPhone",userService.getUserByPhone(bo.getPhoneOrAccount()));
//        session.setAttribute("userByAccount",userService.getUserByAccount(phoneOrAccount));
//        if (userService.getUserByAccount(phoneOrAccount) != null) { //账号登录
//            if (userService.getUserByAccount(phoneOrAccount).getPassword().equals(password)) {
//                session.setAttribute("name", userService.getUserByAccount(phoneOrAccount).getName());
//                session.setAttribute("password",userService.getUserByAccount(phoneOrAccount).getPassword());
//                session.setAttribute("phone",userService.getUserByAccount(phoneOrAccount).getPhone());
//                session.setAttribute("head_portrait",userService.getUserByAccount(phoneOrAccount).getHead_portrait());
//                session.setAttribute("user_type",userService.getUserByAccount(phoneOrAccount).getUser_type());
//                return "redirect:/index";
//            }
//        }
        if (userService.getUserByPhone(bo.getPhoneOrAccount()) != null) {  //手机号登录
            if (userService.getUserByPhone(bo.getPhoneOrAccount()).getPassword().equals(bo.getPassword())) {
                session.setAttribute("name", userService.getUserByPhone(bo.getPhoneOrAccount()).getName());
                session.setAttribute("password",userService.getUserByPhone(bo.getPhoneOrAccount()).getPassword());
                session.setAttribute("phone",userService.getUserByPhone(bo.getPhoneOrAccount()).getPhone());
                session.setAttribute("head_portrait",userService.getUserByPhone(bo.getPhoneOrAccount()).getHead_portrait());
                session.setAttribute("user_type",userService.getUserByPhone(bo.getPhoneOrAccount()).getUser_type());
//                session.setAttribute("token",token);
                return "redirect:/index";
            }
        }
        return "redirect:/login/toLogin";
    }

    //退出登录
    @GetMapping(value = "/login_out")
    public String loginOut(HttpSession session) {
        session.invalidate();//该方法可以清除session对象中的所有信息。
        return "redirect:/index";
    }

    //跳转到注册界面
    @GetMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    //用户注册
    @GetMapping("/register")
    public String register(String name, String phone, String password1, String password2,
                           HttpSession session, HttpServletRequest request){
        session.setAttribute("user",userService.getUserByPhone(phone));
        session.setAttribute("password1",password1);
        session.setAttribute("password2",password2);
        if (name == null || phone == null || password1 == null || password2 == null){
            return "";
        }
        if (userService.getUserByPhone(phone) == null) {
            if (password1.equals(password2)) {
                userService.addUser(name, phone, password1);//insert,delete,update返回值类型为int(0和1)
                return "redirect:/login/toLogin";
            }
        }
        return "redirect:/login/toRegister";
    }

    //跳转到忘记密码界面
    @GetMapping("/toForgetPassword")
    public String toForgetPassword(){
        return "forget_password";
    }

    //忘记密码：修改密码
    @RequestMapping("/updatePassword")
    public String updatePassword(String password1,String password2,String phone,String code,
                                 HttpSession session){
        session.setAttribute("password1",password1);
        session.setAttribute("password2",password2);
        session.setAttribute("phone",phone);
        session.setAttribute("getPhone",userService.getUserByPhone(phone));
        if (userService.getUserByPhone(phone) != null){
            if (password1.equals(password2)) {
                userService.updatePassword(password1, phone);
                return "redirect:/login/toLogin";
            }
            return "redirect:/login/toForgetPassword";
        }
        return "redirect:/login/toForgetPassword";
    }

    //获取手机验证码，需系统上线有备案号等证明材料才能申请短信签名模板id
//    @RequestMapping("/cellphone")
//    @ResponseBody
//    public Map<String, String> cellphone(String phone){
//        Sms sms = new Sms();
//        sms.setPhoneNumber(phone);
//        //设置过期时间
//        sms.setMin(5);
//        // i是验证码
//        String i = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
//        sms.setCode(i);
//        int appid=1400789307;
//        String appkey="7f01391b08283056e371d441095b2868";
//        int templateId = dxtemplateId;
//        String smsSign = dxsmsSign;
//
//        try{
//            String[] params = {sms.getCode(), Integer.toString(sms.getMin())};
//            SmsSingleSender sender =  new SmsSingleSender(appid,appkey);
//            SmsSingleSenderResult result =sender.sendWithParam("86",sms.getPhoneNumber(),templateId,
//                    params,smsSign,"","");
//            System.out.println(result);
//        }catch (HTTPException e){
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //短信验证码和手机号返回给前端
//        HashMap<String, String> objectObjectHashMap = new HashMap<>();
//        //验证码
//        objectObjectHashMap.put("nb",i);
//        //手机号
//        objectObjectHashMap.put("bnb",phone);
//
//        return objectObjectHashMap;
//    }
}
