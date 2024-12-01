package com.cooba.enums;

import com.clickhouse.client.internal.google.common.base.Functions;
import com.cooba.entity.Order;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum TableEnum {
    order("order", Order.class);

    TableEnum(String name, Class<?> entityClass) {
        this.name = name;
        this.entityClass = entityClass;
    }

    private final String name;
    private final Class<?> entityClass;

    private static final Map<String, Class<?>> map = Arrays.stream(TableEnum.values()).collect(Collectors.toMap(TableEnum::getName, TableEnum::getEntityClass));
    private static final Map<String, TableEnum> enumMap = Arrays.stream(TableEnum.values()).collect(Collectors.toMap(TableEnum::getName, Function.identity()));

    public static Class<?> getClass(String name) {
        return map.get(name);
    }

    public static TableEnum getEnum(String name) {
        return enumMap.get(name);
    }
}
