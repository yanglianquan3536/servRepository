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

    private ConfigService CONFIG_SERVICE;

    public abstract String getNacosServerAddr();

    @Override
    public void publish(T t){
        try {
            String group = getGroup();
            String dataId = getDataId(getKey(t));
            String config = getConfig(t);
            if(!getConfigService().publishConfig(dataId, group, config)){
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
            getConfigService().removeConfig(dataId, group);
        } catch (NacosException e) {
            log.error("cannot remove config, please check", e);
        }
    }

    @Override
    public void addListener(String key, Listener listener) {
        String group = getGroup();
        String dataId = getDataId(key);
        try {
            getConfigService().addListener(dataId, group, listener);
        } catch (NacosException e) {
            log.error("cannot add listener, please check", e);
        }
    }

    public synchronized ConfigService getConfigService(){
        if(CONFIG_SERVICE == null){
            try {
                CONFIG_SERVICE = NacosFactory.createConfigService(getNacosServerAddr());
            } catch (NacosException e) {
                log.error("can not create configService", e);
                throw new IllegalStateException("configService not created, broken");
            }
        }
        return CONFIG_SERVICE;
    }

    public abstract String getGroup();

    public abstract String getDataId(String key);

    public abstract String getConfig(T t);
}
