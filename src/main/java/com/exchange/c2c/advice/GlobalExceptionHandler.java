package com.exchange.c2c.advice;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handler(MissingServletRequestParameterException e) {
        return Result.failure("缺少参数'" + e.getParameterName() + "'", e.getMessage());
    }

    @ExceptionHandler
    public Result handler(HttpRequestMethodNotSupportedException e) {
        return Result.failure("请求方法'" + e.getMethod() + "'不支持", e.getMessage());
    }

    @ExceptionHandler
    public Result handler(MethodArgumentTypeMismatchException e) {
        return Result.failure("'" + e.getName() + "'参数类型不正确", e.getMessage());
    }

    @ExceptionHandler
    public Result handler(ConstraintViolationException e) {
        String message = e.getMessage().split(": ")[1];
        return Result.failure(message, e.getMessage());
    }

    @ExceptionHandler
    public Result handler(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        if ("typeMismatch".equals(fieldError.getCode()))
            return Result.failure("'" + fieldError.getField() + "'参数类型不正确", fieldError.getDefaultMessage());

        return Result.failure(fieldError.getDefaultMessage());
    }

    @ExceptionHandler
    public Result handler(BizException e) {
        return Result.of(e.getCode(), e.getMessage());
    }

    @ExceptionHandler
    public Result handler(NoHandlerFoundException e) {
        return Result.of(404, "Not Found", e.getMessage());
    }

    @ExceptionHandler
    public Result handler(MaxUploadSizeExceededException e) {
        return Result.failure("文件过大");
    }

    @ExceptionHandler
    public Result handler(Exception e) {
        log.error("系统异常 ==> " + e.getMessage(), e);
        String errorMsg = e + " at " + e.getStackTrace()[0];
        return Result.of(500, "系统异常", errorMsg);
    }
}
