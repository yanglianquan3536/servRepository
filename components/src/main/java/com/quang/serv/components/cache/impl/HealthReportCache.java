package com.quang.serv.components.cache.impl;

import com.quang.serv.components.cache.CacheImplement;
import com.quang.serv.core.health.HealthReport;
import org.springframework.stereotype.Component;

/**
 * @author Lianquan Yang
 */
@Component
public class HealthReportCache extends CacheImplement<HealthReport> {

    public static final String GROUP_HEALTH_REPORT = "PRE_HEALTH_REPORT";

    public HealthReportCache() {
        super(GROUP_HEALTH_REPORT);
    }
}
