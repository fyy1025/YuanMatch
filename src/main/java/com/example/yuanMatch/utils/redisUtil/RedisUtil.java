package com.example.yuanMatch.utils.redisUtil;

import com.example.yuanMatch.models.domain.Team;

import java.util.*;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-26 10:31
 *@project YuanMatch
 */
public class RedisUtil
{
    public static Team ListToTeam(Map entries)
    {
        Team team = new Team();

        team.setId((Long)entries.get("id"));
        team.setName((String) entries.get("name"));
        team.setDescription((String) entries.get("description"));
        team.setMaxNum((Integer) entries.get("maxNum"));
        team.setExpireTime((Date) entries.get("expireTime"));
        team.setUserId((Long) entries.get("userId"));
        team.setStatus((Integer) entries.get("status"));
        team.setPassword((String) entries.get("password"));

        return team;
    }

    public static Map TeamToList(Team team)
    {
        Map<String, String> map = new HashMap<>();

        map.put("id",team.getId().toString());
        map.put("name",team.getName());
        map.put("description",team.getDescription());
        map.put("maxNum",team.getMaxNum().toString());
        map.put("expireTime",team.getExpireTime().toString());
        map.put("userId",team.getUserId().toString());
        map.put("status",team.getStatus().toString());
        map.put("password",team.getPassword());

        return map;
    }
}
