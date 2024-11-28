package com.cooba.service.impl;

import cn.hutool.json.JSONUtil;
import com.cooba.service.ConnectorManageService;
import com.cooba.service.EngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectorManageServiceImpl implements ConnectorManageService {
    private final Map<String, Properties> connectorMap = new ConcurrentHashMap<>();
    private final EngineService engineService;

    @Override
    public String setConnector(Properties properties) {
        engineService.createEngine(properties);
        connectorMap.put(properties.getProperty("name"), properties);
        return JSONUtil.toJsonStr(properties);
    }

    @Override
    public String getConnector(String name) {
        Properties properties = connectorMap.get(name);
        return JSONUtil.toJsonStr(properties);
    }

    @Override
    public Collection<String> getConnectors() {
        return connectorMap.keySet();
    }

    @Override
    public void deleteConnector(String name) {
        engineService.closeEngine(name);
    }
}
