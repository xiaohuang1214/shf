package com.atguigu.interceptor;

import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Date: 2022/5/25
 * Author:George
 * Description:
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute("USER") == null){
            WebUtil.writeJSON(response, Result.build("未登录", ResultCodeEnum.LOGIN_AUTH));
            return false;
        }

        return true;
    }
}
