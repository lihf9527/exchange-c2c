package com.exchange.c2c.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.common.page.PageList;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ApiBeanUtils {
    private ApiBeanUtils() {
    }

    public static <S, T> T copyProperties(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <S, T> T copyProperties(S source, Supplier<T> supplier) {
        T target = supplier.get();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <R, T> PageList<R> convertToPageList(IPage<T> page, Function<T, R> mapper) {
        PageList<R> pageList = new PageList<>();
        pageList.setPageIndex((int) page.getCurrent());
        pageList.setPageSize((int) page.getSize());
        pageList.setTotalPages((int) page.getPages());
        pageList.setTotalElements(page.getTotal());
        pageList.setList(page.getRecords().stream().map(mapper).collect(Collectors.toList()));
        return pageList;
    }

    public static <T> PageList<T> convertToPageList(IPage<T> page) {
        return convertToPageList(page, e -> e);
    }
}
