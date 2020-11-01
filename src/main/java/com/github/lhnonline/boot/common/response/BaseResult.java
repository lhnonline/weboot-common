package com.github.lhnonline.boot.common.response;

import com.alibaba.fastjson.JSONObject;
import com.github.lhnonline.boot.common.util.HttpCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author luohaonan
 * @date 2020-11-11
 * @email 0376lhn@gmail.com
 * @description
 */
@Getter
@Setter
@SuppressWarnings({"all"})
public class BaseResult<T> {
    /**
     *
     */
    protected Integer code;
    /**
     * 是否成功
     */
    protected Boolean success;
    /**
     * 成功消息，或者错误消息
     */
    protected String msg;
    /**
     * 成功时返回的数据
     */
    protected T data;
    /**
     * 错误时候返回的数据
     * <br/>
     * 如果提交表单时候，不想用lhnonline.github.online.common.util.FormResult返回错误信息，可以把错误信息放到error中
     */
    protected T error;

    protected BaseResult(Integer code, Boolean success, String msg, T data, T error) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
        this.error = error;
    }

    public static <T> BaseResult newInstance(Integer code, Boolean success, String msg, T error, T data) {
        return new BaseResult<>(code, success, msg, data, error);
    }

    public static <T> BaseResult success(Integer code, String msg, T data) {
        return BaseResult.newInstance(code, true, msg, null, data);
    }

    public static <T> BaseResult success(String msg, T data) {
        return BaseResult.newInstance(HttpCode.OK, true, msg, null, data);
    }

    public static <T> BaseResult triumph(T data) {
        return BaseResult.newInstance(HttpCode.OK, true, null, null, data);
    }

    public static <T> BaseResult success(T data) {
        return BaseResult.newInstance(HttpCode.OK, true, "success", null, data);
    }


    public static <T> BaseResult 成功(T data) {
        return BaseResult.newInstance(HttpCode.OK, true, "成功", null, data);
    }

    public static <T> BaseResult success() {
        return BaseResult.newInstance(HttpCode.OK, true, "success", null, null);
    }

    public static <T> BaseResult triumph() {
        return BaseResult.newInstance(HttpCode.OK, true, null, null, null);
    }

    public static <T> BaseResult 成功() {
        return BaseResult.newInstance(HttpCode.OK, true, "成功", null, null);
    }


    public static <T> BaseResult fail(String msg, T error) {
        return BaseResult.newInstance(HttpCode.BAD_REQUEST, false, msg, error, null);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
