package com.quang.serv.components.cache.impl;

import com.quang.serv.components.cache.impl.base.HashCacheImplement;
import com.quang.serv.core.health.HealthReport;
import org.springframework.stereotype.Component;

/**
 * 健康上报缓存
 * 用于缓存所有服务器的上报信息
 * 定时任务读取缓存中的上报数据以确定死亡的服务节点
 * @author Lianquan Yang
 */
@Component
public class HealthReportCache extends HashCacheImplement<HealthReport> {

    public static final String GROUP_HEALTH_REPORT = "PRE_HEALTH_REPORT";

    public HealthReportCache() {
        super(GROUP_HEALTH_REPORT);
    }
}
