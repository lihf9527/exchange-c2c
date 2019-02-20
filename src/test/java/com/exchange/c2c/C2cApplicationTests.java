package com.exchange.c2c;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exchange.c2c.entity.Appeal;
import com.exchange.c2c.mapper.*;
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

    @Test
    public void contextLoads() {

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

