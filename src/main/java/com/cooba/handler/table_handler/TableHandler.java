package com.cooba.handler.table_handler;

import com.cooba.dto.CdcKey;
import com.cooba.dto.CdcValue;
import com.cooba.enums.TableEnum;

public interface TableHandler {

    void handle(CdcKey cdcKey, CdcValue cdcValue);

    TableEnum getTableEnum();
}
