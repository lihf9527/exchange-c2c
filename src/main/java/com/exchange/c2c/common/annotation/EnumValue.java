package com.exchange.c2c.common.annotation;

import com.exchange.c2c.common.EnumMsg;
import com.exchange.c2c.common.validator.EnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValueValidator.class})
public @interface EnumValue {
    String message() default "枚举值不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends EnumMsg> enumClass();

    boolean nullable() default false;

    String delimiter() default ",";
}
