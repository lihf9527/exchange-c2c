package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 申诉表
 */
@Data
@TableName("c2c_appeal")
public class Appeal {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String depict;

    /**
     * 凭证: 图片url
     */
    private String voucher;

    /**
     * 结果 1处理中 2胜诉 3败诉
     */
    private Integer result;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private Long updateBy;
}
