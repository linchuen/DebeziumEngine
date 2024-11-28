package com.cooba.service.impl;

import com.cooba.handler.DebeziumJsonHandler;
import com.cooba.service.EngineService;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EngineServiceImpl implements EngineService {
    private final Map<String, ExecutorService> executorServices = new ConcurrentHashMap<>();
    private final Map<String, DebeziumEngine<?>> engineMap = new ConcurrentHashMap<>();
    private final DebeziumJsonHandler debeziumJsonHandler;

    @Override
    public void createEngine(Properties properties) {
        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(properties)
                .notifying(debeziumJsonHandler)
                .build();
        engineMap.put(properties.getProperty("name"), engine);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executorServices.put(properties.getProperty("name"), executor);
        executor.execute(engine);
    }

    @Override
    public void closeEngine(String name) {
        executorServices.get(name).shutdownNow();

        try {
            engineMap.get(name).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void preDestroyEngine() {
        executorServices.forEach((name, executorService) -> executorService.shutdownNow());

        engineMap.forEach((name, engine) -> {
            try {
                engine.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
