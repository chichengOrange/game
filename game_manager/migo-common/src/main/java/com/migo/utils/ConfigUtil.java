//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.utils;

import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {
    private static final Map<String, String> sysParamCache = new HashMap();

    public ConfigUtil() {
    }

    public static String getSysParam(String paramKey, String defaultValue) {
        return sysParamCache.containsKey(paramKey) ? (String)sysParamCache.get(paramKey) : defaultValue;
    }

    public static void addSysParamCache(String paramKey, String paramValue) {
        sysParamCache.put(paramKey, paramValue);
    }
}
