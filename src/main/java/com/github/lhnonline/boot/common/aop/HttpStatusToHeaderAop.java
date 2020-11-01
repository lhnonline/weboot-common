package com.github.lhnonline.boot.common.aop;


import com.github.lhnonline.boot.common.response.BaseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @author luohaonan
 * @date 2020-11-11
 * @email 0376lhn@gmail.com
 * @description 把返回结果中的状态码写入http状态码的切面
 */
@Aspect
public class HttpStatusToHeaderAop {

    @SuppressWarnings("all")
    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object writeHttpCodeToHeader(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object result = pjp.proceed();
            if (result instanceof BaseResult) {
                HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                httpServletResponse.setStatus(((BaseResult) result).getCode());
            }
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            return BaseResult.fail("发生错误", e.getLocalizedMessage());
        }
    }
}
