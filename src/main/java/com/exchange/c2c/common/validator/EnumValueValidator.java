package com.exchange.c2c.common.validator;

import com.exchange.c2c.common.EnumMsg;
import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.common.util.ClassUtils;
import com.exchange.c2c.common.util.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
    private Class<? extends EnumMsg> enumClass;
    private boolean nullable;
    private String delimiter;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
        nullable = constraintAnnotation.nullable();
        delimiter = constraintAnnotation.delimiter();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (nullable) {
            if (value == null)
                return true;

            if (value instanceof String && "".equals(value)) {
                return true;
            }
        } else {
            if (value == null)
                return false;

            if (value instanceof String && "".equals(value))
                return false;
        }

        if (value instanceof String) {
            Class genericClass = ClassUtils.getGenericClass(enumClass, 0);
            String[] values = String.valueOf(value).split(delimiter);
            for (String val : values) {
                if (!EnumUtils.isValid(genericClass.cast(val), enumClass))
                    return false;
            }

            return true;
        }

        return EnumUtils.isValid(value, enumClass);
    }
}
