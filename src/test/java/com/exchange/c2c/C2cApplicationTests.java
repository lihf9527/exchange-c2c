package com.exchange.c2c;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exchange.c2c.common.util.RandomUtils;
import com.exchange.c2c.entity.Appeal;
import com.exchange.c2c.mapper.*;
import com.exchange.c2c.service.EmailService;
import com.exchange.c2c.service.SmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class C2cApplicationTests {

    @Autowired
    private ValueOperations<String, String> valueOperations;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private FundTransactionMapper fundTransactionMapper;
    @Autowired
    private CurrencyMapper currencyMapper;
    @Autowired
    private AppealMapper appealMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsService smsService;

    @Test
    public void contextLoads() {

    }

    @Test
    public void smsTest() {
        String template = "【ALiCoin】您的验证码：#code#，为了保护您的账户安全，请不要把验证码透露给别人";
        String msg = template.replace("#code#", RandomUtils.randomString(6));
        System.out.println(smsService.send("18397691556", msg));
    }

    @Test
    public void emailTest() {
        String template = "【ALiCoin】您的验证码：#code#，为了保护您的账户安全，请不要把验证码透露给别人";
        String text = template.replace("#code#", RandomUtils.randomString(6));
        System.out.println(emailService.send("lihf@sina.cn", "ALiCoin", text));
    }

    @Test
    public void updateTest() {
        Appeal appeal = new Appeal();
        appeal.setId(1);
        appeal.setTitle("abc");

        QueryWrapper<Appeal> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 1);
        System.out.println(appealMapper.update(appeal, wrapper));
    }

    @Test
    public void currencyTest() {
        System.out.println(currencyMapper.findCurrencyIdByCode("USDT"));
    }

    @Test
    public void redisTest() {
        valueOperations.set("hello", "你好", 1, TimeUnit.MINUTES);
        System.out.println(valueOperations.get("hello"));
    }

    @Test
    public void mapperTest() {
        System.out.println(userMapper.selectById(3));
        System.out.println(accountMapper.selectById(46));
        System.out.println(fundTransactionMapper.selectById(999));
    }

    public static void main(String[] args) {
        String token = "Bearer eyJ1c2VyTmFtZSI6";
        System.out.println(token.replaceFirst("[B|b][E|e][A|a][R|r][E|e][R|r]", "").trim());
    }

}

