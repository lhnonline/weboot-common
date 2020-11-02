package com.github.lhnonline.boot.common.response;

import com.github.lhnonline.boot.common.util.HttpCode;
import lombok.Getter;
import lombok.Setter;

/**
 * author luohaonan
 * date 2020-11-11
 * email 0376lhn@gmail.com
 * description
 */
@Setter
@Getter
@SuppressWarnings("unused")
public class FormResult extends BaseResult {
    /**
     * 表单错误信息
     */
    protected FormErrors formErrors;

    @SuppressWarnings("unchecked")
    private FormResult(Integer code, Boolean success, String msg, Object data, Object error, FormErrors formErrors) {
        super(code, success, msg, data, error);
        this.formErrors = formErrors;
    }

    public static FormResult fail(String msg, FormErrors formErrors) {
        return new FormResult(HttpCode.BAD_REQUEST, false, msg, null, null, formErrors);
    }

    public static FormResult fail(Integer code, String msg, FormErrors formErrors) {
        return new FormResult(code, false, msg, null, null, formErrors);
    }

    public static FormResult fail(String msg, org.springframework.validation.Errors errors) {
        FormErrors formErrors = new FormErrors();
        StringBuilder sb = new StringBuilder();
        errors.getFieldErrors().forEach(e -> {
            formErrors.putErrorMsg(e.getField(), e.getDefaultMessage());
            sb.append(e.getDefaultMessage()).append(";");
            System.out.println();
        });
        formErrors.setDigest(sb.toString());
        return new FormResult(HttpCode.BAD_REQUEST, false, msg, null, null, formErrors);
    }

    public static FormResult success(String msg, Object data) {
        return new FormResult(HttpCode.OK, true, msg, data, null, null);
    }

    public static FormResult success() {
        return new FormResult(HttpCode.OK, true, null, null, null, null);
    }
}
