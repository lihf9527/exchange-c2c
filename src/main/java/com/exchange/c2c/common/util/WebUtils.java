package com.exchange.c2c.common.util;

import com.exchange.c2c.entity.User;
import com.exchange.c2c.service.UserService;
import com.exchange.c2c.web.interceptor.AuthorizationInterceptor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebUtils {
    private WebUtils() {
    }

    private static RequestAttributes getRequestAttributes() {
        return RequestContextHolder.currentRequestAttributes();
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) getRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static Long getUserId() {
        return (Long) getRequestAttributes().getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
    }

    public static User getLoginUser() {
        Long userId = getUserId();
        Assert.notNull(userId, "用户未登录");
        return SpringUtils.getBean(UserService.class).findById(userId);
    }
}
