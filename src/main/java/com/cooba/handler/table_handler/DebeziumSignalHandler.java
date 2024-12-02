package com.cooba.handler.table_handler;

import com.cooba.dto.CdcKey;
import com.cooba.dto.CdcValue;
import com.cooba.enums.TableEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebeziumSignalHandler implements TableHandler{
    @Override
    public void handle(CdcKey cdcKey, CdcValue cdcValue) {
        log.info("ignore debezium_signal table");
    }

    @Override
    public TableEnum getTableEnum() {
        return TableEnum.debezium_signal;
    }
}
