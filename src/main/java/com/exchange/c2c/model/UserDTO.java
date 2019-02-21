package com.exchange.c2c.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息")
public class UserDTO {
    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("邀请码")
    private String passportName;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("手机区号")
    private String phoneArea;

    @ApiModelProperty("手机号码")
    private String cellPhone;

    @ApiModelProperty("是否禁用/冻结 1:是,0:否")
    private Byte isDisabled;

    @ApiModelProperty("全称")
    private String fullName;

    @ApiModelProperty("ID编号")
    private String idNumber;

    @ApiModelProperty("1：身份证；2：护照；3:驾照")
    private Byte idType;

    @ApiModelProperty("证件正面照。关联SYS_FILES的FILE_ID")
    private Long idScan1;

    @ApiModelProperty("证件反面照。关联SYS_FILES的FILE_ID")
    private Long idScan2;

    @ApiModelProperty("证件手持照。关联SYS_FILES的FILE_ID")
    private Long idScan3;

    @ApiModelProperty("0 - 未认证 1 - 提交审核 2 - 审核未通过 3 - 审核通过")
    private Byte verifiedStatus;

    @ApiModelProperty("用户等级 1 - 注册成功  2 - 实名认证通过  3 - 联系客服认证通过")
    private Byte verifiedLevel;

    @ApiModelProperty("主账号类型 1 手机 2 邮箱 (默认注册账号为主账号)")
    private Byte signupType;

    @ApiModelProperty("是否设置交易密码")
    private Byte isSetTransactionPassword;

    @ApiModelProperty("交易频率")
    private Byte transactionPasswordFre;

    @ApiModelProperty("是否开启谷歌验证码")
    private Byte gaEnabled;

    @ApiModelProperty("谷歌验证码秘钥")
    private String gaSecurityKey;

    @ApiModelProperty("逻辑删除: 1已删除, 0未删除")
    private Byte isDeleted;
}
