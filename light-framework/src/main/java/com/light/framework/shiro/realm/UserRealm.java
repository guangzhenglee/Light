package com.light.framework.shiro.realm;

import com.light.common.exception.user.*;
import com.light.framework.shiro.service.SysLoginService;
import com.light.framework.util.ShiroUtils;
import com.light.system.domain.SysUser;
import com.light.system.service.ISysMenuService;
import com.light.system.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * shiro自定义Realm，处理登陆验证逻辑
 * author:ligz
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private SysLoginService loginService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = ShiroUtils.getSysUser();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (user.isAdmin()) {//超级管理员拥有全部的权限
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        } else {
            //角色列表
            Set<String> roles = roleService.selectRoleKeys(user.getUserId());
            //菜单列表
            Set<String> menus = menuService.selectPermsByUserId(user.getUserId());
            //将角色和权限加入到认证的对象里面去
            info.setRoles(roles);
            info.setStringPermissions(menus);
        }
        return info;
    }

    /**
     * 登陆认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "" ;
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

        SysUser user;
        try {
            user = loginService.login(username, password);
        } catch (CaptchaException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException | RoleBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            log.info(String.format("对用户[%s]进行登录验证..验证未通过%s" ,username, e.getMessage()), e);
            throw new AuthenticationException(e.getMessage(), e);
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

}
