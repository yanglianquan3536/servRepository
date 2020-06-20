package com.quang.serv.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public static <T> T find(Collection<T> collection, Predicate<T> predicate){
        if(isEmpty(collection)) return null;
        for (T t : collection) {
            if(t != null && predicate.eval(t)) return t;
        }
        return null;
    }

    public static <I, O> List<O> collect(Collection<I> collections, Transform<I, O> transform) {
        if(isEmpty(collections)) return new ArrayList<>();
        List<O> output = new ArrayList<>();
        for (I i : collections) {
            if(i != null)
                output.add(transform.transform(i));
        }
        return output;
    }

    public interface Predicate<T>{
        boolean eval(T t);
    }

    public interface Transform<I, O>{
        O transform(I i);
    }
}
