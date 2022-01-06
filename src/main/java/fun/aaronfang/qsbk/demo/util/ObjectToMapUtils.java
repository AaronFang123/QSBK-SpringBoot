package fun.aaronfang.qsbk.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * java 对象转换为Map key-value形式
 **/
public class ObjectToMapUtils {

    public static Map<String, Object> toMap(Object bean)  {
        ObjectMapper mapper = new ObjectMapper();
        Map m = null;
        try {
            m = mapper.readValue(mapper.writeValueAsString(bean), Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return m;
    }
    /**
     * 将对象装换为map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {

                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     */
    public static <T> void mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map;
            T bean;
            for (T t : objList) {
                bean = t;
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps,Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map;
            T bean;
            for (Map<String, Object> stringObjectMap : maps) {
                map = stringObjectMap;
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }
}
