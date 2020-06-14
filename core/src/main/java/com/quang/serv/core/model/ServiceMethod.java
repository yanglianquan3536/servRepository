package com.quang.serv.core.model;

import lombok.Data;

/**
 * 所有注册的方法
 * @author Lianquan Yang
 */
@Data
public class ServiceMethod {
    private long id;
    private long serviceId;
    private String group;
    private String path;
}
