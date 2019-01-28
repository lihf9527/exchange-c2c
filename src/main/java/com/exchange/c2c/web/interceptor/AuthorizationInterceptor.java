package com.exchange.c2c.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.JwtUtils;
import com.exchange.c2c.web.model.UserModel;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    public static final String USER_KEY = "userId";

    private final ValueOperations<String, String> valueOperations;

    @Autowired
    public AuthorizationInterceptor(ValueOperations<String, String> valueOperations) {
        this.valueOperations = valueOperations;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return true;

        if (!needLogin((HandlerMethod) handler))
            return true;

        String token = getToken(request);
        if (StringUtils.isEmpty(token))
            throw new BizException("token不能为空");

        Claims claims;
        try {
            claims = JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("无效的token ==> {}", token);
            throw new BizException("token无效");
        }

        if (new Date().after(claims.getExpiration()))
            throw new BizException("token失效，请重新登录");

        log.info("token解析成功 ==> sub={}, exp={}", claims.getSubject(), claims.getExpiration().toLocaleString());
        UserModel userModel = JSON.parseObject(claims.getSubject(), UserModel.class);
        String redisToken = valueOperations.get("userInfo:" + userModel.getUserId());
        if (StringUtils.isEmpty(redisToken))
            throw new BizException("token失效，请重新登录");

        if (!Objects.equals(token, redisToken))
            throw new BizException("已在另一处登录");

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, userModel.getUserId());

        return true;
    }

    private boolean needLogin(HandlerMethod handlerMethod) {
        return Objects.nonNull(handlerMethod.getMethodAnnotation(Login.class));
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("authorization");
        return StringUtils.isEmpty(authorization) ? null : authorization.replaceFirst("[B|b][E|e][A|a][R|r][E|e][R|r]", "").trim();
    }
}
