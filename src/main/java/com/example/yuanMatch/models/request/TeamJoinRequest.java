package com.example.yuanMatch.models.request;

import lombok.Data;

import java.io.Serializable;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-26 12:10
 *@project YuanMatch
 */
@Data
public class TeamJoinRequest implements Serializable
{

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;
}
