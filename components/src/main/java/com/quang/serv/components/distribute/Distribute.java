package com.quang.serv.components.distribute;

import com.alibaba.nacos.api.config.listener.Listener;
import com.quang.serv.core.model.Service;

public interface Distribute<T> {

    void publish(T t);

    void remove(String key);

    default void remove(T t){
        remove(getKey(t));
    }

    void addListener(String key, Listener listener);

    String getKey(T t);

}
