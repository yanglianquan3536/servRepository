package com.quang.serv.pipeline;

import com.quang.serv.core.health.HealthReport;
import com.quang.serv.pipeline.cache.Cache;
import com.quang.serv.pipeline.cache.impl.HealthReportCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Lianquan Yang
 */
@SpringBootApplication
public class PipelineBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PipelineBootstrap.class, args);
        Cache<HealthReport> healthCache = context.getBean(HealthReportCache.class);
        healthCache.add(new HealthReport());
    }
}
