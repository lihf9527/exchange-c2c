package com.exchange.c2c.service.impl;

import com.exchange.c2c.entity.Appeal;
import com.exchange.c2c.mapper.AppealMapper;
import com.exchange.c2c.service.AppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppealServiceImpl implements AppealService {
    @Autowired
    private AppealMapper appealMapper;

    @Override
    public void insert(Appeal appeal) {
        appealMapper.insert(appeal);
    }
}
