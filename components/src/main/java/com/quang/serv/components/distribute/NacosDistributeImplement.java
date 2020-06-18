package com.quang.serv.components.distribute;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Lianquan Yang
 */
@Slf4j
public abstract class NacosDistributeImplement<T> implements Distribute<T> {

    @Value("${nacos.server}")
    private String nacosServerAddr;

    private ConfigService configService;
    {
        try {
            configService = NacosFactory.createConfigService(nacosServerAddr);
        } catch (NacosException e) {
            log.error("can not create configService", e);
            throw new IllegalStateException("configService not created, broken");
        }
    }

    @Override
    public void publish(T t){
        try {
            String group = getGroup();
            String dataId = getDataId(getKey(t));
            String config = getConfig(t);
            if(!configService.publishConfig(dataId, group, config)){
                throw new IllegalStateException("publish config failed, please check");
            }
        } catch (NacosException e) {
            log.error("cannot publish config, please check", e);
        }
    }

    @Override
    public void remove(String key){
        String group = getGroup();
        String dataId = getDataId(key);
        try {
            configService.removeConfig(dataId, group);
        } catch (NacosException e) {
            log.error("cannot remove config, please check", e);
        }
    }

    @Override
    public void addListener(String key, Listener listener) {
        String group = getGroup();
        String dataId = getDataId(key);
        try {
            configService.addListener(dataId, group, listener);
        } catch (NacosException e) {
            log.error("cannot add listener, please check", e);
        }
    }

    public abstract String getGroup();

    public abstract String getDataId(String key);

    public abstract String getConfig(T t);
}
