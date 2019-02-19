package com.exchange.c2c.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@ApiModel
public class CreateAppealForm {
    @NotEmpty(message = "订单编号不能为空")
    @ApiModelProperty("订单编号")
    private String orderNo;

    @Size(message = "申诉标题长度不能超过32", max = 32)
    @NotEmpty(message = "申诉标题不能为空")
    @ApiModelProperty("申诉标题")
    private String title;

    @Size(message = "申诉内容长度不能超过128", max = 128)
    @NotEmpty(message = "申诉内容不能为空")
    @ApiModelProperty("申诉内容")
    private String descr;

    @NotEmpty(message = "申诉材料不能为空")
    @ApiModelProperty("申诉材料 图片url,多张用逗号分隔")
    private String materials;
}
