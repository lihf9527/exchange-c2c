package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

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
     * 订单编号
     */
    private String orderNo;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String descr;

    /**
     * 申诉材料、凭证
     */
    private String materials;

    /**
     * 申诉状态 1处理中 2胜诉 3败诉
     */
    private Integer status;

    /**
     * 创建人ID 申诉发起者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID 裁判
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
