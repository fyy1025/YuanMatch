package com.example.yuanMatch.models.request;

import lombok.Data;

import java.io.Serializable;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-26 12:23
 *@project YuanMatch
 */
@Data
public class TeamQuitRequest implements Serializable
{

    // 开发者 [coder_yupi](https://space.bilibili.com/12890453/)

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * id
     */
    private Long teamId;

}
