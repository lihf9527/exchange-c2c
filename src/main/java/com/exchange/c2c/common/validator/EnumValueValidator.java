package com.exchange.c2c.common.validator;

import com.exchange.c2c.common.EnumMsg;
import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.common.util.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
    private Class<? extends EnumMsg> enumClass;
    private boolean nullable;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
        nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (nullable && value == null)
            return true;

        return EnumUtils.isValid(value, enumClass);
    }
}
