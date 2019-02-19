package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.ApiBeanUtils;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.common.util.EnumUtils;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.Appeal;
import com.exchange.c2c.entity.Order;
import com.exchange.c2c.enums.AppealStatusEnum;
import com.exchange.c2c.enums.OrderStatusEnum;
import com.exchange.c2c.model.AppealDTO;
import com.exchange.c2c.model.CreateAppealForm;
import com.exchange.c2c.service.AppealService;
import com.exchange.c2c.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Api(tags = "申诉接口")
@Validated
@RestController
@RequestMapping("/appeal")
public class AppealController {
    @Autowired
    private AppealService appealService;
    @Autowired
    private OrderService orderService;

    @Login
    @PostMapping("/create")
    @ApiOperation(value = "提交申诉", notes = "创建人: 李海峰")
    public Result<Integer> create(@Valid CreateAppealForm form) {
        verify(orderService.findByOrderNo(form.getOrderNo()));

        val appeal = ApiBeanUtils.copyProperties(form, Appeal::new);
        appeal.setStatus(AppealStatusEnum.PROCESSING.getValue());
        appeal.setCreateBy(WebUtils.getUserId());
        appeal.setCreateTime(LocalDateTime.now());
        appealService.insert(appeal);
        return Result.success(appeal.getId());
    }

    private void verify(Order order) {
        OrderStatusEnum orderStatusEnum = EnumUtils.toEnum(order.getStatus(), OrderStatusEnum.class);
        switch (Objects.requireNonNull(orderStatusEnum)) {
            case WAIT_PAY:
                if (order.getCreateTime().plusHours(1).isAfter(LocalDateTime.now()))
                    throw new BizException("订单创建不足1个小时,不能申诉");
                break;
            case WAIT_CONFIRM:
                if (order.getPayTime().plusHours(1).isAfter(LocalDateTime.now()))
                    throw new BizException("距离确认转账时间不足1个小时，不能申诉");
                break;
            case CANCELED:
                throw new BizException("订单已取消,不能申诉");
            case APPEAL:
                throw new BizException("订单申诉中,不能重复申诉");
            case FINISHED:
                throw new BizException("订单已完成,不能申诉");
        }
    }

    @Login
    @GetMapping("/info")
    @ApiOperation(value = "申诉详情", notes = "创建人: 李海峰")
    public Result<AppealDTO> info(@RequestParam @ApiParam("订单编号") String orderNo) {
        val order = orderService.findByOrderNo(orderNo);
        val validUserIds = Arrays.asList(order.getBuyerId(), order.getSellerId());
        Assert.isTrue(validUserIds.contains(WebUtils.getUserId()), "非法操作");

        val appeal = appealService.findByOrderNo(orderNo);
        val appealDTO = ApiBeanUtils.copyProperties(appeal, AppealDTO::new);
        return Result.success(appealDTO);
    }
}
