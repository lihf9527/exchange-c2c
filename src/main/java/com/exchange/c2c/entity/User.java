package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户表
 */
@Data
@TableName("user_users")
public class User {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 邀请码
     */
    private String passportName;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 修改时间
     */
    private LocalDateTime updatedDate;

    /**
     * 修改人
     */
    private String updatedBy;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private LocalDateTime createdDate;

    /**
     * 手机
     */
    private String cellPhone;

    /**
     * 密码
     */
    private String password;

    /**
     * 交易密码
     */
    private String transactionPassword;

    /**
     * 是否禁用或冻结。1：是，0：否
     */
    private Integer isDisabled;

    /**
     * 全称
     */
    private String fullName;

    /**
     * 1：身份证；2：护照；3:驾照
     */
    private Integer idType;

    /**
     * ID编号
     */
    private String idNumber;

    /**
     * 证件正面照。关联SYS_FILES的FILE_ID
     */
    private Long idScan1;

    /**
     * 证件反面照。关联SYS_FILES的FILE_ID
     */
    private Long idScan2;

    /**
     * 证件手持照。关联SYS_FILES的FILE_ID
     */
    private Long idScan3;

    /**
     * 邀请人code
     */
    private String inviteCode;

    /**
     * 0 - 未认证 1 - 提交审核 2 - 审核未通过 3 - 审核通过
     */
    private Integer verifiedStatus;

    /**
     * 用户等级 1 - 注册成功  2 - 实名认证通过  3 - 联系客服认证通过
     */
    private Integer verifiedLevel;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 卡照片4？暂时没用
     */
    private Long idScan4;

    /**
     * 第一名称
     */
    private String firstName;

    /**
     * 最后名称
     */
    private String lastName;

    /**
     * 生日
     */
    private LocalDate dateOfBirth;

    /**
     * 性别。0：女，1：男
     */
    private Integer gender;

    /**
     * 国家
     */
    private String country;

    /**
     * 身份
     */
    private String province;

    /**
     * 街道
     */
    private String street;

    /**
     * 邮编
     */
    private String postalCode;

    /**
     * 区号
     */
    private String phoneArea;

    private String gaSecurityKey;

    private Integer gaEnabled;

    /**
     * 邮箱状态
     */
    private Integer emailEnabled;

    /**
     * 手机状态
     */
    private Integer phoneEnabled;

    /**
     * VIP级别
     */
    private Integer vipLevelId;

    /**
     * 客户认证不通过的原因
     */
    private String notes;

    /**
     * 客户认证不通过的字段错误信息。JSON格式
     */
    private String fieldNotes;

    /**
     * 客户认证提交申请时间
     */
    private LocalDateTime submitTime;

    /**
     * 0普通用户10操盘用户20机构用户30测试40推广70c2c商家90刷单100期权二级代理110期权一级代理
     */
    private Integer userType;

    /**
     * 邀请人ID
     */
    private Long invitedByUserId;

    /**
     * 审核时间
     */
    private LocalDateTime verifyTime;

    /**
     * 审核人
     */
    private String verifyCheckUser;

    private Integer signupTime;

    /**
     * 加密盐
     */
    private String signupIp;

    /**
     * 推荐人id 暂未用到
     */
    private Long refereeId;

    /**
     * 主账号类型 1 手机 2 邮箱 (默认注册账号为主账号)
     */
    private Integer signupType;
}
