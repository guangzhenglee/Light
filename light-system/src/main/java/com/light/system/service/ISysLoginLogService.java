package com.light.system.service;

import com.light.system.domain.SysLoginLog;

import java.util.List;


/**
 * 系统访问日志情况信息 服务层
 *
 * @author ligz
 */
public interface ISysLoginLogService {
    /**
     * 新增系统登录日志
     * @param loginLog 访问日志对象
     */
    void insertLoginLog(SysLoginLog loginLog);

    /**
     * 查询系统登录日志集合
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
    List<SysLoginLog> selectLoginLogList(SysLoginLog loginLog);

    /**
     * 批量删除系统登录日志
     * @param ids 需要删除的数据
     * @return 删除记录数
     */
    int deleteLoginLogByIds(String ids);

    /**
     * 清空系统登录日志
     */
    void cleanLoginLog();
}
