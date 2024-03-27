package com.example.yuanMatch.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yuanMatch.models.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author fyy
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-02-28 18:43:06
*/
@Service
public interface UserService extends IService<User>
{
    /**
     * 用户注册功能
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return long 用户id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword,
                      String invitationNumber);
    /**
     * 用户登陆
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @return long
     */
    User userLogIn(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 根据用户id查询用户信息
     * @param userName
     * @param request
     * @return {@link List}<{@link User}>
     */
    List<User> queryUserById(String userName, HttpServletRequest request);

    /**
     * 根据用户id删除用户
     * @param userId
     * @param request
     * @return boolean
     */
    boolean deleteUserById(Long userId, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     *验证用户权限，为管理员返回true，否则返回false
     *
     * @param request
     * @return boolean
     */
    boolean valideUserRole(HttpServletRequest request);

    /**
     * 用户注销
     * @param request
     */
    int userLogOut(HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);

    boolean valideUserRole(User loginUser);
}
