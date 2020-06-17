package com.quang.serv.register.health;

import com.quang.serv.common.utils.CollectionUtils;
import com.quang.serv.components.distribute.Distribute;
import com.quang.serv.core.health.HealthReport;
import com.quang.serv.core.model.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集群健康检查配置
 * 对服务的更新要加全局锁，根据服务名加锁
 * @author Lianquan Yang
 */
@Component
@Slf4j
public class HealthReportDisconnectManager {

    /*<服务名, 死亡节点列表>*/
    private Map<String, Set<HealthReport>> disconnectHealthReports = new ConcurrentHashMap<>();
    @Resource
    private Distribute<Service> serviceNacosDistribute;

    /*add到死亡名单里的服务IP需要下发配置（不要再调用该节点）*/
    public void add(HealthReport healthReport){
        // TODO 获取当前服务的所有配置,并从当前配置中更新该节点
        Service service = null;
        // TODO 检验此时是否所有节点都不存在了，如果全部不存在则告警
        serviceNacosDistribute.publish(service);

        disconnectHealthReports.computeIfAbsent(healthReport.getServiceName(), k -> new HashSet<>());
        disconnectHealthReports.get(healthReport.getServiceName()).add(healthReport);
    }

    /*add到死亡名单里的服务IP需要下发配置（不要再调用该节点）*/
    public void addAll(List<HealthReport> healthReports){
        if(CollectionUtils.isNotEmpty(healthReports)){
            for (HealthReport healthReport : healthReports) {
                add(healthReport);
            }
        }
    }

    /*从死亡名单里移除的服务IP需要下发配置（可重新调用该节点）*/
    public void remove(HealthReport healthReport){

        Set<HealthReport> healthReports = disconnectHealthReports.get(healthReport.getServiceName());
        if(CollectionUtils.isNotEmpty(healthReports)){
            // TODO:获取当前服务的所有配置，并从配置中更新该节点
            Service service = null;
            serviceNacosDistribute.publish(service);
            healthReports.remove(healthReport);
        }
    }

    /*从死亡名单里移除的服务IP需要下发配置（可重新调用该节点）*/
    public void remove(List<HealthReport> healthReports){
        if(CollectionUtils.isNotEmpty(healthReports)){
            for (HealthReport healthReport : healthReports) {
                remove(healthReport);
            }
        }
    }

    /*隔一段时间将死亡名单同步到DB以持久化存储，便于宕机后恢复*/
    public void flush(){
        for (Map.Entry<String, Set<HealthReport>> entry : disconnectHealthReports.entrySet()) {
            // TODO:将断联的服务写入DB
            log.info(String.format("flush %s into db, batch size:%d", entry.getKey(), entry.getValue().size()));

        }
    }
}
