package com.cooba.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Order extends CdcEntity {
    private Integer id;
    private Integer orderId;
    private Boolean sync;
    private Date createTime;
    private String transNo;
    private Date updateTime;
}
