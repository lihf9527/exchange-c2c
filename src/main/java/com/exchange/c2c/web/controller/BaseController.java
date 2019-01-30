package com.exchange.c2c.web.controller;

import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.entity.User;
import com.exchange.c2c.service.UserService;
import com.exchange.c2c.web.interceptor.AuthorizationInterceptor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.Validator;

public abstract class BaseController {
    @Autowired
    private Validator validator;

    @Autowired
    private UserService userService;

    protected Long getUserId() {
        return (Long) RequestContextHolder.currentRequestAttributes().getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
    }

    protected User getLoginUser() {
        Long userId = getUserId();
        if (userId == null)
            return null;

        return userService.findById(userId);
    }

    protected <T> void validate(T form, Class... classes) {
        val result = validator.validate(form, classes);
        val iterator = result.iterator();
        if (iterator.hasNext())
            throw new BizException(iterator.next().getMessage());
    }
}
