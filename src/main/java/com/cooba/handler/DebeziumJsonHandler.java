package com.cooba.handler;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cooba.dto.CdcKey;
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
        String table = JSONUtil.toBean(changeEvent.key(), CdcKey.class).getTable();
        String json = changeEvent.value();
        log.info("Key = {}, Value = {}", changeEvent.key(), json);

        Class<?> type = clickhouseConverter.getType(table);
        Object object = JSONUtil.toBean(json, type);
        log.info("{}", object);

        BaseMapper mapper = clickhouseConverter.getMapper(table);
        mapper.insert(object);

    }
}
