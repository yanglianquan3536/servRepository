package com.quang.serv.register.health;

import com.quang.serv.core.components.Collector;
import com.quang.serv.core.health.HealthReport;
import com.quang.serv.pipeline.health.server.HealthServerBootstrap;

/**
 * 集群健康度收集
 * @author Lianquan Yang
 */
public class ServiceHealthCollector implements Collector<HealthReport> {
    public void init(){
        // 启动健康上报服务器
        HealthServerBootstrap.start(this);
    }

    /**
     * 健康度收集
     */
    public void collect(HealthReport report){
        // 处理健康上报数据
        System.out.println(report);
    }
}
