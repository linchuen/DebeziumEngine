package com.cooba.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Demo {

    private Integer id;

    private String name;

    private Boolean isEnable;

    private BigDecimal money;

    private LocalDate localDate;

    private Date date;
}
