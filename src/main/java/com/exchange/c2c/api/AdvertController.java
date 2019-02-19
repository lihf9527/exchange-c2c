package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.page.PageList;
import com.exchange.c2c.common.util.*;
import com.exchange.c2c.entity.Advert;
import com.exchange.c2c.enums.AdvertStatusEnum;
import com.exchange.c2c.model.*;
import com.exchange.c2c.service.AdvertService;
import com.exchange.c2c.service.OrderService;
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
import java.util.List;
import java.util.Objects;
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
    @Autowired
    private OrderService orderService;

    @Login
    @PostMapping("/create")
    @ApiOperation(value = "新增广告", notes = "创建人: 李海峰")
    public Result<Integer> create(@Valid CreateAdvertForm form) {
        val advert = ApiBeanUtils.copyProperties(form, Advert::new);
        advert.setPayModeIds(getPayModeIds(form.getPayModes()));
        advert.setAdNo(RandomUtils.serialNumber(6));
        advert.setSurplusQuantity(form.getTotalQuantity());
        advert.setCreateBy(WebUtils.getUserId());
        advert.setCreateTime(LocalDateTime.now());
        advert.setStatus(AdvertStatusEnum.DISABLE.getValue());
        advert.setVersion(0L);
        advertService.save(advert);
        return Result.success(advert.getId());
    }

    private String getPayModeIds(String payModes) {
        List<Integer> accountTypes = payModeService.findAccountTypes(payModes.split(","));
        List<Integer> ids = payModeService.findIds(WebUtils.getUserId(), accountTypes);
        return ids.stream().map(Objects::toString).collect(Collectors.joining(","));
    }

    @Login
    @PostMapping("/update")
    @ApiOperation(value = "修改广告", notes = "创建人: 李海峰")
    public Result<?> update(@Valid UpdateAdvertForm form) {
        val oldAdvert = advertService.findById(form.getId());
        Assert.isEquals(oldAdvert.getCreateBy(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(AdvertStatusEnum.DISABLE.getValue(), oldAdvert.getStatus(), "只能修改已下架的广告");

        val advert = ApiBeanUtils.copyProperties(form, Advert::new);
        advert.setPayModeIds(getPayModeIds(form.getPayModes()));
        advert.setUpdateBy(WebUtils.getUserId());
        advert.setUpdateTime(LocalDateTime.now());
        advert.setVersion(oldAdvert.getVersion());
        advertService.save(advert);
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/enable")
    @ApiOperation(value = "上架广告", notes = "创建人: 李海峰")
    public Result<?> enable(@ApiParam("广告ID") @RequestParam Integer id) {
        val advert = advertService.findById(id);
        Assert.isEquals(AdvertStatusEnum.DISABLE.getValue(), advert.getStatus(), "广告已上架,不能重复上架");
        
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/disable")
    @ApiOperation(value = "下架广告", notes = "创建人: 李海峰")
    public Result<?> disable(@ApiParam("广告ID") @RequestParam Integer id) {
        val advert = advertService.findById(id);
        Assert.isEquals(AdvertStatusEnum.ENABLE.getValue(), advert.getStatus(), "广告已下架,不能重复下架");
        return Result.SUCCESS;
    }

    @Login
    @GetMapping("/info")
    @ApiOperation(value = "广告详情", notes = "创建人: 李海峰")
    public Result<AdvertDTO> info(@RequestParam @ApiParam("广告ID") Integer id) {
        val advert = advertService.findById(id);
        val advertDTO = ApiBeanUtils.copyProperties(advert, AdvertDTO::new);
        return Result.success(advertDTO);
    }

    @Login
    @PostMapping("/myAds")
    @ApiOperation(value = "我的广告列表", notes = "创建人: 李海峰")
    public Result<PageList<AdvertDTO>> myAds(@Valid MyAdsForm form) {
        return Result.success(ApiBeanUtils.convertToPageList(advertService.findAll(form), e -> ApiBeanUtils.copyProperties(e, AdvertDTO::new)));
    }

    @PostMapping("/list")
    @ApiOperation(value = "买卖市场广告列表", notes = "创建人: 李海峰")
    public Result<PageList<MarketAdvertDTO>> list(@Valid MarketAdvertForm form) {
        return Result.success(ApiBeanUtils.convertToPageList(advertService.findAll(form), e -> {
            MarketAdvertDTO dto = ApiBeanUtils.copyProperties(e, MarketAdvertDTO::new);
            dto.setSellerName(userService.getFullName(e.getCreateBy()));
            long count = orderService.countSellerFinishedOrders(e.getCreateBy(), e.getType());
            double totalCount = orderService.countSellerAllOrders(e.getCreateBy(), e.getType());
            dto.setCount(count);
            dto.setRatio(NumberUtils.format(count / totalCount, "0%"));
            return dto;
        }));
    }
}
