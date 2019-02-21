package com.exchange.c2c.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AccountTypeDTO {
    @ApiModelProperty("枚举值")
    private String value;

    @ApiModelProperty("枚举名称")
    private String name;
}
