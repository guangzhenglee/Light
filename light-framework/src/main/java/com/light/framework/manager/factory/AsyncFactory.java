package com.light.framework.manager.factory;

import com.light.common.constant.Constants;
import com.light.common.utils.AddressUtils;
import com.light.common.utils.ServletUtils;
import com.light.common.utils.SpringUtils;
import com.light.framework.shiro.session.OnlineSession;
import com.light.framework.util.LogUtils;
import com.light.framework.util.ShiroUtils;
import com.light.system.domain.SysLoginLog;
import com.light.system.domain.SysOperLog;
import com.light.system.domain.SysUserOnline;
import com.light.system.service.ISysOperLogService;
import com.light.system.service.ISysUserOnlineService;
import com.light.system.service.impl.SysLoginLogServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 工厂
 * 生成异步的任务
 * author:ligz
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    private AsyncFactory() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordloginLog(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                // 打印信息到日志
                String s = LogUtils.getBlock(ip) +
                        AddressUtils.getRealAddressByIP(ip) +
                        LogUtils.getBlock(username) +
                        LogUtils.getBlock(status) +
                        LogUtils.getBlock(message);
                sys_user_logger.info(s, args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SysLoginLog loginLog = new SysLoginLog();
                loginLog.setLoginName(username);
                loginLog.setIpaddr(ip);
                loginLog.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
                loginLog.setBrowser(browser);
                loginLog.setOs(os);
                loginLog.setMsg(message);
                // 日志状态
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
                    loginLog.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    loginLog.setStatus(Constants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(SysLoginLogServiceImpl.class).insertLoginLog(loginLog);
            }
        };
    }

    /**
     * 操作日志的记录方法
     * @param operLog 操作日志
     * @return 任务
     */
    public static TimerTask recordOper(final SysOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                //远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }

    /**
     * 同步session到数据库
     *
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                SysUserOnline online = new SysUserOnline();
                online.setSessionId(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus(session.getStatus());
                SpringUtils.getBean(ISysUserOnlineService.class).saveOnline(online);

            }
        };
    }

}
