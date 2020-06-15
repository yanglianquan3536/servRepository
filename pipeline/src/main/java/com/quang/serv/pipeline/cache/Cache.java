package com.quang.serv.pipeline.cache;

import com.quang.serv.core.components.cache.CacheSerializable;

public interface Cache<T extends CacheSerializable> {

    boolean add(T t);

    boolean remove(T t);

    boolean remove(String key);

    boolean has(T t);

    boolean has(String key);

}
