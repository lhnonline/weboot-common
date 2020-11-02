package com.github.lhnonline.boot.common.log;

import lombok.Getter;
import lombok.Setter;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
/**
 * author luohaonan
 * date 2020-11-11
 * email 0376lhn@gmail.com
 * description
 */
public abstract class AbstractLog {
    protected String requestUri;
    protected String requestMethod;
    protected Object retValue;
    protected Enumeration<String> headerNames;
    protected Map<String, Object> headerValues = new LinkedHashMap<>();
    protected String methodSignature;
    protected Object[] args;
}
