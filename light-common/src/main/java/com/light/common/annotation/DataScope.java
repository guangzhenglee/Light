package com.light.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限的过滤器,使用注解的形式过滤
 * author:ligz
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    /**
     * 表的别名,比如dept部门表为 "d"
     */
    String tableAlias() default "";
}
