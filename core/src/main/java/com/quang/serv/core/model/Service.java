package com.quang.serv.core.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 服务原型
 * @author lianquan Yang
 */
@Data
public class Service {

    private Long id;
    private String name;
    // 拥有者
    private String owner;

    private String protocol;
    private String host;
    private String port;
    private String context;
    private Integer version;
    // 服务环境
    private Integer env;

    private Date createTime;
    private Date updateTime;

    private ServiceConfig serviceConfig;
    private List<ServiceMethod> serviceMethods;
    private List<ServiceNode> serviceNodes;

}
