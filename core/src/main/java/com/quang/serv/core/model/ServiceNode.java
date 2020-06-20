package com.quang.serv.core.model;

import lombok.Data;

/**
 * 提供该服务的节点
 * 仅需提供host
 * protocol，port，context通过service获取
 * @author Lianquan Yang
 */
@Data
public class ServiceNode {
    private Long id;
    private Long serviceId;
    private String host;
    private int port;
}
