package com.quang.serv.components.cache;

import com.quang.serv.core.components.CacheSerializable;

import java.util.Map;

/**
 * @author Lianquan Yang
 */
public interface HashCache<T extends CacheSerializable> extends Cache<T>{

    Map<String, T> list();

}
