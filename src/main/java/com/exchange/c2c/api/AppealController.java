package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "申诉接口")
@Validated
@RestController
@RequestMapping("/appeal")
public class AppealController {

    @PostMapping("/create")
    @ApiOperation(value = "提交申诉", notes = "创建人: 李海峰")
    public Result<?> create() {

        return Result.SUCCESS;
    }

    @GetMapping("/info")
    @ApiOperation(value = "申诉详情", notes = "创建人: 李海峰")
    public Result<?> info() {

        return Result.SUCCESS;
    }
}
