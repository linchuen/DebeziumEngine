package com.cooba.handler.table_handler;

import com.cooba.dto.CdcKey;
import com.cooba.dto.CdcValue;
import com.cooba.entity.Order;
import com.cooba.enums.TableEnum;
import com.cooba.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderHandler implements TableHandler{
    private final OrderMapper orderMapper;
    @Override
    public void handle(CdcKey cdcKey, CdcValue cdcValue) {
        orderMapper.insert((Order) cdcValue.getValue());
    }

    @Override
    public TableEnum getTableEnum() {
        return TableEnum.order;
    }
}
