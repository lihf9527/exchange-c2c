package com.exchange.c2c.common;

public interface EnumMsg<T> {
    T getValue();

    String getName();

    default boolean isEmpty() {
        return false;
    }
}
