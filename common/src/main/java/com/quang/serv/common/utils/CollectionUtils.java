package com.quang.serv.common.utils;

import java.util.Collection;

/**
 * @author Lianquan Yang
 */
public class CollectionUtils {

    public static boolean isNotEmpty(Collection<?> collection){
        return collection != null && !collection.isEmpty();
    }

    public static boolean isEmpty(Collection<?> collection){
        return !isNotEmpty(collection);
    }

}
