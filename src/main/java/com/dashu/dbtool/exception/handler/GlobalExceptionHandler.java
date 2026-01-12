package com.dashu.dbtool.exception.handler;

import com.dashu.dbtool.common.response.CommonResult;
import com.dashu.dbtool.common.response.ResponseCode;
import com.dashu.dbtool.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public CommonResult<Void> handleBusinessException(BusinessException e) {
        log.error("Business exception occurred: {}", e.getMessage(), e);
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        log.warn("参数检验失败: {}", message);
        return CommonResult.error(ResponseCode.VERIFICATION_PARAM_ERROR, message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String message = "缺少必须参数：" + e.getParameterName();
        log.warn(message);
        return CommonResult.error(ResponseCode.VERIFICATION_PARAM_ERROR, message);
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<Void> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return CommonResult.error();
    }
}
