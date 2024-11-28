package com.cooba.service;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Properties;

public interface ConnectorManageService {

    String setConnector(Properties properties);

    String getConnector(String name);

    Collection<String> getConnectors();

    void deleteConnector(String name);
}
