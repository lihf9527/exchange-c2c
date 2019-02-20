package com.exchange.c2c.common.validator;

import com.exchange.c2c.common.annotation.CurrencyCode;
import com.exchange.c2c.common.util.SpringUtils;
import com.exchange.c2c.enums.CurrencyTypeEnum;
import com.exchange.c2c.mapper.CurrencyMapper;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyCodeValidator implements ConstraintValidator<CurrencyCode, String> {
    private CurrencyTypeEnum currencyType;
    private boolean nullable;

    @Override
    public void initialize(CurrencyCode constraintAnnotation) {
        currencyType = constraintAnnotation.type();
        nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext constraintValidatorContext) {
        if (nullable && StringUtils.isEmpty(code))
            return true;

        if (!nullable && StringUtils.isEmpty(code))
            return false;

        CurrencyMapper currencyMapper = SpringUtils.getBean(CurrencyMapper.class);
        return currencyMapper.existsByTypeAndCode(currencyType.getValue(), code);
    }
}
