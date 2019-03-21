package com.sandu.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * JSON Utils
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/25
 */

@Slf4j
public class JSON {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @param object
     * @return
     */
    public static String stringify(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }
    }

    /**
     * @param content
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parse(String content, Class<T> clazz) {
        if (check(content, clazz)) {
            return null;
        }

        try {
            return objectMapper.readValue(content, clazz);
        } catch (Exception e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }
    }

    /**
     * @param content
     * @param typeRef
     * @param <T>
     * @return
     */
    public static <T> T parse(String content, TypeReference typeRef) {
        if (check(content, typeRef)) {
            return null;
        }

        try {
            return objectMapper.readValue(content, typeRef);
        } catch (Exception e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }
    }

    /**
     * @param content
     * @param javaType
     * @param <T>
     * @return
     */
    public static <T> T parse(String content, JavaType javaType) {
        if (check(content, javaType)) {
            return null;
        }

        try {
            return objectMapper.readValue(content, javaType);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }
    }

    private static boolean check(String content, Object typeRef) {
        if (StringUtils.isEmpty(content)) {
            log.warn("Parameters content is cannot be empty.");
            return true;
        }

        if (typeRef == null) {
            log.error("Parameters typeRef is cannot be empty.");
            return true;
        }

        return false;
    }
}
