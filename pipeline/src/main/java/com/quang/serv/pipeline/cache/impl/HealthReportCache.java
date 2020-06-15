package com.quang.serv.pipeline.cache.impl;

import com.quang.serv.core.health.HealthReport;
import com.quang.serv.pipeline.cache.CacheImplement;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Lianquan Yang
 */
@Component
public class HealthReportCache extends CacheImplement<HealthReport> {

    private static final String PREFIX_HEALTH_REPORT = "PRE_HEALTH_REPORT";

    public HealthReportCache() {
        super(PREFIX_HEALTH_REPORT);
    }
}
