package com.example.yuanMatch.exception;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-0410:45
 *@project yuanMatch
 */

import com.example.yuanMatch.common.ErrorCode;
import com.example.yuanMatch.common.baseRespond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(BusinessException.class)
    public baseRespond businessExceptionHandler(BusinessException e)
    {
        log.error("businessException",e);
        return new baseRespond(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public baseRespond runtimeExceptionHandler(RuntimeException e)
    {
        log.error("runtimeException",e);
        return new baseRespond(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }
}
