package com.exchange.c2c.model;

import com.exchange.c2c.common.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel
public class AppealModel {
    @ApiModelProperty("申诉标题")
    private String title;

    @ApiModelProperty("申诉描述")
    private String depict;

    @ApiModelProperty("申诉材料")
    private String voucher;

    @ApiModelProperty("申述结果 1处理中 2胜诉 3败诉")
    private Integer result;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("申诉时间")
    private LocalDateTime createTime;
}
