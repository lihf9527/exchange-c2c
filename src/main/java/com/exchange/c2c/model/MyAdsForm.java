package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.common.page.PageForm;
import com.exchange.c2c.enums.AdvertStatusEnum;
import com.exchange.c2c.enums.AdvertTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MyAdsForm extends PageForm {
    @EnumValue(message = "广告类型枚举值不正确", enumClass = AdvertTypeEnum.class, nullable = true)
    @ApiModelProperty("广告类型 1购买 2出售")
    private Integer type;

    @EnumValue(message = "广告状态枚举值不正确", enumClass = AdvertStatusEnum.class, nullable = true)
    @ApiModelProperty("广告状态 1上架 0下架")
    private Integer status;
}
