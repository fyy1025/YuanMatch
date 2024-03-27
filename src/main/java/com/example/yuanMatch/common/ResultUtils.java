package com.example.yuanMatch.common;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-03 15:35
 *@project yuanMatch
 */
public class ResultUtils
{
    /**
     * 返回成功
     * @param data
     * @return {@link baseRespond}<{@link T}>
     */
    public static <T>baseRespond<T> success(T data)
    {
        ErrorCode successCode = ErrorCode.SUCCESS;
        return new baseRespond<>(successCode.getCode(),data,successCode.getMessage(),"");
    }

    public static baseRespond fail(ErrorCode errorCode)
    {
        return new baseRespond(errorCode);
    }

    public static baseRespond fail(int code,String message,String description)
    {
        return new baseRespond(code,message,description);
    }

}
