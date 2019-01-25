package com.exchange.c2c.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.common.util.JwtUtils;
import com.exchange.c2c.common.util.PBKDF2Util;
import com.exchange.c2c.entity.User;
import com.exchange.c2c.mapper.UserMapper;
import com.exchange.c2c.service.UserService;
import com.exchange.c2c.web.model.LoginForm;
import com.exchange.c2c.web.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ValueOperations<String, String> valueOperations;

    @Override
    public User findById(Long userId) {
        return Optional.ofNullable(userMapper.selectById(userId)).orElseThrow(() -> new BizException("用户不存在"));
    }

    @Override
    public String login(LoginForm form) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("EMAIL", form.getUserName()).or().eq("CELL_PHONE", form.getUserName());
        User user = userMapper.selectOne(wrapper);
        Assert.notNull(user, "用户不存在");

        try {
            PBKDF2Util.authenticate(form.getPassword(), user.getPassword(), Optional.ofNullable(user.getSignupIp()).orElse("abc"));
        } catch (Exception e) {
            log.error("加密失败 ==> " + e.getMessage(), e);
            throw new BizException("密码错误");
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        userModel.setGaSecurityKey("");
        String token = JwtUtils.createToken(JSON.toJSONString(userModel), 60 * 30);
        valueOperations.set("userInfo:" + user.getUserId(), token, 30, TimeUnit.MINUTES);
        return token;
    }
}
