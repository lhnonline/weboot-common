package com.github.lhnonline.boot.common.aop;


import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.github.lhnonline.boot.common.log.AbstractLog;
import com.github.lhnonline.boot.common.log.DefaultLog;
import com.github.lhnonline.boot.common.log.ILogWriter;
import com.github.lhnonline.boot.common.properties.RequestLogConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author luohaonan
 * date 2020-11-11
 * email 0376lhn@gmail.com
 * description http请求日志记录切面
 */
@Aspect
@Slf4j
@SuppressWarnings("all")
public class HttpRequestLogAop {

    @Autowired
    private RequestLogConfig config;

    @Autowired
    private ILogWriter logWriter;


    public void doBefore(JoinPoint joinPoint, AbstractLog logInfo) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        logInfo.setRequestUri(request.getRequestURL().toString());
        logInfo.setRequestMethod(request.getMethod());
        if (config.getHeaderEnabled()) {
            if (config.getHeaderAll()) {
                Enumeration<String> headerNames = request.getHeaderNames();
                logInfo.setHeaderNames(headerNames);
                while (headerNames.hasMoreElements()) {
                    String h = headerNames.nextElement();
                    logInfo.getHeaderValues().put(h, request.getHeader(h));
                }
            } else if (config.getLogHeaderSet().isEmpty()) {
                for (String headerName : config.getLogHeaderSet()) {
                    String value = request.getHeader(headerName);
                    logInfo.getHeaderValues().put(headerName, request.getHeader(headerName));
                }
            }
        }
        logInfo.setMethodSignature(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        /////////////////////////////////////////////////////////////////////////////////////
        Object[] args = joinPoint.getArgs();
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.asList(args).stream();
        List<Object> logArgs = stream
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        logInfo.setArgs(logArgs.toArray());
    }


    public void doAfterReturning(Object ret, AbstractLog logInfo) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        logInfo.setRetValue(ret);
    }


    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object writeHttpCodeToHeader(ProceedingJoinPoint pjp) throws Throwable {
        DefaultLog logInfo = new DefaultLog();

        // 请求前
        doBefore(pjp, logInfo);

        // 执行请求
        Object result = pjp.proceed();

        // 执行请求后
        doAfterReturning(result, logInfo);

        // 记录日志
        logWriter.write(logInfo);

        // 返回结果给客户端
        return result;
    }
}
