package com.dashu.dbtool.interceptor;

import com.dashu.dbtool.exception.UnAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // 从 session 中获取用户信息
        Object user = request.getSession().getAttribute("LOGIN_USER");
        if (user == null) {
            throw new UnAuthorizedException("请先登陆");
        }
        return true;
    }

    private void failResp(HttpServletResponse response, int code) {
        response.setContentType("text/plain;charset=UTF-8");
        response.setStatus(code);
        try (PrintWriter writer = response.getWriter()){
            writer.write("请先登陆");
            writer.flush();
        } catch (IOException e) {
            log.warn("Failed to write response to client: {}", e.getMessage());
        }
    }
}
