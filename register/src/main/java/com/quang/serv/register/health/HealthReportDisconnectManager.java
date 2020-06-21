package com.quang.serv.register.health;

import com.quang.serv.common.utils.CollectionUtils;
import com.quang.serv.components.business.service.IServiceBusiness;
import com.quang.serv.components.cache.SetCache;
import com.quang.serv.core.health.HealthReport;
import com.quang.serv.core.model.Service;
import com.quang.serv.core.model.ServiceNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 集群健康检查配置
 * 对服务的更新要加全局锁，根据服务名加锁
 * @author Lianquan Yang
 */
@Component
@Slf4j
public class HealthReportDisconnectManager {

    /*<服务名, 死亡节点列表>*/
    @Resource
    private IServiceBusiness serviceBusiness;
    @Resource
    private SetCache<HealthReport> healthReportDisconnectCache;

    /*add到死亡名单里的服务IP需要下发配置（不要再调用该节点）*/
    public void add(HealthReport healthReport){
        Service service = serviceBusiness.selectDetailByName(healthReport.getServiceName());
        if(service != null){
            List<ServiceNode> serviceNodes = service.getServiceNodes();
            if(CollectionUtils.isEmpty(serviceNodes)){
                log.error(String.format("service [%s] doesn't has any node, so it's meaningless to add node into blacklist", healthReport.getServiceName()));
                return;
            }
            ServiceNode currentNode = CollectionUtils.find(serviceNodes, serviceNode -> serviceNode.getHost().equals(healthReport.getIp()));
            if(currentNode == null){
                log.error(String.format("service [%s] node [%s] doesn't exist, so it's meaningless to add node into blacklist", healthReport.getServiceName(), healthReport.getIp()));
                return;
            }
            // 变更该节点
            boolean hasRemoved = serviceBusiness.removeNode(healthReport.getServiceName(), healthReport.getIp());
            if(hasRemoved){
                // DB中成功移除该节点后方能将该节点加入清单
                healthReportDisconnectCache.add(healthReport);
            }
            return;
        }
        log.error(String.format("service [%s] has been removed, but client still report itself, client [%s], please check", healthReport.getServiceName(), healthReport.getIp()));
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

        if(healthReportDisconnectCache.has(healthReport)){
            // 获取service详情
            Service service = serviceBusiness.selectByName(healthReport.getServiceName());
            if(service == null){
                log.error(String.format("service [%s] doesn't exist, operation 'add node' cannot finish, please check", healthReport.getServiceName()));
                return;
            }
            boolean hasAdded = serviceBusiness.addNode(new ServiceNode() {{
                setHost(healthReport.getIp());
                setPort(healthReport.getPort());
                setServiceId(service.getId());
            }});
            if(hasAdded){
                if(healthReportDisconnectCache.remove(healthReport)){
                    log.error(String.format("healthReport [%s] has been inserted into db, but failed to remove from cache", healthReport));
                }
            }
        }
        log.warn(String.format("service [%s] doesn't has any die nodes, ignored", healthReport.getServiceName()));
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
        List<Service> list = serviceBusiness.list();
        if(CollectionUtils.isNotEmpty(list)){
            for (Service service : list) {
                List<HealthReport> reports = healthReportDisconnectCache.list(service.getName());
                Service currentService = serviceBusiness.selectByName(service.getName());
                if(currentService == null) {
                    log.error(String.format("service [%s] doesn't exist, sync process will be stopped", service.getName()));
                    continue;
                }
                // 更新该服务
                for (HealthReport report : reports) {
                    boolean hasRemoved = serviceBusiness.removeNode(service.getName(), report.getIp());
                    if(!hasRemoved){
                        log.error(String.format("service [%s] sync error, node [%s] cannot be removed", service.getName(), report.getIp()));
                    }
                }
            }
        }
    }
}
