package com.light.system.mapper;

import com.light.system.domain.SysConfig;

import java.util.List;

/**
 * 参数配置
 * author:ligz
 */
public interface SysConfigMapper {
    /**
     * 查询单个参数配置信息
     * @param sysConfig 参数配置信息
     * @return 参数配置信息
     */
    SysConfig selectConfig(SysConfig sysConfig);

    /**
     * 根据条件查询多个参数配置信息
     * @param sysConfig 参数配置信息
     * @return 参数配置信息列表
     */
    List<SysConfig> selectConfigList(SysConfig sysConfig);

    /**
     * 根据参数配置的键查找单个参数配置信息
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    SysConfig checkConfigKeyUnique(String configKey);

    /**
     * 插入参数配置
     * @param sysConfig 参数配置信息
     * @return
     */
    int insertConfig(SysConfig sysConfig);

    /**
     * 更新参数配置
     * @param sysConfig 参数配置信息
     * @return
     */
    int updateConfig(SysConfig sysConfig);

    /**
     * 删除参数配置
     * @param configIds 需要删除的数据ID
     * @return
     */
    int deleteConfigByIds(String[] configIds);
}
