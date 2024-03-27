package com.example.yuanMatch.models.request;

import lombok.Data;

import java.io.Serializable;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-0211:37
 *@project Center
 */
@Data
public class UserLogInRequest implements Serializable
{
    private static final long serialVersionUID = -7916784555750447385L;
    private String userAccount;
    private String userPassword;
}
