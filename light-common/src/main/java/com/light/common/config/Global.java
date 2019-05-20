package com.light.common.config;

import com.light.common.utils.StringUtil;
import com.light.common.utils.YamlUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类，获取配置信息
 * author:ligz
 */
@Slf4j
public class Global {
    private static final String NAME = "application.yml";

    private static final String CONFIG_KEY = "light.profile";

    /**
     * 单例模式创建
     */
    private static Global global = null;

    private Global(){}

    public static synchronized Global getInstance() {
        if (global == null) {
            synchronized(Global.class) {
                if (global == null) {
                    global = new Global();
                }
            }
        }
        return global;
    }

    /**
     * 用map的形式存储
     */
    private static Map<String, String> map = new HashMap<>();

    private static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            Map<?, ?> yamlMap;
            try {
                yamlMap = YamlUtil.loadYaml(NAME);
                value = String.valueOf(YamlUtil.getProperty(yamlMap, key));
                map.put(key, value != null ? value : StringUtil.EMPTY);
            } catch (Exception e) {
                log.error("获取全局配置失败 {}", key);
            }
        }
        return value;
    }

    /**
     * 获取项目名称
     */
    public static String getName() {
        return StringUtil.nvl(getConfig("light.name"), "light");
    }

    /**
     * 获取项目版本
     */
    public static String getVersion() {
        return StringUtil.nvl(getConfig("light.version"), "1.0.0");
    }

    /**
     * 获取版权年份
     */
    public static String getCopyrightYear() {
        LocalDate now = LocalDate.now();
        return String.valueOf(now.getYear());
    }

    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressEnabled() {
        return Boolean.valueOf(getConfig("light.addressEnabled"));
    }

    /**
     * 获取文件上传路径
     */
    public static String getProfile() {
        return getConfig(CONFIG_KEY);
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getConfig(CONFIG_KEY) + "avatar/" ;
    }

    /**
     * 获取下载上传路径
     */
    public static String getDownloadPath() {
        return getConfig(CONFIG_KEY) + "download/" ;
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath(){
        return getConfig(CONFIG_KEY) + "upload/";
    }

    /**
     * 获取作者
     */
    public static String getAuthor() {
        return StringUtil.nvl(getConfig("gen.author"), "light");
    }

    /**
     * 生成包路径
     */
    public static String getPackageName() {
        return StringUtil.nvl(getConfig("gen.packageName"), "com.light.project.module");
    }

    /**
     * 是否自动去除表前缀
     */
    public static String getAutoRemovePre() {
        return StringUtil.nvl(getConfig("gen.autoRemovePre"), "true");
    }

    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String getTablePrefix() {
        return StringUtil.nvl(getConfig("gen.tablePrefix"), "sys_");
    }
}
