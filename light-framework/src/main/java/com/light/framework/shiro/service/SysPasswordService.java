package com.light.framework.shiro.service;

import com.light.common.constant.Constants;
import com.light.common.exception.user.UserPasswordNotMatchException;
import com.light.common.exception.user.UserPasswordRetryLimitExceedException;
import com.light.common.utils.MessageUtils;
import com.light.framework.manager.AsyncManager;
import com.light.framework.manager.factory.AsyncFactory;
import com.light.system.domain.SysUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 验证密码是否正确
 * 判断是否密码错误超过了次数
 * author:ligz
 */
@Component
public class SysPasswordService {
    private Cache<String, AtomicInteger> loginRecordCache;//记录错误的次数

    private final CacheManager cacheManager;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    @Autowired
    public SysPasswordService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    @PostConstruct
    public void init() {
        loginRecordCache = cacheManager.getCache("loginRecordCache");
    }

    public void validate(SysUser user, String password) {
        String loginName = user.getLoginName();

        AtomicInteger retryCount = loginRecordCache.get(loginName);

        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if (retryCount.incrementAndGet() > Integer.parseInt(maxRetryCount)) {
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed" , maxRetryCount)));
            throw new UserPasswordRetryLimitExceedException(Integer.parseInt(maxRetryCount));
        }

        if (!matches(user, password)) {
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordloginLog(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count" , retryCount)));
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(SysUser user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    private void clearLoginRecordCache(String username) {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex();
    }



}
