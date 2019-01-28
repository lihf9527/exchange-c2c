package com.exchange.c2c.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtils {
    private ClassUtils() {
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Type getGenericInterface(Class clazz, int index) {
        return clazz.getGenericInterfaces()[index];
    }

    public static Type getActualTypeArgument(ParameterizedType parameterizedType, int index) {
        return parameterizedType.getActualTypeArguments()[index];
    }

    public static Class getGenericClass(Class clazz, int index) {
        ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
        return (Class) pt.getActualTypeArguments()[index];
    }

    public static Class getGenericBySuperclass(Class clazz, int index) {
        ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
        return (Class) pt.getActualTypeArguments()[index];
    }

    public static <T> T newGenericInstance(Class clazz, int index) {
        Class<T> genericClass = getGenericClass(clazz, index);
        return newInstance(genericClass);
    }
}
