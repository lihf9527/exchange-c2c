package com.exchange.c2c.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel
public class LoginForm {
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty("邮箱/手机号码")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("谷歌验证码")
    private String googleCode;
}
