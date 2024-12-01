package com.cooba.config;

import com.cooba.handler.DebeziumJsonHandler;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JsonEngineConfig {
    private final DebeziumJsonHandler debeziumJsonHandler;

    @PostConstruct
    public void executeJson() throws IOException {
        ClassPathResource resource = new ClassPathResource("mysql-source-jdbc.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);

        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(properties)
                .notifying(debeziumJsonHandler)
                .build();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);
    }
}
