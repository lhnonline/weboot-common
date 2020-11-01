package com.github.lhnonline.boot.common.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.lhnonline.boot.common.properties.RequestLogConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author luohaonan
 * @date 2020-11-11
 * @email 0376lhn@gmail.com
 * @description
 */
@SuppressWarnings("all")
@Slf4j
public class DefaultLogWriter implements ILogWriter, BeanFactoryAware {

    private BeanFactory beanFactory;

    private RequestLogConfig config;

    static String ArraysToString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();

        if (iMax > 0) {
            b.append('[');
        }

        for (int i = 0; ; i++) {
            b.append(JSON.toJSONString(a[i], SerializerFeature.WriteMapNullValue));
            if (i == iMax) {
                if (iMax > 0) {
                    return b.append(']').toString();
                }
                return b.toString();
            }
            b.append(", ");
        }
    }

    @Override
    public void write(AbstractLog logInfo) {
        log.info("<== {} {}", logInfo.getRequestUri(), logInfo.getRequestMethod());
        if (config.getHeaderEnabled()) {
            if (config.getHeaderAll()) {
                Enumeration<String> headerNames = logInfo.getHeaderNames();
                logInfo.getHeaderValues().forEach((k, v) -> {
                    log.info("HEADER:  {}  ->  {}", k, v);
                });
            } else if (!config.getLogHeaderSet().isEmpty()) {
                config.getLogHeaderSet().forEach(h -> {
                    Object o1 = null;
                    try {
                        o1 = logInfo.getHeaderValues().get(h);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    log.info("header {}:  {}", h, o1);
                });
            }
        }
        log.info("请求方法: {}", logInfo.getMethodSignature());
        log.info("使用参数: {}", Arr2Str(logInfo.getArgs()));
        log.info("==> {}", logInfo.getRetValue());

    }

    @SuppressWarnings("unchecked")
    private String Arr2Str(Object[] arr) {
        if (arr == null || arr.length == 0)
            return "";

        ArrayList paramArray = new ArrayList();
        for (Object o : arr) {
            if (config.getIgnoreTypeSet().contains(o.getClass())) {
                continue; //ignore this request param
            }
            paramArray.add(o);
        }
        return ArraysToString(paramArray.toArray());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        this.config = beanFactory.getBean(RequestLogConfig.class);
    }
}
