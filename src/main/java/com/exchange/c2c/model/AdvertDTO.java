package com.exchange.c2c.model;

import com.exchange.c2c.common.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel
public class AdvertDTO extends UpdateAdvertForm {
    @ApiModelProperty("广告编号")
    private String adNo;

    @ApiModelProperty("广告状态 1上架 0下架")
    private Integer status;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("发布时间")
    private LocalDateTime createTime;
}
