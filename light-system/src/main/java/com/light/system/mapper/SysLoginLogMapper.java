package com.light.system.mapper;

import com.light.system.domain.SysLoginLog;

import java.util.List;

/**
 * 登录日志
 * author:ligz
 */
public interface SysLoginLogMapper {
    /**
     * 插入登录日志
     * @param sysLoginLog
     */
    void insertLoginLog(SysLoginLog sysLoginLog);

    /**
     * 查询登录日志
     * @param sysLoginLog
     * @return
     */
    List<SysLoginLog> selectLoginLogList(SysLoginLog sysLoginLog);

    /**
     * 根据ID批量删除登录日志
     * @param ids
     * @return
     */
    int deleteLoginLogByIds(String[] ids);

    /**
     * 清空登录表
     * @return
     */
    int cleanLoginLog();
}
