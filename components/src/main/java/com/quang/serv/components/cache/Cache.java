package com.quang.serv.components.cache;

import com.quang.serv.core.components.CacheSerializable;

import java.util.Map;

public interface Cache<T extends CacheSerializable> {

    boolean add(T t);

    boolean remove(T t);

    boolean remove(String key);

    boolean has(T t);

    boolean has(String key);

    Map<String, T> list();
}
