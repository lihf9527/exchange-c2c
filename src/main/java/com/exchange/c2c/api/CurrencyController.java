package com.exchange.c2c.api;


import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.enums.CurrencyTypeEnum;
import com.exchange.c2c.model.CurrencyDTO;
import com.exchange.c2c.service.CurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "货币接口")
@Validated
@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/{type}/all")
    @ApiOperation(value = "数字货币或法币下拉框", notes = "创建人: 李海峰")
    public Result<List<CurrencyDTO>> coin(
            @ApiParam("币种类型 C-数字货币 F-法定货币")
            @EnumValue(message = "币种类型枚举值不正确", enumClass = CurrencyTypeEnum.class)
            @PathVariable String type) {
        return Result.success(currencyService.findAll(type));
    }
}
