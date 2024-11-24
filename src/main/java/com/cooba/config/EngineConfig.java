package com.cooba.config;

import com.cooba.handler.DebeziumConsumer;
import com.cooba.handler.DebeziumJsonConsumer;
import io.debezium.embedded.Connect;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import io.debezium.engine.format.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
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
public class EngineConfig {
    private final DebeziumConsumer debeziumConsumer;
    private final DebeziumJsonConsumer debeziumJsonConsumer;

    public void execute() throws IOException {
        ClassPathResource resource = new ClassPathResource("mysql-source.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);

        DebeziumEngine<RecordChangeEvent<SourceRecord>> engine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(properties)
                .notifying(debeziumConsumer)
                .build();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);
    }

    @PostConstruct
    public void executeJson() throws IOException {
        ClassPathResource resource = new ClassPathResource("mysql-source.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);

        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(properties)
                .notifying(debeziumJsonConsumer)
                .build();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);
    }
}
