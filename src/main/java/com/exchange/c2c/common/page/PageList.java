package com.exchange.c2c.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PageList<T> extends PageForm {
    @ApiModelProperty("总页数")
    private Integer totalPages;

    @ApiModelProperty("总记录数")
    private Long totalElements;

    @ApiModelProperty("数据列表")
    private List<T> list;
}
