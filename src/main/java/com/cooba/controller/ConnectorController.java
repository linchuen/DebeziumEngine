package com.cooba.controller;

import com.cooba.service.ConnectorManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Properties;

@RestController
@RequiredArgsConstructor
@RequestMapping("/connectors")
public class ConnectorController {
    private final ConnectorManageService connectorManageService;

    @GetMapping
    public Collection<String> getConnectors() {
        return connectorManageService.getConnectors();
    }

    @PostMapping
    public String setConnector(@RequestBody Properties properties) {
        return connectorManageService.setConnector(properties);
    }

    @GetMapping("/{name}")
    public String getConnector(@PathVariable String name) {
        return connectorManageService.getConnector(name);
    }

    @DeleteMapping("/{name}")
    public String deleteConnector(@PathVariable String name) {
        connectorManageService.deleteConnector(name);
        return "1";
    }
}
