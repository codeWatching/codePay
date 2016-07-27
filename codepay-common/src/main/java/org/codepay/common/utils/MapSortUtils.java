package org.codepay.common.utils;

import java.util.*;

/**
 * Created by wangliping on 15-8-17.
 */
public final class MapSortUtils {


    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */ 
    public static <T> Map<String, T> sortByKey(Map<String, T> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, T> sortMap = new TreeMap<String, T>(new Comparator<String>() {
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 使用 Map按value进行排序
     *
     * @param map
     * @return
     */
    public static <T> Map<String, T> sortByValue(Map<String, T> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        List<Map.Entry<String, T>> entryList = new ArrayList<Map.Entry<String, T>>(map.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, T>>() {
            public int compare(Map.Entry<String, T> me1, Map.Entry<String, T> me2) {
                return String.valueOf(me1.getValue()).compareTo(String.valueOf(me2.getValue()));
            }
        });
        Map<String, T> sortedMap = new LinkedHashMap<String, T>();
        for (Map.Entry<String, T> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
