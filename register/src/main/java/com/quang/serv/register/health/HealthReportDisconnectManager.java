package com.quang.serv.register.health;

import com.quang.serv.common.utils.CollectionUtils;
import com.quang.serv.core.health.HealthReport;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Lianquan Yang
 */
@Component
public class HealthReportDisconnectManager {

    private Set<HealthReport> disconnectHealthReports = new HashSet<>();

    /*add到死亡名单里的服务IP需要下发配置（不要再调用该节点）*/
    public synchronized void add(HealthReport healthReport){
        this.disconnectHealthReports.add(healthReport);
        // TODO:下发配置禁用节点
    }

    /*add到死亡名单里的服务IP需要下发配置（不要再调用该节点）*/
    public synchronized void addAll(List<HealthReport> healthReports){
        if(CollectionUtils.isNotEmpty(healthReports)){
            for (HealthReport healthReport : healthReports) {
                add(healthReport);
            }
        }
    }

    /*从死亡名单里移除的服务IP需要下发配置（可重新调用该节点）*/
    public synchronized void remove(HealthReport healthReport){
        this.disconnectHealthReports.remove(healthReport);
    }

    /*从死亡名单里移除的服务IP需要下发配置（可重新调用该节点）*/
    public synchronized void remove(List<HealthReport> healthReports){
        if(CollectionUtils.isNotEmpty(healthReports)){
            for (HealthReport healthReport : healthReports) {
                remove(healthReport);
            }
        }
    }

    /*隔一段时间将死亡名单同步到DB以持久化存储，便于宕机后恢复*/
    public void flush(){
        for (HealthReport disconnectHealthReport : disconnectHealthReports) {
            // TODO:将断联的服务写入DB
        }
    }
}
