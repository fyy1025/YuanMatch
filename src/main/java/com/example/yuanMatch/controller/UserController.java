package com.example.yuanMatch.controller;


import com.example.yuanMatch.common.ErrorCode;
import com.example.yuanMatch.common.ResultUtils;
import com.example.yuanMatch.common.baseRespond;
import com.example.yuanMatch.contant.Constant;
import com.example.yuanMatch.exception.BusinessException;
import com.example.yuanMatch.models.domain.User;
import com.example.yuanMatch.models.request.UserLogInRequest;
import com.example.yuanMatch.models.request.UserRegisterRequest;
import com.example.yuanMatch.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-02-2811:34
 *@project yuanMatch
 */
@RequestMapping("user")
@RestController
@CrossOrigin
@Tag(name = "用户控制")
public class UserController
{
    @Resource
    private UserService userService;
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public baseRespond<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest)
    {
        //1.验证，数据是否为null
        if (userRegisterRequest == null)
        {
            //return ResultUtils.fail(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2.验证用户账号，密码，验证密码是否为空
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String invitationNumber = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, invitationNumber))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"注册信息为空");
        }
        //3.调用注册业务
        Long result = userService.userRegister(userAccount, userPassword, checkPassword, invitationNumber);
        //return l;
        return ResultUtils.success(result);
    }

    @Operation(summary = "用户登陆")
    @PostMapping("/login")
    public baseRespond<User> userLogin(@RequestBody UserLogInRequest userLoginRequest, HttpServletRequest request)
    {
        if (userLoginRequest == null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"注册信息为空");
        }
        User user = userService.userLogIn(userAccount, userPassword, request);

        return ResultUtils.success(user);
    }

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    @Operation(summary = "获取当前用户")
    @GetMapping("/current")
    public baseRespond<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(Constant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public baseRespond<Integer> userLogOut(HttpServletRequest request)
    {
        if (request == null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.userLogOut(request));

    }

    @Operation(summary = "搜索")
    //@GetMapping("/{userName}")
    @GetMapping("/search")
    public baseRespond<List<User>> queryUserById(String username, HttpServletRequest request)
    {
        if (StringUtils.isAnyBlank(username) || request == null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<User> users = userService.queryUserById(username, request);
        if(users.isEmpty())
        {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(users);
    }

    @Operation(summary = "管理员删除用户")
    //@DeleteMapping("/{userId}")
    @PostMapping("/delete")
    public baseRespond<Boolean> deleteUserById(@RequestBody long id, HttpServletRequest request)
    {
        if (id <= 0 || request == null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.deleteUserById(id, request);
        return ResultUtils.success(b);
    }

}
