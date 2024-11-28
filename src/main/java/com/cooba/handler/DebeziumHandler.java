package com.cooba.handler;

import io.debezium.engine.RecordChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class DebeziumHandler implements Consumer<RecordChangeEvent<SourceRecord>> {


    @Override
    public void accept(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
        log.info("Key = {}, Value = {}", sourceRecord.key(), sourceRecord.value());
        Object sourceRecordChangeValue = sourceRecord.value();
        log.info("SourceRecordChangeValue = '{}'", sourceRecordRecordChangeEvent);

    }
}
