package com.yummyspots.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SimpleCache {
    private static final Map<String, Object> cache = new ConcurrentHashMap<>();

    public static void cache(String key, Object value) {
        if (cache.size() > 1000) {
            cache.clear();
        }
        cache.put(key, value);
    }

    public static Object get(String key) {
        return cache.get(key);
    }

    public static String getString(String key) {
        return (String) get(key);
    }
}
