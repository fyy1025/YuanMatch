package com.example.yuanMatch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yuanMatch.common.ErrorCode;
import com.example.yuanMatch.contant.Constant;
import com.example.yuanMatch.exception.BusinessException;
import com.example.yuanMatch.mappers.UserMapper;
import com.example.yuanMatch.models.domain.User;
import com.example.yuanMatch.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.yuanMatch.contant.Constant.USER_LOGIN_STATE;

/**
 * @author fyy
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-02-28 18:43:06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService
{
    @Resource
    private UserMapper userMapper;

    private static final String SALT = "fyy";

    /**
     * 实现用户注册功能
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return long 用户id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String invitationNumber)
    {
        //1.1验证用户名，密码，验证密码不为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,invitationNumber))
        {
           throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        //1.2验证用户长度必须大于6和
        if (userAccount.length() <= 6)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        // 1.3密码长度必须大于6
        if (userPassword.length() <= 6 || checkPassword.length() <= 6)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        //1.4验证密码和新密码是否相同
        if (!userPassword.equals(checkPassword))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"新旧密码不一致");
        }
        // 1.5账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find())
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号包含非法字符");
        }
        //1.6 验证邀请编号
        if(invitationNumber.length()>=6)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"邀请编码过长");

        }
        //1.6账号不能已存在
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("user_Account", userAccount);
        Long count = userMapper.selectCount(objectQueryWrapper);
        if (count > 0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号已存在");
        }

        //1.7邀请编码不能已存在
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("invitation_Number", invitationNumber);
        count = userMapper.selectCount(queryWrapper2);
        if (count > 0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"邀请编码已存在");
        }
        // 2. 加密用户信息
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setInvitationNumber(invitationNumber);
        boolean saveResult = this.save(user);
        if (!saveResult)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数无效，注册失败");
        }
        return user.getId();
    }

    /**
     * 用户登陆
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @return long
     */
    @Override
    public User userLogIn(String userAccount, String userPassword, HttpServletRequest request)
    {
        //1.1验证用户名，密码，验证密码不为空
        if (StringUtils.isAnyBlank(userAccount, userPassword))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        //1.2验证用户长度必须大于6和
        if (userAccount.length() <= 6)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        // 1.3密码长度必须大于6
        if (userPassword.length() <= 6)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        // 1.5账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find())
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号包含非法字符");
        }

        //2.查询用户是否已注册
        //密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //用户查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_Account", userAccount);
        queryWrapper.eq("user_Password", encryptPassword);
        User user=this.getOne(queryWrapper);
        //User user = userMapper.selectOne();
        if (user == null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }

        //3.用户已注册，进行密码加密和数据脱敏！
        User safeUser = this.getSafetyUser(user);

        //4.记录用户的登陆态
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);
        return safeUser;
    }

    @Override
    public List<User> queryUserById(String userName, HttpServletRequest request)
    {
        //1.验证用户权限，不是管理员权限时，返回空列表
        if(valideUserRole(request))
        {
            throw new BusinessException(ErrorCode.NO_AUTH,"无查询权限");
        }
        //2.获取用户列表
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(userName))
        {
            queryWrapper.like("user_Name", userName);
        }
        List<User> userList = this.list(queryWrapper);
        List<User> list = userList.stream().map(user -> this.getSafetyUser(user)).collect(Collectors.toList());
        return list;

    }

    @Override
    public boolean deleteUserById(Long userId, HttpServletRequest request)
    {
        //1.验证用户权限，不是管理员权限时，返回空列表
        if(valideUserRole(request))
        {
            throw new BusinessException(ErrorCode.NO_AUTH,"无查询权限");
        }
        //2.获取用户列表
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(userId <=0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户id错误");
        }
        boolean remove = this.removeById(userId);
        return remove;

    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser)
    {
        //脱敏逻辑，只保留id，账号，账号状态，账号权限，创建时间！
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setValid(originUser.getValid());
        safetyUser.setRole(originUser.getRole());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setInvitationNumber(originUser.getInvitationNumber());
        return safetyUser;
    }

    /**
     *验证用户权限，为管理员返回true，否则返回false
     *
     * @param request
     * @return boolean
     */



    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogOut(HttpServletRequest request)
    {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return  0;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return (User) userObj;
    }

    @Override
    public boolean valideUserRole(HttpServletRequest request)
    {
        //1.获取用户信息
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)attribute;
        //2.验证用户权限
        return user.getRole() != Constant.ADMIN_ROLE ||user == null;

    }

    @Override
    public boolean valideUserRole(User loginUser)
    {
        return loginUser != null && loginUser.getRole() == Constant.ADMIN_ROLE;
    }
}




