package com.light.system.service.impl;

import com.light.common.constant.UserConstants;
import com.light.common.utils.ConvertUtil;
import com.light.system.domain.SysConfig;
import com.light.system.mapper.SysConfigMapper;
import com.light.system.service.ISysConfigService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数配置 服务层实现
 * author:ligz
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    /**
     * 查询参数配置信息
     * @param configId 参数配置ID
     * @return
     */
    @Override
    public SysConfig selectConfigById(Long configId) {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return sysConfigMapper.selectConfig(config);
    }

    /**
     * 根据键名查询参数配置信息
     * @param configKey 参数键名
     * @return
     */
    @Override
    public String selectConfigByKey(String configKey) {
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig res = sysConfigMapper.selectConfig(config);
        return ObjectUtils.allNotNull(res) ? res.getConfigValue() : "";
    }

    /**
     * 查询参数配置列表
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<SysConfig> selectConfigList(SysConfig config) {
        return sysConfigMapper.selectConfigList(config);
    }

    /**
     * 新增参数配置
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfig config) {
        return sysConfigMapper.insertConfig(config);
    }

    /**
     * 修改参数配置
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config) {
        return sysConfigMapper.updateConfig(config);
    }

    /**
     * 批量删除参数配置对象
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(String ids) {
        return sysConfigMapper.deleteConfigByIds(ConvertUtil.toStrArray(ids));
    }

    /**
     * 校验参数键名是否唯一
     * @param config 参数信息
     * @return
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        SysConfig info = sysConfigMapper.checkConfigKeyUnique(config.getConfigKey());
        if (ObjectUtils.allNotNull(info) && !info.getConfigId().equals(config.getConfigId())) {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }
}
