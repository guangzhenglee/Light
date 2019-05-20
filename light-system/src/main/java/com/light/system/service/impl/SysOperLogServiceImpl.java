package com.light.system.service.impl;

import com.light.common.utils.ConvertUtil;
import com.light.system.domain.SysOperLog;
import com.light.system.mapper.SysOperLogMapper;
import com.light.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 操作日志 服务层处理
 * author:ligz
 */
public class SysOperLogServiceImpl implements ISysOperLogService {
    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog) {
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return 删除记录数
     */
    @Override
    public int deleteOperLogByIds(String ids) {
        return operLogMapper.deleteOperLogByIds(ConvertUtil.toStrArray(ids));
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId) {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }
}
