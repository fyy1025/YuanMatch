package com.example.yuanMatch.contant;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-0210:48
 *@project Center
 */
public interface Constant
{
    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    //  ------- 权限 --------

    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    // https://space.bilibili.com/12890453/

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;


    String User_Team_INFO_REDIS_NAME = "yuanMatch:UserTeam:";

    String TEAM_INFO_NAME="yuanMatch:Team:";
}
