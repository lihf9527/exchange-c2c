package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.enums.AdvertStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdateAdvertForm extends CreateAdvertForm {
    @NotNull(message = "广告ID不能为空")
    @ApiModelProperty("广告ID")
    private Integer id;

    @EnumValue(message = "广告状态枚举值不正确", enumClass = AdvertStatusEnum.class)
    @NotEmpty(message = "广告状态不能为空")
    @ApiModelProperty("广告状态 1上架 0下架")
    private Integer status;
}
