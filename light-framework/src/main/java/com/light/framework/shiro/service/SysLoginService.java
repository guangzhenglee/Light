package com.light.framework.shiro.service;

import com.light.common.constant.Constants;
import com.light.common.constant.ShiroConstants;
import com.light.common.constant.UserConstants;
import com.light.common.enums.UserStatus;
import com.light.common.exception.user.*;
import com.light.common.utils.DateUtil;
import com.light.common.utils.MessageUtils;
import com.light.common.utils.ServletUtils;
import com.light.framework.manager.AsyncManager;
import com.light.framework.manager.factory.AsyncFactory;
import com.light.framework.util.ShiroUtils;
import com.light.system.domain.SysUser;
import com.light.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 登录校验方法
 *
 * @author ligz
 */
@Component
public class SysLoginService {

    private final SysPasswordService passwordService;

    private final ISysUserService userService;

    @Autowired
    public SysLoginService(SysPasswordService passwordService, ISysUserService userService) {
        this.passwordService = passwordService;
        this.userService = userService;
    }

    /**
     * 登录
     */
    public SysUser login(String username, String password) {
        // 验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 查询用户信息
        SysUser user = userService.selectUserByLoginName(username);

        if (user == null && maybeMobilePhoneNumber(username)) {
            user = userService.selectUserByPhoneNumber(username);
        }

        if (user == null && maybeEmail(username)) {
            user = userService.selectUserByEmail(username);
        }

        if (user == null) {
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }

        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
            throw new UserDeleteException();
        }

        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked" , user.getRemark())));
            throw new UserBlockedException();
        }

        passwordService.validate(user, password);

        AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(user);
        return user;
    }

    private boolean maybeEmail(String username) {
        return username.matches(UserConstants.EMAIL_PATTERN);
    }

    private boolean maybeMobilePhoneNumber(String username) {
        return username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN);
    }

    /**
     * 记录登录信息
     */
    private void recordLoginInfo(SysUser user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtil.getNowDate());
        userService.updateUserInfo(user);
    }
}
