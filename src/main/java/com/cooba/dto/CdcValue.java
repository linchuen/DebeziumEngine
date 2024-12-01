package com.cooba.dto;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cooba.entity.CdcEntity;
import lombok.Getter;

@Getter
public class CdcValue {
    private final Object value;

    public CdcValue(String json, Class<?> type) {
        JSONObject jsonObject = JSONUtil.parseObj(json);
        JSONObject before = jsonObject.getJSONObject("before");
        JSONObject after = jsonObject.getJSONObject("after");

        if (after == null) {
            before.set(CdcEntity.DELETED, true);
            value = JSONUtil.toBean(before, type);
        } else {
            after.set(CdcEntity.DELETED, false);
            value = JSONUtil.toBean(after, type);
        }
    }
}
