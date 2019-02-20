package com.exchange.c2c.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CurrencyDTO {
    @ApiModelProperty("币种编码")
    private String code;

    @ApiModelProperty("币种名称")
    private String name;
}
