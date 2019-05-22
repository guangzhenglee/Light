package com.light.framework.aspectj;

import com.light.common.annotation.DataScope;
import com.light.common.base.BaseEntity;
import com.light.common.utils.StringUtil;
import com.light.framework.util.ShiroUtils;
import com.light.system.domain.SysRole;
import com.light.system.domain.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * 数据过滤的切面
 * 控制数据访问的权限
 * author:ligz
 */
@Aspect
@Component
public class DataScopeAspect {
    /**
     * 全部的数据权限
     */
    private static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定义的数据权限
     */
    private static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 数据权限过滤关键字
     */
    private static final String DATA_SCOPE = "dataScope";

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.light.common.annotation.DataScope)")
    public void dataScopePointCut() {
        //配置织入点
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) {
        handleDataScope(point);
    }

    /**
     * 获得别名给数据加上权限
     * @param joinPoint
     */
    private void handleDataScope(final JoinPoint joinPoint) {
        //获得注解
        DataScope dataScope = getAnnotationLog(joinPoint);
        if (dataScope == null) return;
        //获得当前用户
        SysUser user = ShiroUtils.getSysUser();
        if (!ObjectUtils.isEmpty(user) && !user.isAdmin()) {
            //如果是超级管理员，则不需要过滤数据
            dataScopeFilter(joinPoint, user, dataScope.tableAlias());
        }
    }

    /**
     * 通过反射获得注解
     * @param joinPoint
     * @return
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();//获得织入点的方法
        if (method != null) {
            return method.getAnnotation(DataScope.class);//通过方法得到注解
        }
        return null;
    }

    /**
     * 真正执行数据过滤的逻辑
     * @param joinPoint
     * @param user
     * @param alias
     */
    private static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String alias) {
        StringBuilder sqlString = new StringBuilder();
        //获得用户的角色
        for (SysRole role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString = new StringBuilder();
                break;
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                //如果角色都是自定义的权限dataScope = 2时，如果alias = d,即过滤该用户所在部门的数据出来
                sqlString.append(StringUtil.format(" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ",
                        alias, role.getRoleId()));
            }
            if (StringUtil.isNotEmpty(sqlString.toString())) {
                BaseEntity baseEntity = (BaseEntity) joinPoint.getArgs()[0];
                baseEntity.getParams().put(DATA_SCOPE,  " AND (" + sqlString.substring(4) + ")");
            }
        }
    }
}
