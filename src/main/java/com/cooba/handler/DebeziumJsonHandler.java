package com.cooba.handler;

import io.debezium.engine.ChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class DebeziumJsonHandler implements Consumer<ChangeEvent<String, String>> {

    @Override
    public void accept(ChangeEvent<String, String> stringStringChangeEvent) {
        log.info("Key = {}, Value = {}", stringStringChangeEvent.key(), stringStringChangeEvent.value());
    }
}
