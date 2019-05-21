package com.light.framework.manager;

import com.light.common.utils.SpringUtils;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 * author:ligz
 */
public class AsyncManager {
    /**
     * 操作延迟时间（单位毫秒）
     */
    private static final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private static ScheduledExecutorService executorService;

    /**
     * 单例创建
     */
    private static AsyncManager asyncManager = new AsyncManager();

    public static AsyncManager getAsyncManager() {
        executorService = SpringUtils.getBean("scheduledExecutorService");
        return asyncManager;
    }

    /**
     * 线程池执行任务
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executorService.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);//延迟10秒执行任务，用线程池执行
    }
}
