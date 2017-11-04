package com.example.websocket.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * fastxml.jackson的Json工具类
 */
public class JsonUtil {
    private JsonUtil() {
    }

    /**
     * json串转bean
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T str2bean(String jsonStr, Class<T> clazz) throws IOException {
        return getMapper().readValue(jsonStr, clazz);
    }

    /**
     * bean转json串
     *
     * @param bean
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> String bean2str(T bean) throws IOException {
        ObjectMapper oMapper = getMapper();

        oMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException {
                paramJsonGenerator.writeString("");
            }
        });

        return oMapper.writeValueAsString(bean);
    }

    /**
     * 将json格式的字符串 放到map中
     *
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> parseToMap(String jsonStr) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        Map<String, Object> resultMap = new HashMap<>();
        jsonObj.forEach(resultMap::put);
        return resultMap;
    }

    /**
     * 创建ObjectMapper
     *
     * @return
     */
    public static ObjectMapper getMapper() {
        ObjectMapper oMapper = new ObjectMapper();
        oMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//确定是否遇到未知属性（不映射到属性的属性，并且没有“任何设置者”或处理程序可以处理它）
        oMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);//可以启用允许JSON空字符串值（“”）绑定到POJO的功能
        return oMapper;
    }


}
