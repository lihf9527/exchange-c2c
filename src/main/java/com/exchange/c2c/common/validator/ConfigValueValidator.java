package com.exchange.c2c.common.validator;

import com.exchange.c2c.common.annotation.ConfigValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfigValueValidator implements ConstraintValidator<ConfigValue, String> {
    private String prefix;

    @Override
    public void initialize(ConfigValue constraintAnnotation) {
        prefix = constraintAnnotation.prefix();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
