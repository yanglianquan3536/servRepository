package com.quang.serv.core.components;

import java.io.Serializable;

public interface CacheSerializable extends Serializable {

    /*用于唯一标识该对象*/
    String getKeyForHash();

    /*用于将对象分组，相同的对象在一组*/
    String getKeyForSet();
}
