package com.cooba.config;

import com.cooba.handler.DebeziumConsumer;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
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

    @PostConstruct
    public void execute() throws IOException {
        ClassPathResource resource = new ClassPathResource("mysql-source.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);

        DebeziumEngine<RecordChangeEvent<SourceRecord>> engine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(properties)
                .notifying(debeziumConsumer)
                .build();
        log.info("engine created");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);
    }
}
