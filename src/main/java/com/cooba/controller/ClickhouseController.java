package com.cooba.controller;

import com.cooba.entity.Demo;
import com.cooba.service.impl.ClickhouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instancio.Instancio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/demo")
public class ClickhouseController {
    private final ClickhouseService clickhouseService;

    @PostMapping
    public ResponseEntity<?> hello() {
        Demo demo = Instancio.create(Demo.class);
        log.info("demo:{}", demo);

        clickhouseService.insertDemoData(demo);
        return ResponseEntity.ok("");
    }

}
