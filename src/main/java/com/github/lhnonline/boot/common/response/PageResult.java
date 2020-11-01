package com.github.lhnonline.boot.common.response;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.lhnonline.boot.common.util.HttpCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author luohaonan
 * @date 2020-11-11
 * @email 0376lhn@gmail.com
 * @description
 */
@Getter
@Setter
@SuppressWarnings("all")
public class PageResult<T> extends BaseResult<T> {

    /**
     * 总记录条数
     */
    private Long total;

    protected PageResult(Integer code, Boolean success, String msg, T data, T error, Long total) {
        super(code, success, msg, data, error);
        setTotal(total);
    }

    public static PageResult fromMybatisPlusPage(IPage pageInfo) {
        long total = pageInfo.getTotal();
        List records = pageInfo.getRecords();
        return new PageResult(HttpCode.OK, true, null, records, null, total);
    }

    public static PageResult fromMybatisPlusPage(IPage pageInfo, String msg) {
        PageResult result = PageResult.fromMybatisPlusPage(pageInfo);
        result.setMsg(msg);
        return result;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
