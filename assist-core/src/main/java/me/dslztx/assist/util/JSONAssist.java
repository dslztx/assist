package me.dslztx.assist.util;

import java.lang.reflect.Array;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONAssist {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSONString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T parseObjectForbidMapOrCollectionOrArray(String s, Class<T> clz) throws JsonProcessingException {
        if (Map.class.isAssignableFrom(clz) || List.class.isAssignableFrom(clz) || clz.isArray()) {
            throw new UnsupportedOperationException(
                "can not support map/collection/array object deserialize, use " + "specific method instead");
        }

        return objectMapper.readValue(s, clz);
    }

    public static <K, V> Map<K, V> parseMap(String s, Class<K> kClass, Class<V> vClass) throws JsonProcessingException {

        ObjectMapper localMapper = new ObjectMapper();

        JavaType javaType = localMapper.getTypeFactory().constructMapType(HashMap.class, kClass, vClass);

        return localMapper.readValue(s, javaType);
    }

    public static <K> List<K> parseList(String s, Class<K> kClass) throws JsonProcessingException {

        ObjectMapper localMapper = new ObjectMapper();

        JavaType javaType = localMapper.getTypeFactory().constructCollectionType(ArrayList.class, kClass);

        return localMapper.readValue(s, javaType);
    }

    public static <K> K[] parseArray(String s, Class<K> kClass) throws JsonProcessingException {

        List<K> tt = parseList(s, kClass);

        K[] array = (K[])Array.newInstance(kClass, tt.size());

        return (K[])tt.toArray(array);

    }

    public static void main(String[] args) {
        System.out.println(Map.class.isAssignableFrom(HashMap.class));
        System.out.println(Collection.class.isAssignableFrom(List.class));
        System.out.println(Collection.class.isAssignableFrom(ArrayList.class));
        System.out.println(Collection.class.isAssignableFrom(Set.class));
        System.out.println(Collection.class.isAssignableFrom(HashSet.class));
    }

}
