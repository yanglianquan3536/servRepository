package com.quang.serv.common.utils;

/**
 * @author Lianquan Yang
 */
public class StringUtils {

    public static boolean isBlank(String str){
        return str == null || str.isEmpty();
    }

    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }
}
