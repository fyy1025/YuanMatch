package com.example.yuanMatch.service;

import com.example.yuanMatch.models.TeamQuery;
import com.example.yuanMatch.models.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yuanMatch.models.domain.User;
import com.example.yuanMatch.models.vo.TeamUserVO;
import com.example.yuanMatch.models.request.TeamJoinRequest;
import com.example.yuanMatch.models.request.TeamQuitRequest;
import com.example.yuanMatch.models.request.TeamUpdateRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 96977
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2024-03-25 16:59:13
*/
public interface TeamService extends IService<Team> {

    @Transactional(rollbackFor = Exception.class)
    long addTeam(Team team, User loginUser);

    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    Team getTeamById(Long teamId);

    @Transactional(rollbackFor = Exception.class)
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);


    @Transactional(rollbackFor = Exception.class)
    boolean deleteTeam(long id, User loginUser);

    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

}
