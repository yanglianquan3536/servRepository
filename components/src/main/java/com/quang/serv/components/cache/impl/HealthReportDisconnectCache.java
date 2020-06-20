package com.quang.serv.components.cache.impl;

import com.quang.serv.components.cache.impl.base.SetCacheImplement;
import com.quang.serv.core.health.HealthReport;

/**
 * 用于维护死亡节点的缓存
 * @author Lianquan Yang
 */
public class HealthReportDisconnectCache extends SetCacheImplement<HealthReport> {

    public static final String GROUP_HEALTH_REPORT_DISCONNECT = "PRE_HEALTH_REPORT_DISCONNECT";

    public HealthReportDisconnectCache(String group) {
        super(GROUP_HEALTH_REPORT_DISCONNECT);
    }

}
