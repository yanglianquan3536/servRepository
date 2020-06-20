package com.quang.serv.components.cache;

import com.quang.serv.core.components.CacheSerializable;

import java.util.List;
import java.util.Map;

/**
 * 如果有获取列表的需求，使用其对应的扩展
 * @see HashCache
 * @see SetCache
 * @param <T>
 */

public interface Cache<T extends CacheSerializable> {

    boolean add(T t);

    boolean remove(T t);

    boolean remove(String key);

    boolean has(T t);

    boolean has(String key);
}
