package com.github.lhnonline.boot.common.annotation;

import com.github.lhnonline.boot.common.config.HttpRequestLogAopReg;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * author luohaonan
 * date 2020-11-11
 * email 0376lhn@gmail.com
 * description 启用http请求日志
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({HttpRequestLogAopReg.class})
public @interface EnableHttpRequestLogAop {
}
