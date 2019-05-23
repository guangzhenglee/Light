package com.light.framework.web.domain.server;

import com.light.common.utils.ArithUtil;
import com.light.common.utils.DateUtil;

import java.lang.management.ManagementFactory;

/**
 * JVM相关信息
 * @author ligz
 */
public class Jvm {
    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public double getTotal() {
        return ArithUtil.div(total, (1024 * 1024), 2);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMax() {
        return ArithUtil.div(max, (1024 * 1024), 2);
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getFree() {
        return ArithUtil.div(free, (1024 * 1024), 2);
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getUsed() {
        return ArithUtil.div(total - free, (1024 * 1024), 2);
    }

    public double getUsage() {
        return ArithUtil.mul(ArithUtil.div(total - free, total, 4), 100);
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    /**
     * JDK启动时间
     */
    public String getStartTime() {
        return DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD_HH_MM_SS, DateUtil.getServerStartDate());
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        return DateUtil.getDatePoor(DateUtil.getNowDate(), DateUtil.getServerStartDate());
    }
}
