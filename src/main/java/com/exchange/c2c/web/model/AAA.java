package com.exchange.c2c.web.model;

public interface AAA<T> {
    default void aaa(T t) {
        System.out.println(t);
    }
}
