package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.page.PageList;
import com.exchange.c2c.common.util.ApiBeanUtils;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.common.util.RandomUtils;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.Advert;
import com.exchange.c2c.enums.AdvertStatusEnum;
import com.exchange.c2c.model.*;
import com.exchange.c2c.service.AdvertService;
import com.exchange.c2c.service.PayModeService;
import com.exchange.c2c.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Api(tags = "广告接口")
@Validated
@RestController
@RequestMapping("/advert")
public class AdvertController {
    @Autowired
    private AdvertService advertService;
    @Autowired
    private PayModeService payModeService;
    @Autowired
    private UserService userService;

    @Login
    @PostMapping("/create")
    @ApiOperation(value = "新增广告", notes = "创建人: 李海峰")
    public Result<Integer> create(@Valid CreateAdvertForm form) {
        val advertisement = buildAdvertisement(form);
        advertisement.setCreateBy(WebUtils.getUserId());
        advertisement.setAdvNo(RandomUtils.serialNumber(6));
        advertisement.setCreateTime(LocalDateTime.now());
        advertisement.setStatus(AdvertStatusEnum.DISABLE.getValue());
        advertService.save(advertisement);
        return Result.success(advertisement.getId());
    }

    private Advert buildAdvertisement(CreateAdvertForm form) {
        val advertisement = ApiBeanUtils.copyProperties(form, Advert::new);
        advertisement.setUpdateTime(LocalDateTime.now());
        val payModes = form.getPayModes().split(",");
        val payModeList = payModeService.findEnabled(WebUtils.getUserId(), payModes);
        Assert.isEquals(payModes.length, payModeList.size(), "支付方式未启用");
        val payModeIds = payModeList.stream().map(e -> e.getId().toString()).collect(Collectors.joining(","));
        advertisement.setPayModeIds(payModeIds);
        return advertisement;
    }

    @Login
    @PostMapping("/update")
    @ApiOperation(value = "修改广告", notes = "创建人: 李海峰")
    public Result<?> update(@Valid UpdateAdvertForm form) {
        val advertisement = advertService.findById(form.getId());
        Assert.isEquals(advertisement.getId(), WebUtils.getUserId(), "非法操作");
        advertService.save(buildAdvertisement(form));
        return Result.SUCCESS;
    }

    @Login
    @GetMapping("/info")
    @ApiOperation(value = "广告详情", notes = "创建人: 李海峰")
    public Result<AdvertModel> info(@RequestParam @ApiParam("广告ID") Integer id) {
        val advertisement = advertService.findById(id);
        val advertModel = ApiBeanUtils.copyProperties(advertisement, AdvertModel::new);
        return Result.success(advertModel);
    }

    @Login
    @PostMapping("/myAds")
    @ApiOperation(value = "我的广告列表", notes = "创建人: 李海峰")
    public Result<PageList<AdvertModel>> myAds(@Valid MyAdsForm form) {
        return Result.success(ApiBeanUtils.convertToPageList(advertService.findAll(form), e -> ApiBeanUtils.copyProperties(e, AdvertModel::new)));
    }

    @PostMapping("/list")
    @ApiOperation(value = "买卖市场广告列表", notes = "创建人: 李海峰")
    public Result<PageList<MarketAdvertModel>> list(@Valid MarketAdvertForm form) {
        return Result.success(ApiBeanUtils.convertToPageList(advertService.findAll(form), e -> {
            MarketAdvertModel model = ApiBeanUtils.copyProperties(e, MarketAdvertModel::new);
            model.setSellerName(userService.getFullName(e.getCreateBy()));
            // TODO: 2019/2/1
            model.setCount(0L);
            return model;
        }));
    }
}
