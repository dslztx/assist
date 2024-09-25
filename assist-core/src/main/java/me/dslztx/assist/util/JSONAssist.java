//package me.dslztx.assist.util;
//
//import java.lang.reflect.Array;
//import java.util.*;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class JSONAssist {
//
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    public static String toJSONString(Object obj) throws JsonProcessingException {
//        return objectMapper.writeValueAsString(obj);
//    }
//
//    public static <T> T parseObjectForbidMapOrCollectionOrArray(String jsonString, Class<T> clz)
//        throws JsonProcessingException {
//
//        if (StringAssist.isBlank(jsonString)) {
//            throw new RuntimeException("jsonString is blank");
//        }
//
//        if (Objects.isNull(clz)) {
//            throw new RuntimeException("object class is null");
//        }
//
//        if (Map.class.isAssignableFrom(clz) || List.class.isAssignableFrom(clz) || clz.isArray()) {
//            throw new UnsupportedOperationException(
//                "can not support map/collection/array object deserialize, use " + "specific method instead");
//        }
//
//        return objectMapper.readValue(jsonString, clz);
//    }
//
//    public static <K, V> Map<K, V> parseMap(String jsonString, Class<K> keyClz, Class<V> valueClz)
//        throws JsonProcessingException {
//
//        if (StringAssist.isBlank(jsonString)) {
//            throw new RuntimeException("jsonString is blank");
//        }
//
//        if (Objects.isNull(keyClz)) {
//            throw new RuntimeException("map key class is null");
//        }
//
//        if (Objects.isNull(valueClz)) {
//            throw new RuntimeException("map value class is null");
//        }
//
//        ObjectMapper localMapper = new ObjectMapper();
//
//        JavaType javaType = localMapper.getTypeFactory().constructMapType(HashMap.class, keyClz, valueClz);
//
//        return localMapper.readValue(jsonString, javaType);
//    }
//
//    public static <K> List<K> parseList(String jsonString, Class<K> elementClz) throws JsonProcessingException {
//
//        if (StringAssist.isBlank(jsonString)) {
//            throw new RuntimeException("jsonString is blank");
//        }
//
//        if (Objects.isNull(elementClz)) {
//            throw new RuntimeException("element class is null");
//        }
//
//        ObjectMapper localMapper = new ObjectMapper();
//
//        JavaType javaType = localMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClz);
//
//        return localMapper.readValue(jsonString, javaType);
//    }
//
//    public static <K> K[] parseArray(String jsonString, Class<K> elementClz) throws JsonProcessingException {
//        if (StringAssist.isBlank(jsonString)) {
//            throw new RuntimeException("jsonString is blank");
//        }
//
//        if (Objects.isNull(elementClz)) {
//            throw new RuntimeException("element class is null");
//        }
//
//        List<K> listResult = parseList(jsonString, elementClz);
//
//        K[] arrayResult = (K[])Array.newInstance(elementClz, listResult.size());
//
//        return (K[])listResult.toArray(arrayResult);
//    }
//
//}
