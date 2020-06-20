package com.quang.serv.components.business.service.impl;

import com.quang.serv.components.business.service.IServiceBusiness;
import com.quang.serv.components.distribute.Distribute;
import com.quang.serv.components.mybatis.dao.service.IServiceConfigMapper;
import com.quang.serv.components.mybatis.dao.service.IServiceMapper;
import com.quang.serv.components.mybatis.dao.service.IServiceMethodMapper;
import com.quang.serv.components.mybatis.dao.service.IServiceNodeMapper;
import com.quang.serv.core.model.Service;
import com.quang.serv.core.model.ServiceConfig;
import com.quang.serv.core.model.ServiceMethod;
import com.quang.serv.core.model.ServiceNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lianquan Yang
 */
@Slf4j
public class ServiceBusiness implements IServiceBusiness {

    @Resource
    private IServiceMapper serviceMapper;
    @Resource
    private IServiceConfigMapper serviceConfigMapper;
    @Resource
    private IServiceMethodMapper serviceMethodMapper;
    @Resource
    private IServiceNodeMapper serviceNodeMapper;

    @Resource
    private Distribute<Service> serviceNacosDistribute;

    @Override
    public Service selectById(long serviceId){
        return serviceMapper.selectById(serviceId);
    }

    @Override
    public Service selectByName(String serviceName){
        return serviceMapper.selectByName(serviceName);
    }

    @Override
    public Service selectDetailById(long serviceId){
        Service service = serviceMapper.selectById(serviceId);
        if(service != null){
            ServiceConfig serviceConfig = serviceConfigMapper.selectByServiceId(serviceId);
            List<ServiceMethod> serviceMethods = serviceMethodMapper.selectByServiceId(serviceId);
            List<ServiceNode> serviceNodes = serviceNodeMapper.selectByServiceId(serviceId);
            service.setServiceConfig(serviceConfig);
            service.setServiceMethods(serviceMethods);
            service.setServiceNodes(serviceNodes);
            return service;
        }
        log.error(String.format("cannot find service by id [%d], please check", serviceId));
        return null;
    }

    @Override
    public List<Service> list() {
        return serviceMapper.list();
    }

    @Override
    public Service selectDetailByName(String serviceName){
        Service service = serviceMapper.selectByName(serviceName);
        if(service != null){
            return selectDetailById(service.getId());
        }
        log.error(String.format("cannot find service named [%s], please check", serviceName));
        return null;
    }

    /**
     *全新提交的Service必须将所有配置写全
     */
    @Override
    @Transactional
    public boolean addService(Service service) {
        // 校验
        Assert.notNull(service, "service must not be null");
        Assert.notNull(service.getServiceConfig(), "service config must not be null");
        Assert.notEmpty(service.getServiceMethods(), "service methods must not be empty");
        Assert.notEmpty(service.getServiceNodes(), "service nodes must not be empty");
        int serviceEffective = serviceMapper.insert(service);
        if(serviceEffective > 0){
            long serviceId = service.getId();
            service.getServiceConfig().setId(serviceId);
            serviceConfigMapper.insert(service.getServiceConfig());
            for (ServiceMethod serviceMethod : service.getServiceMethods()) {
                serviceMethod.setServiceId(serviceId);
                serviceMethodMapper.insert(serviceMethod);
            }
            for (ServiceNode serviceNode : service.getServiceNodes()) {
                serviceNode.setServiceId(serviceId);
                serviceNodeMapper.insert(serviceNode);
            }
        }
        // 下发配置
        serviceNacosDistribute.publish(service);
        return true;
    }

    /**
     * 移除Service需移除关联信息
     * @param serviceName 指定service名
     */
    @Override
    public boolean removeService(String serviceName) {
        Service service = serviceMapper.selectByName(serviceName);
        if(service != null){
            // db中移除该service
            return removeService(service.getId());
        }
        log.error(String.format("cannot find service named [%s], operations will be ignored", serviceName));
        return false;
    }

    @Transactional
    public boolean removeService(long serviceId){
        Service service = selectById(serviceId);
        if(service != null){
            int effective = serviceMapper.deleteById(serviceId);
            if(effective > 0){
                serviceConfigMapper.deleteByServiceId(serviceId);
                serviceMethodMapper.deleteByServiceId(serviceId);
                serviceNodeMapper.deleteByServiceId(serviceId);
                // 移除配置
                serviceNacosDistribute.remove(service);
                return true;
            }
            log.error(String.format("error occurred when remove service named [%s], please check", service.getName()));
            return false;
        }
        log.error(String.format("cannot find service by id [%d], operations will be ignored", serviceId));
        return false;
    }

    @Override
    public boolean addNode(ServiceNode serviceNode) {
        Assert.notNull(serviceNode.getServiceId(), "serviceId cannot be null");
        Service service = serviceMapper.selectById(serviceNode.getServiceId());
        if(service != null){
            // 开始添加节点
            serviceNodeMapper.insert(serviceNode);
            // 下发配置
            publish(serviceNode.getServiceId());
            return true;
        }
        log.error(String.format("cannot find service by id [%d], so serviceNode cannot be added, please check", serviceNode.getServiceId()));
        return false;
    }

    /*移除节点时根据服务名，主机名和端口移除*/
    @Override
    public boolean removeNode(String serviceName, String host) {
        Service service = serviceMapper.selectByName(serviceName);
        if(service != null){
            long serviceId = service.getId();
            // 根据host和port查询node
            ServiceNode serviceNode = serviceNodeMapper.selectByHost(serviceId, host);
            if(serviceNode == null){
                log.warn(String.format("node [service=%s, host=%s] may have been removed, please check", serviceName, host));
                return true;
            }
            serviceNodeMapper.deleteById(serviceNode.getId());
            // 准备就绪后同步配置
            publish(serviceName);
            return true;
        }
        log.error(String.format("cannot find service named [%s], please check", serviceName));
        return false;
    }

    @Override
    public boolean updateConfig(ServiceConfig serviceConfig) {
        return false;
    }

    @Override
    public boolean addServiceMethod(ServiceMethod serviceMethod) {
        return false;
    }

    @Override
    public boolean removeServiceMethod(ServiceMethod serviceMethod) {
        return false;
    }

    @Override
    public boolean addServiceMethod(List<ServiceMethod> serviceMethods) {
        return false;
    }

    @Override
    public boolean removeServiceMethod(List<ServiceMethod> serviceMethods) {
        return false;
    }

    @Override
    public void publish(String serviceName){
        Service service = selectDetailByName(serviceName);
        serviceNacosDistribute.publish(service);
    }

    @Override
    public void publish(long serviceId){
        Service service = selectDetailById(serviceId);
        serviceNacosDistribute.publish(service);
    }
}
