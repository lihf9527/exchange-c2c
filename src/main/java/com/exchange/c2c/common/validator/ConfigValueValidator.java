package com.exchange.c2c.common.validator;

import com.exchange.c2c.common.annotation.ConfigValue;
import com.exchange.c2c.common.util.SpringUtils;
import com.exchange.c2c.mapper.ConfigMapper;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfigValueValidator implements ConstraintValidator<ConfigValue, String> {
    private String prefix;
    private boolean nullable;
    private boolean multiple;
    private String delimiter;

    @Override
    public void initialize(ConfigValue constraintAnnotation) {
        prefix = constraintAnnotation.prefix();
        nullable = constraintAnnotation.nullable();
        multiple = constraintAnnotation.multiple();
        delimiter = constraintAnnotation.delimiter();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (nullable && StringUtils.isEmpty(value))
            return true;

        if (!nullable && StringUtils.isEmpty(value))
            return false;

        ConfigMapper configMapper = SpringUtils.getBean(ConfigMapper.class);
        if (multiple) {
            for (String s : value.split(delimiter)) {
                if (!configMapper.exists(prefix, s))
                    return false;
            }
            return true;
        }
        return configMapper.exists(prefix, value);
    }
}
