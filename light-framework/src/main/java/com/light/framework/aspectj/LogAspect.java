package com.light.framework.aspectj;

import com.light.common.annotation.Log;
import com.light.common.enums.BusinessStatus;
import com.light.common.json.JSON;
import com.light.common.utils.ServletUtils;
import com.light.common.utils.StringUtil;
import com.light.framework.manager.AsyncManager;
import com.light.framework.manager.factory.AsyncFactory;
import com.light.framework.util.ShiroUtils;
import com.light.system.domain.SysOperLog;
import com.light.system.domain.SysUser;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录注解
 * author:ligz
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.light.common.annotation.Log")
    public void logPointCut() {
        //以注解作为织入点
    }

    /**
     * 处理完成后执行记录日志的行为
     * @param joinPoint
     */
    @AfterReturning
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常请求
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    private void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            //获得注解
            Log annotationLog = getAnnotationLog(joinPoint);
            if (annotationLog == null) return;
            //获取当前用户
            SysUser user = ShiroUtils.getSysUser();
            //记录数据库的日志
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            //请求地址
            String ip = ShiroUtils.getIp();
            operLog.setOperIp(ip);

            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (ObjectUtils.allNotNull(user)) {
                operLog.setOperName(user.getLoginName());
                if (ObjectUtils.allNotNull(user.getDept()) && StringUtil.isNotEmpty(user.getDept().getDeptName())) {
                    operLog.setDeptName(user.getDept().getDeptName());
                }
            }
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtil.substring(e.getMessage(), 0, 2000));
            }
            //设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 处理设置注解上的参数
            getMethodDescription(annotationLog, operLog);
            //线程池异步存储操作日志
            AsyncManager.getAsyncManager().execute(AsyncFactory.recordOper(operLog));

        } catch (Exception exp) {
            log.error("异常消息:{}", exp.getMessage());
        }
    }

    /**
     * 将注解的参数全部写到对象里面去
     * @param log
     * @param operLog
     * @throws Exception
     */
    private void getMethodDescription(Log log, SysOperLog operLog) throws Exception {
        //设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        //设置标题
        operLog.setTitle(log.title());
        //设置操作人员类别
        operLog.setOperatorType(log.operatorType().ordinal());
        //是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数
     * @param operLog
     * @throws Exception
     */
    private void setRequestValue(SysOperLog operLog) throws Exception {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        String params = JSON.marshal(map);
        operLog.setOperParam(params);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
