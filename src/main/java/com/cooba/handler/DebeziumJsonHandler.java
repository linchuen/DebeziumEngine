package com.cooba.handler;

import cn.hutool.json.JSONUtil;
import com.cooba.dto.CdcKey;
import com.cooba.dto.CdcValue;
import com.cooba.enums.TableEnum;
import com.cooba.handler.table_handler.TableHandler;
import io.debezium.engine.ChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebeziumJsonHandler implements Consumer<ChangeEvent<String, String>> {
    private final TableHandlerFactory tableHandlerFactory;

    @Override
    public void accept(ChangeEvent<String, String> changeEvent) {
        log.info("Key = {}, Value = {}", changeEvent.key(), changeEvent.value());

        CdcKey cdcKey = JSONUtil.toBean(changeEvent.key(), CdcKey.class);
        String table = cdcKey.getTable();

        TableEnum tableEnum = TableEnum.getEnum(table);
        if (tableEnum == null) {
            log.warn("Unknown table type:{}", table);
            return;
        }

        Class<?> type = tableEnum.getEntityClass();
        CdcValue cdcValue = new CdcValue(changeEvent.value(), type);

        tableHandlerFactory.handle(tableEnum, cdcKey, cdcValue);
    }
}
