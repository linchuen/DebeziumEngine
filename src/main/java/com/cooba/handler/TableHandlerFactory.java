package com.cooba.handler;

import com.cooba.dto.CdcKey;
import com.cooba.dto.CdcValue;
import com.cooba.enums.TableEnum;
import com.cooba.handler.table_handler.TableHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TableHandlerFactory {
    private final Map<TableEnum, TableHandler> tableHandlerMap;

    public TableHandlerFactory(List<TableHandler> tableHandlers) {
        this.tableHandlerMap = tableHandlers.stream()
                .collect(Collectors.toMap(
                        TableHandler::getTableEnum,
                        Function.identity()
                ));
    }

    public TableHandler getHandler(TableEnum tableEnum) {
        return tableHandlerMap.get(tableEnum);
    }

}
