package com.exchange.c2c.common.util;

import org.springframework.beans.BeanUtils;

import java.util.function.Supplier;

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
}
