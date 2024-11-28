package com.cooba.service;

import com.cooba.entity.Demo;
import com.cooba.mapper.DemoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClickhouseService {
    private final DemoMapper demoMapper;

    public void insertDemoData(Demo demo){
        demoMapper.insert(demo);
    }
}
