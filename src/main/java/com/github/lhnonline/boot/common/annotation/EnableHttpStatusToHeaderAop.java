package com.github.lhnonline.boot.common.annotation;

import com.github.lhnonline.boot.common.config.HttpStatusToHeaderAopReg;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author luohaonan
 * @date 2020-11-11
 * @email 0376lhn@gmail.com
 * @description 开启把返回结果中的状态码写入http状态码
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({HttpStatusToHeaderAopReg.class})
public @interface EnableHttpStatusToHeaderAop {
}
