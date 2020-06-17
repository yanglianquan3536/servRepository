package com.quang.serv.components.cache;

import com.quang.serv.core.components.CacheSerializable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 缓存的默认实现（Hash）方式
 * @author Lianquan Yang
 */
public abstract class CacheImplement<T extends CacheSerializable> implements Cache<T> {

    @Resource
    protected RedisTemplate<String, T> redisTemplate;
    private String group;
    // 默认过期时间
    private long expireSeconds;

    private static final long DEFAULT_EXPIRE_SECONDS = 10 * 60;

    public CacheImplement(String group){
        this(group, DEFAULT_EXPIRE_SECONDS);
    }

    public CacheImplement(String group, long expireSeconds){
        this.group = group;
        this.expireSeconds = expireSeconds;
    }

    @Override
    public boolean add(T t) {
        HashOperations<String, String, T> hKeyOperation = redisTemplate.opsForHash();
        hKeyOperation.putIfAbsent(group, t.getKey(), t);
        return true;
    }

    @Override
    public boolean remove(T t) {
        return remove(t.getKey());
    }

    @Override
    public boolean remove(String key) {
        HashOperations<String, String, T> hKeyOperation = redisTemplate.opsForHash();
        Long effectiveNum = hKeyOperation.delete(group, key);
        return effectiveNum > 0;
    }

    @Override
    public boolean has(T t) {
        return has(t.getKey());
    }

    @Override
    public boolean has(String key) {
        HashOperations<String, String, T> hKeyOperation = redisTemplate.opsForHash();
        return hKeyOperation.hasKey(group, key);
    }

    @Override
    public Map<String, T> list(){
        HashOperations<String, String, T> hKeyOperation = redisTemplate.opsForHash();
        return hKeyOperation.entries(group);
    }

    public T get(String key){
        HashOperations<String, String, T> hKeyOperation = redisTemplate.opsForHash();
        return hKeyOperation.get(group, key);
    }
}
