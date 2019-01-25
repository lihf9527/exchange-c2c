package com.exchange.c2c.common.util;

import com.exchange.c2c.common.exception.BizException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public abstract class Assert {

    public static void isTrue(boolean expression, String message) {
        throwException(!expression, message);
    }

    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        throwException(!expression, messageSupplier);
    }

    public static void isFalse(boolean expression, String message) {
        throwException(expression, message);
    }

    public static void isFalse(boolean expression, Supplier<String> messageSupplier) {
        throwException(expression, messageSupplier);
    }

    public static void isNull(Object object, String message) {
        throwException(Objects.nonNull(object), message);
    }

    public static void isNull(Object object, Supplier<String> messageSupplier) {
        throwException(Objects.nonNull(object), messageSupplier);
    }

    public static void notNull(Object object, String message) {
        throwException(Objects.isNull(object), message);
    }

    public static void notNull(Object object, Supplier<String> messageSupplier) {
        throwException(Objects.isNull(object), messageSupplier);
    }

    public static void isEmpty(String string, String message) {
        throwException(!StringUtils.isEmpty(string), message);
    }

    public static void isEmpty(String string, Supplier<String> messageSupplier) {
        throwException(!StringUtils.isEmpty(string), messageSupplier);
    }

    public static void notEmpty(String string, String message) {
        throwException(StringUtils.isEmpty(string), message);
    }

    public static void notEmpty(String string, Supplier<String> messageSupplier) {
        throwException(StringUtils.isEmpty(string), messageSupplier);
    }

    public static void isEmpty(Collection collection, String message) {
        throwException(!CollectionUtils.isEmpty(collection), message);
    }

    public static void isEmpty(Collection collection, Supplier<String> messageSupplier) {
        throwException(!CollectionUtils.isEmpty(collection), messageSupplier);
    }

    public static void notEmpty(Collection collection, String message) {
        throwException(CollectionUtils.isEmpty(collection), message);
    }

    public static void notEmpty(Collection collection, Supplier<String> messageSupplier) {
        throwException(CollectionUtils.isEmpty(collection), messageSupplier);
    }

    public static void isEquals(Object expected, Object actual, String message) {
        throwException(!Objects.equals(expected, actual), message);
    }

    public static void isEquals(Object expected, Object actual, Supplier<String> messageSupplier) {
        throwException(!Objects.equals(expected, actual), messageSupplier);
    }

    public static void notEquals(Object expected, Object actual, String message) {
        throwException(Objects.equals(expected, actual), message);
    }

    public static void notEquals(Object expected, Object actual, Supplier<String> messageSupplier) {
        throwException(Objects.equals(expected, actual), messageSupplier);
    }

    private static void throwException(boolean expression, String message) {
        if (expression)
            throw new BizException(message);
    }

    private static void throwException(boolean expression, Supplier<String> messageSupplier) {
        if (expression)
            throw new BizException(nullSafeGet(messageSupplier));
    }

    private static String nullSafeGet(Supplier<String> messageSupplier) {
        return messageSupplier == null ? null : messageSupplier.get();
    }
}
