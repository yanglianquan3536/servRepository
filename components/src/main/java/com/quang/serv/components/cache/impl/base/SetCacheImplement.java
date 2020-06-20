package com.quang.serv.components.cache.impl.base;

import com.quang.serv.common.utils.CollectionUtils;
import com.quang.serv.components.cache.SetCache;
import com.quang.serv.core.components.CacheSerializable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 缓存的实现（List）方式
 * @author Lianquan Yang
 */
public class SetCacheImplement<T extends CacheSerializable> implements SetCache<T> {

    @Resource
    protected RedisTemplate<String, T> redisTemplate;
    private String group;
    // 默认过期时间
    private long expireSeconds;

    private static final long DEFAULT_EXPIRE_SECONDS = 10 * 60;

    public SetCacheImplement(String group){
        this(group, DEFAULT_EXPIRE_SECONDS);
    }

    public SetCacheImplement(String group, long expireSeconds){
        this.group = group;
        this.expireSeconds = expireSeconds;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean add(T t) {
        Assert.notNull(t, "object for add must not be null");
        String cacheKey = cacheKey(t.getKeyForSet());
        SetOperations<String, T> setOperation = redisTemplate.opsForSet();
        Long effectiveNum = setOperation.add(cacheKey, t);
        return effectiveNum != null && effectiveNum > 0;
    }

    @Override
    public boolean remove(T t) {
        Assert.notNull(t, "object for remove must not be null");
        String cacheKey = cacheKey(t.getKeyForSet());
        SetOperations<String, T> setOperation = redisTemplate.opsForSet();
        Long effectiveNum = setOperation.remove(cacheKey, t);
        return effectiveNum != null && effectiveNum > 0;
    }

    @Override
    public boolean remove(String key) {
        throw new IllegalStateException("you must provide an element[T] to remove");
    }

    @Override
    public boolean has(T t) {
        Assert.notNull(t, "object must provide for predicate");
        String cacheKey = cacheKey(t.getKeyForSet());
        SetOperations<String, T> setOperation = redisTemplate.opsForSet();
        Boolean isMember = setOperation.isMember(cacheKey, t);
        return isMember != null && isMember;
    }

    @Override
    public boolean has(String key) {
        throw new IllegalStateException("you must provide an element[T] to predicate");
    }

    @Override
    public List<T> list(String key) {
        String cacheKey = cacheKey(key);
        SetOperations<String, T> setOperation = redisTemplate.opsForSet();
        Set<T> members = setOperation.members(cacheKey);
        if(CollectionUtils.isNotEmpty(members)) return new ArrayList<>(members);
        return new ArrayList<>();
    }

    private String cacheKey(String key){
        return group + "_" + key;
    }

}
