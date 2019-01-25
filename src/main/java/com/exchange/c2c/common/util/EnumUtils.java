package com.exchange.c2c.common.util;

import com.exchange.c2c.common.EmptyEnum;
import com.exchange.c2c.common.EnumMsg;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class EnumUtils {
    private static final ConcurrentMap<String, Map<?, EnumMsg>> ENUM_MAP = new ConcurrentHashMap<>();

    private EnumUtils() {
    }

    public static <E extends EnumMsg> List<E> getEnums(Class<E> enumClass) {
        return getEnumMsgMap(enumClass).values().stream().map(enumClass::cast).collect(Collectors.toList());
    }

    public static <E extends EnumMsg> List<EnumMsg> getEnumMsgs(Class<E> enumClass) {
        return new ArrayList<>(getEnumMsgMap(enumClass).values());
    }

    public static <E extends EnumMsg<V>, V> String getEnumName(V value, Class<E> enumClass) {
        return toEnumMsg(value, enumClass).getName();
    }

    public static <E extends EnumMsg<V>, V> boolean isValid(V value, Class<E> enumClass) {
        return !toEnumMsg(value, enumClass).isEmpty();
    }

    public static <E extends EnumMsg<V>, V> E toEnum(V value, Class<E> enumClass) {
        EnumMsg enumMsg = toEnumMsg(value, enumClass);
        return enumClass.isInstance(enumMsg) ? enumClass.cast(enumMsg) : null;
    }

    public static <E extends EnumMsg<V>, V> EnumMsg toEnumMsg(V value, Class<E> enumClass) {
        if (value == null || enumClass == null)
            return EmptyEnum.NULL;

        EnumMsg enumMsg = getEnumMsgMap(enumClass).get(value);
        return enumMsg == null ? EmptyEnum.NULL : enumMsg;
    }

    private static <E extends EnumMsg> Map<?, EnumMsg> getEnumMsgMap(Class<E> enumClass) {
        String className = enumClass.getName();
        Map<?, EnumMsg> enumMsgMap = ENUM_MAP.get(className);
        if (enumMsgMap != null)
            return enumMsgMap;

        EnumMsg[] enumMsgs;
        try {
            Method method = enumClass.getMethod("values");
            enumMsgs = (EnumMsg[]) method.invoke(null);
        } catch (Exception e) {
            enumMsgs = new EnumMsg[0];
        }
        enumMsgMap = Arrays.stream(enumMsgs).collect(Collectors.toMap(EnumMsg::getValue, enumMsg -> enumMsg));
        ENUM_MAP.putIfAbsent(className, enumMsgMap);
        return enumMsgMap;
    }
}
