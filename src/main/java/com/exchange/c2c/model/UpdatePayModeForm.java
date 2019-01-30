package com.exchange.c2c.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdatePayModeForm extends CreatePayModeForm {
    @NotNull(message = "支付方式ID不能为空")
    @ApiModelProperty("支付方式ID")
    private Integer id;
}
