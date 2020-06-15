package com.quang.serv.pipeline.config;

import com.quang.serv.core.components.cache.CacheSerializable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * 用于获取RedisTemplate，方便操作Redis
 * @author Lianquan Yang
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, CacheSerializable> redisTemplate(LettuceConnectionFactory connectionFactory){
        RedisTemplate<String, CacheSerializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }

}
