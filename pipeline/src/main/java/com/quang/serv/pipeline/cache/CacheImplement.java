package com.quang.serv.pipeline.cache;

import com.quang.serv.core.components.cache.CacheSerializable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Lianquan Yang
 */
public abstract class CacheImplement<T extends CacheSerializable> implements Cache<T> {

    @Resource
    protected RedisTemplate<String, T> redisTemplate;
    private String prefix;
    // 默认过期时间
    private long expireSeconds = 10 * 60;

    public CacheImplement(String prefix){
        this.prefix = prefix;
    }

    public void setExpireSeconds(long expireSeconds){
        this.expireSeconds = expireSeconds;
    }

    @Override
    public boolean add(T t) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(getKey(t), t, expireSeconds, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public boolean remove(T t) {
        return remove(t.getKey());
    }

    @Override
    public boolean remove(String key) {
        Boolean delResult = redisTemplate.delete(getKey(key));
        return delResult != null && delResult;
    }

    @Override
    public boolean has(T t) {
        return has(t.getKey());
    }

    @Override
    public boolean has(String key) {
        Boolean hasResult = redisTemplate.hasKey(getKey(key));
        return hasResult != null && hasResult;
    }

    private String getKey(T t){
        return getKey(t.getKey());
    }

    private String getKey(String key){
        return prefix + "_" + key;
    }
}
