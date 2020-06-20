package com.quang.serv.components.cache;

import com.quang.serv.core.components.CacheSerializable;

import java.util.List;

public interface SetCache<T extends CacheSerializable> extends Cache<T>{
    List<T> list(String key);
}
