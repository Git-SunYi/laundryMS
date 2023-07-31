package com.sunyi.laundryms.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.sunyi.laundryms.web.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class TokenInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        //获取到token
        String token = request.getHeader("Authorization");
        if (token!=null){
            boolean result= TokenUtil.verify(token);
            if (result){
                log.info("通过拦截器");
                return true;
            }
        }
        try {
            JSONObject json=new JSONObject();
            json.put("msg","token verify fail");
            json.put("code","500");
            response.getWriter().append(json.toString());
            log.info("认证失败，未通过拦截器");
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}

