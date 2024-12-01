package com.cooba.handler;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cooba.dto.CdcKey;
import com.cooba.dto.CdcValue;
import com.cooba.util.ClickhouseConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.engine.ChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebeziumJsonHandler implements Consumer<ChangeEvent<String, String>> {
    private final ClickhouseConverter clickhouseConverter;

    @Override
    public void accept(ChangeEvent<String, String> changeEvent) {
        log.info("Key = {}, Value = {}", changeEvent.key(), changeEvent.value());

        CdcKey cdcKey = JSONUtil.toBean(changeEvent.key(), CdcKey.class);
        String table = cdcKey.getTable();
        String json = changeEvent.value();

        Class<?> type = clickhouseConverter.getType(table);
        CdcValue cdcValue = new CdcValue(json, type);

        BaseMapper mapper = clickhouseConverter.getMapper(table);
        mapper.insert(cdcValue.getValue());

    }
}
