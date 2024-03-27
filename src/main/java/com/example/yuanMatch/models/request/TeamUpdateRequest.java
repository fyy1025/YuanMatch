package com.example.yuanMatch.models.request;

import lombok.Data;

import java.util.Date;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-26 9:47
 *@project YuanMatch
 */
@Data
public class TeamUpdateRequest
{
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * id
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 0 - 公开，1 - 加密
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;
}
