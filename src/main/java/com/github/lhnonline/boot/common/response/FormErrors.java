package com.github.lhnonline.boot.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * author luohaonan
 * date 2020-11-11
 * email 0376lhn@gmail.com
 * description
 */
@Getter
@Setter
public class FormErrors {
    /**
     * 错误摘要
     */
    protected String digest;
    /**
     * 错误列表
     */
    protected Map<String, String> errorList = new HashMap<>();

    /**
     * @param field    表单字段
     * @param errorMsg 错误信息
     */
    public void putErrorMsg(String field, String errorMsg) {
        errorList.put(field, errorMsg);
    }
}
