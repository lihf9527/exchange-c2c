package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.common.page.PageForm;
import com.exchange.c2c.enums.MarketTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class MarketAdvertForm extends PageForm {
    @EnumValue(message = "市场类型枚举值不正确", enumClass = MarketTypeEnum.class)
    @NotNull(message = "市场类型不能为空")
    @ApiModelProperty("市场类型 1出售 2购买")
    private Integer type;
}
