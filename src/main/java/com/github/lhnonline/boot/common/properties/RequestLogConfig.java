package com.github.lhnonline.boot.common.properties;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * author luohaonan
 * date 2020-11-11
 * email 0376lhn@gmail.com
 * description
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = "weboot.log")
public class RequestLogConfig {

    private String ignoreParamTypes = "org.springframework.validation.BeanPropertyBindingResult,";
    private Boolean headerEnabled = Boolean.FALSE;
    private Boolean headerAll = Boolean.FALSE;
    private String headerNames = "host,token";

    private Set<Class> ignoreTypeSet;
    private Set<String> logHeaderSet;

    @PostConstruct
    public void init() {
        ignoreTypeSet = new LinkedHashSet<>();
        logHeaderSet = new LinkedHashSet<>();
        if (!StringUtils.isEmpty(ignoreParamTypes)) {
            String[] paraTypes = ignoreParamTypes.split(",");
            List<String> clazz = Arrays.asList(paraTypes);
            clazz.forEach(c -> {
                try {
                    Class<?> aClass = Class.forName(c);
                    ignoreTypeSet.add(aClass);
                } catch (ClassNotFoundException e) {
                    log.warn("skip unrecognized class type '{}' when config weboot.log.ignore-param-types", c);
                }
            });
        }

        if (headerEnabled && null != headerNames) {
            String[] logHeaderArray = headerNames.split(",");
            logHeaderSet.addAll(Arrays.asList(logHeaderArray));
        }
    }

    public String toString() {
        return JSON.toJSON(this).toString();
    }
}
