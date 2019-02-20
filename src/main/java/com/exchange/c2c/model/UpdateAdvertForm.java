package com.exchange.c2c.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdateAdvertForm extends CreateAdvertForm {
    @NotNull(message = "广告ID不能为空")
    @ApiModelProperty("广告ID")
    private Integer id;
}
