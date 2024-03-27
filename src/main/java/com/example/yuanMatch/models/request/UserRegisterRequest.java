package com.example.yuanMatch.models.request;

import lombok.Data;

import java.io.Serializable;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-0210:58
 *@project Center
 */
@Data
public class UserRegisterRequest implements Serializable
{
    private static final long serialVersionUID = 3028404589588072228L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;

}
