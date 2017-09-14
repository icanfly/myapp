package com.lpnote.demo.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpnote.demo.entity.FootTruck;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luopeng on 2017/9/14.
 */
public class JacksonFootTruckMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, FootTruck.class);

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static List<FootTruck> deserialize(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, javaType);
    }

    public static List<FootTruck> deserialize(InputStream is) throws IOException {
        return objectMapper.readValue(is,javaType);
    }

    public static String serialize(List<FootTruck> footTrucks) throws JsonProcessingException {
        return objectMapper.writeValueAsString(footTrucks);
    }

}
