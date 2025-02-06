package org.appserver.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class utils {

    public static <T> JSONArray removeEmptyFields(List<T> products) {
        JSONArray result = new JSONArray();
        for (Object product : products) {
           JSONObject map = new JSONObject();
            Field[] fields = product.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(product);
                    if (value != null && !(value instanceof String && ((String) value).isEmpty())) {
                        map.put(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            result.add(map);
        }
        return result;
    }

    public static <T> Map<String, Object> removeEmpty(T object) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value != null && !(value instanceof String && ((String) value).isEmpty())) {
                    map.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
