package com.quang.serv.components.mybatis.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonMapper<K, T> {
    int insert(T t);
    int deleteById(K k);
    int update(T t);
    T selectById(K k);
    List<T> list();
}
