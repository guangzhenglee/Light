package com.light.web.core.resolver;


import com.light.common.annotation.LoginAuth;
import com.light.framework.util.ShiroUtils;
import com.light.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 登录用户参数解析器
 * author:ligz
 */
@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        final Method method = methodParameter.getMethod();
        final Class<?> clazz = Objects.requireNonNull(methodParameter.getMethod()).getDeclaringClass();

        boolean isHasLoginAuthAnn = clazz.isAnnotationPresent(LoginAuth.class) || Objects.requireNonNull(method).isAnnotationPresent(LoginAuth.class);
        boolean isHasLoginUserParameter = methodParameter.getParameterType().isAssignableFrom(SysUser.class);

        return isHasLoginAuthAnn && isHasLoginUserParameter;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory){
        return ShiroUtils.getSysUser();
    }
}
