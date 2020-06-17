package com.quang.serv.register.health;

import com.quang.serv.components.cache.impl.HealthReportCache;
import com.quang.serv.core.components.collector.Collector;
import com.quang.serv.core.health.HealthReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 集群健康度收集
 * 跟随集群启动，在Server启动后自动监听
 * @author Lianquan Yang
 */
@Slf4j
@Component
public class HealthReportCollector implements Collector<HealthReport> {

    @Resource
    private HealthReportCache healthReportCache;
    @Resource
    private HealthReportDisconnectManager healthReportDisconnectManager;

    /**
     * 健康度收集
     */
    public void collect(HealthReport report){
        // 设置当前服务器时间，用于之后做匹配
        report.setCurrentTime(new Date());
        // 只要能收集到上报数据，便将lostTime重置为0
        report.setLostTimes(0);
        healthReportDisconnectManager.remove(report);
        if(!healthReportCache.add(report)){
            log.error("cannot put health report into cache, please check!");
        }
    }
}
