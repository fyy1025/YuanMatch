package com.example.yuanMatch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yuanMatch.models.domain.UserTeam;
import com.example.yuanMatch.service.UserTeamService;
import com.example.yuanMatch.mappers.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author 96977
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-03-25 17:03:21
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




