package com.cooba.service;

import java.util.Properties;

public interface EngineService {

    void createEngine(Properties properties);

    void closeEngine(String name);
}
