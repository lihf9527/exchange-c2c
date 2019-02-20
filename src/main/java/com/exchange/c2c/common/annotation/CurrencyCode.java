package com.exchange.c2c.common.annotation;

import com.exchange.c2c.common.validator.CurrencyCodeValidator;
import com.exchange.c2c.enums.CurrencyTypeEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CurrencyCode.List.class)
@Documented
@Constraint(validatedBy = {CurrencyCodeValidator.class})
public @interface CurrencyCode {
    String message() default "code不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    CurrencyTypeEnum type();

    boolean nullable() default false;

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CurrencyCode[] value();
    }
}
