package com.quang.serv.components.business.service;

import com.quang.serv.core.model.Service;
import com.quang.serv.core.model.ServiceConfig;
import com.quang.serv.core.model.ServiceMethod;
import com.quang.serv.core.model.ServiceNode;

import java.util.List;

public interface IServiceBusiness {

    Service selectById(long serviceId);

    Service selectDetailByName(String serviceName);

    Service selectByName(String serviceName);

    Service selectDetailById(long serviceId);

    List<Service> list();

    boolean addService(Service service);

    boolean removeService(String serviceName);

    boolean addNode(ServiceNode serviceNode);

    boolean removeNode(String serviceName, String host);

    boolean updateConfig(ServiceConfig serviceConfig);

    boolean addServiceMethod(ServiceMethod serviceMethod);

    boolean removeServiceMethod(ServiceMethod serviceMethod);

    boolean addServiceMethod(List<ServiceMethod> serviceMethods);

    boolean removeServiceMethod(List<ServiceMethod> serviceMethods);

    void publish(String serviceName);

    void publish(long serviceId);
}
