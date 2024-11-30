package com.cooba.util;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cooba.entity.CdcEntity;
import com.cooba.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClickhouseConverter {
    private final OrderMapper orderMapper;
    private Map<String, Class<?>> typeMap;
    private final Map<String, BaseMapper> mapperMap = new HashMap<>();


    @PostConstruct
    public void init() {
        mapperMap.put("order", orderMapper);

        Reflections reflections = new Reflections("com.cooba.entity"); // Scan the entire classpath
        Set<Class<? extends CdcEntity>> subclasses = reflections.getSubTypesOf(CdcEntity.class);
        typeMap = subclasses.stream()
                .collect(Collectors.toMap(
                        clazz -> camelToSnake(clazz.getSimpleName()),
                        clazz -> clazz
                ));
    }

    public Class<?> getType(String table) {
        return typeMap.get(table);
    }

    public BaseMapper getMapper(String table) {
        return mapperMap.get(table);
    }


    public static String camelToSnake(String str) {
        StringBuilder result = new StringBuilder();

        char c = str.charAt(0);
        result.append(Character.toLowerCase(c));


        for (int i = 1; i < str.length(); i++) {

            char ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                result.append('_');
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}
