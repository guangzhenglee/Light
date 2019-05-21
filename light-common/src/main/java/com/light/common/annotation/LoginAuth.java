package com.light.common.annotation;

import java.lang.annotation.*;

/**
 * 已登录权限验证注解
 * author:ligz
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginAuth {

}
