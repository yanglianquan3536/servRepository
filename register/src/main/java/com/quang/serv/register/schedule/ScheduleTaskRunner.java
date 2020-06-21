package com.quang.serv.register.schedule;

import com.quang.serv.common.utils.CollectionUtils;
import com.quang.serv.components.cache.HashCache;
import com.quang.serv.core.health.HealthReport;
import com.quang.serv.register.health.HealthReportDisconnectDetector;
import com.quang.serv.register.health.HealthReportDisconnectManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Lianquan Yang
 */
@Component
@Slf4j
public class ScheduleTaskRunner {

    @Resource
    private HashCache<HealthReport> healthReportCache;
    @Resource
    private HealthReportDisconnectManager healthReportDisconnectManager;

    @Scheduled(fixedRate = 3000)
    public void healthReportScan(){
        // 获取缓存中所有的健康度
        Map<String, HealthReport> list = healthReportCache.list();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Set<HealthReport>> task = pool.submit(new HealthReportDisconnectDetector(new ArrayList<>(list.values())));
        try {
            Set<HealthReport> healthReports = task.get();
            if(CollectionUtils.isNotEmpty(healthReports)){
                healthReportDisconnectManager.addAll(new ArrayList<>(healthReports));
            }
        } catch (Exception e) {
            log.error(String.format("schedule [%s]: error occurred.", "healthReportScan"), e);
        }
    }

}
