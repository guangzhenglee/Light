package com.light.framework.service;

import com.light.common.utils.StringUtil;
import com.light.framework.shiro.session.OnlineSession;
import com.light.system.domain.SysUserOnline;
import com.light.system.service.ISysUserOnlineService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 会话db操作处理
 * @author ligz
 */
@Component
public class SysShiroService {

    private final ISysUserOnlineService onlineService;

    @Autowired
    public SysShiroService(ISysUserOnlineService onlineService) {
        this.onlineService = onlineService;
    }

    /**
     * 删除会话
     *
     * @param onlineSession 会话信息
     */
    public void deleteSession(OnlineSession onlineSession){
        onlineService.deleteOnlineById(String.valueOf(onlineSession.getId()));
    }

    /**
     * 获取会话信息
     *
     * @param sessionId
     * @return
     */
    public Session getSession(Serializable sessionId){
        SysUserOnline userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
        return StringUtil.isNull(userOnline) ? null : createSession(userOnline);
    }

    private Session createSession(SysUserOnline userOnline){
        OnlineSession onlineSession = new OnlineSession();
        if (StringUtil.isNotNull(userOnline)){
            onlineSession.setId(userOnline.getSessionId());
            onlineSession.setHost(userOnline.getIpaddr());
            onlineSession.setBrowser(userOnline.getBrowser());
            onlineSession.setOs(userOnline.getOs());
            onlineSession.setDeptName(userOnline.getDeptName());
            onlineSession.setLoginName(userOnline.getLoginName());
            onlineSession.setStartTimestamp(userOnline.getStartTimestamp());
            onlineSession.setLastAccessTime(userOnline.getLastAccessTime());
            onlineSession.setTimeout(userOnline.getExpireTime());
        }
        return onlineSession;
    }
}
