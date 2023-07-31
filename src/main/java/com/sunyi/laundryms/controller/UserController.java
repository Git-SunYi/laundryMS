package com.sunyi.laundryms.controller;

import com.sunyi.laundryms.common.result.ResponseData;
import com.sunyi.laundryms.common.result.utils.FileUtils;
import com.sunyi.laundryms.domain.bo.UserBO;
import com.sunyi.laundryms.domain.entity.User;
import com.sunyi.laundryms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    //跳转到用户中心界面并显示用户信息
    @GetMapping("/toUserCenter")
    public String toUserCenter(String phone, HttpSession session, HttpServletRequest request){ //phone是通过url路径传过来的值
        session.setAttribute("phone",phone);
//        request.setAttribute("user",userService.getUserByPhone(phone));
        session.setAttribute("name",userService.getUserByPhone(phone).getName());
        session.setAttribute("account",userService.getUserByPhone(phone).getAccount());
        session.setAttribute("gender",userService.getUserByPhone(phone).getGender());
        session.setAttribute("password",userService.getUserByPhone(phone).getPassword());
        session.setAttribute("user_type",userService.getUserByPhone(phone).getUser_type());
        session.setAttribute("user_money",userService.getUserByPhone(phone).getUser_money());
        return "user_center";
    }

    //用户中心更新用户信息
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(String name, String account, String gender, String phone,String password,
                                 HttpSession session,HttpServletRequest request){
        User user=new User(0,name,account,password,gender,phone,"","","");
        if (userService.updateUserInfo(user, (String) session.getAttribute("phone")) > 0){
            session.setAttribute("phone",phone);
            session.setAttribute("name",name);
            session.setAttribute("account",account);
            session.setAttribute("gender",gender);
            session.setAttribute("password",password);
            log.info("用户信息修改成功");
        }else {
            log.info("用户信息修改失败");
        }
        return "redirect:/user/toUserCenter?phone="+session.getAttribute("phone");
    }

    //上传头像，更新头像
    @PostMapping("/uploadPicture")
    public String uploadPicture(@RequestParam("img") MultipartFile file,HttpSession session) throws IOException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名。也可以在这里添加判断语句，规定特定格式的图片才能上传，否则拒绝保存。
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if (suffixName.equals(".jpg") || suffixName.equals(".tif") || suffixName.equals(".pjp") || suffixName.equals(".apng") || suffixName.equals(".xbm") || suffixName.equals(".jxl") || suffixName.equals(".svgz") || suffixName.equals(".jpeg") || suffixName.equals(".ico") || suffixName.equals(".tiff")
                || suffixName.equals(".gif") || suffixName.equals(".svg") || suffixName.equals(".jfif") || suffixName.equals(".webp") || suffixName.equals(".png") || suffixName.equals(".bmp") || suffixName.equals(".pipeg") || suffixName.equals(".avif")) {
            //为了避免发生图片替换，这里使用了文件名重新生成
            fileName = UUID.randomUUID() + suffixName;

            String head_portrait="/img/head_portrait/"+fileName;
            String phone= (String) session.getAttribute("phone");
            if (userService.updateHeadPortrait(head_portrait, phone) > 0){

                //方式一：将上传的图片保存到已经打包(compile)好的指定路径下
                String path = ResourceUtils.getURL("classpath:").getPath() + "static/img/head_portrait/";
                File file1 = new File(path + fileName);
                //把multipartFile写入file1
                file.transferTo(file1);
                //将文件保存到未编译的系统中
                InputStream inputStream=null;
                try {
                    String savePathName = "src/main/resources/static/img/head_portrait/"+fileName;
                    inputStream = new FileInputStream(file1); //从已经打包(compile)好的指定路径下的文件获得输入流
                    FileUtils.saveFile(inputStream,savePathName);
                    log.info("头像保存成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    //关闭流
                    inputStream.close();
                }

//                //方式二：创建临时文件的方式将文件保存到未编译的系统中
//                // 注：没有保存到打包好的路径下，设置头像后不能立即显示，需重新运行系统
//                InputStream inputStream=null;
//                File tempFile=null;
//                try {
//                    String savePathName = "src/main/resources/static/img/head_portrait/"+fileName;
//                    //创建临时文件
//                    tempFile=File.createTempFile("temp",null);
//                    //把multipartFile写入临时文件
//                    file.transferTo(tempFile);
//                    //使用临时文件创建inputStream 流
//                    inputStream=new FileInputStream(tempFile);
//                    FileUtils.saveFile(inputStream,savePathName);
//                    log.info("头像保存成功");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    //删除临时文件
//                    tempFile.deleteOnExit();
//                    //关闭流
//                    inputStream.close();
//                }

                log.info("用户信息修改成功");
                session.setAttribute("head_portrait",head_portrait);
            }else {
                log.info("用户信息修改失败");
            }
        }
        return "redirect:/user/toUserCenter?phone="+session.getAttribute("phone");
    }

    //注销用户
    @PostMapping("/deleteUserInfo")
    @ResponseBody
    public ResponseData deleteUserInfo(String phone, HttpSession session){
        if (userService.deleteUserInfo(phone)>0){
            session.invalidate();
            return ResponseData.ok("注销成功");
        }
        return ResponseData.fail("注销失败");
    }

    //充值
    @PostMapping(value = "/recharge",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResponseData recharge(@RequestBody UserBO bo,HttpServletRequest request){
        if (userService.updateUserByPhone(bo)){
            return ResponseData.ok();
        }
        return ResponseData.fail("充值失败");
    }

}
