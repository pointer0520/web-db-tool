package com.dashu.dbtool.exception.handler;

import com.dashu.dbtool.common.response.CommonResult;
import com.dashu.dbtool.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    public CommonResult<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Validation exception occurred: {}", e.getMessage(), e);

        // 获取所有字段错误
        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ?
                                fieldError.getDefaultMessage() :
                                "验证失败",
                        (existing, replacement) -> existing + "; " + replacement
                ));

        return CommonResult.success(errors);
    }

    public CommonResult<Void> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return CommonResult.error();
    }
}
