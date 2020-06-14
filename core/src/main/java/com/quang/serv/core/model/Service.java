package com.quang.serv.core.model;

import lombok.Data;

import java.util.Date;

@Data
public class Service {

    private long id;
    private String name;
    private String owner;
    private Date createTime;
    private Date updateTime;

}
