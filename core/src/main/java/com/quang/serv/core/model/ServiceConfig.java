package com.quang.serv.core.model;

import lombok.Data;

/**
 * 基于服务的配置
 * @author lianquan Yang
 */
@Data
public class ServiceConfig {
    private long id;
    private long serviceId;
    // 连接重置时间
    public int maxConnResetTimeout;
}
