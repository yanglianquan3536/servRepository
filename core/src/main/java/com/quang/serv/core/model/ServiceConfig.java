package com.quang.serv.core.model;

import lombok.Data;

/**
 * 基于服务的配置
 * @author lianquan Yang
 */
@Data
public class ServiceConfig {
    private Long id;
    private Long serviceId;
    // 连接重置时间
    public Integer maxConnResetTimeout;
}
