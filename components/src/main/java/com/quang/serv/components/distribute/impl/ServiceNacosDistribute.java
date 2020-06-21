package com.quang.serv.components.distribute.impl;

import com.alibaba.fastjson.JSON;
import com.quang.serv.components.distribute.NacosDistributeImplement;
import com.quang.serv.core.model.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 服务分发
 * @author Lianquan Yang
 */
@Component
public class ServiceNacosDistribute extends NacosDistributeImplement<Service> {

    @Value("${nacos.server.url}")
    private String nacosServerAddr;

    private static final String SERVICE_REGISTER_GROUP = "serv:manager";
    private static final String SERVICE_DATA_ID_PREFIX = "name:";

    @Override
    public String getNacosServerAddr() {
        return nacosServerAddr;
    }

    @Override
    public String getGroup() {
        return SERVICE_REGISTER_GROUP;
    }

    @Override
    public String getDataId(String key) {
        return SERVICE_DATA_ID_PREFIX + key;
    }

    @Override
    public String getConfig(Service service) {
        return JSON.toJSONString(service);
    }

    @Override
    public String getKey(Service service) {
        return service.getName();
    }
}
