package com.sandu.api.springFestivalActivity.model;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ModelBuilder<T> {
    private T t;

    private Class<T> clz;

    private ModelBuilder(T t, Class<T> clz) {
        this.t = t;
        this.clz = clz;
    }

    public static <T> ModelBuilder<T> builder(Class<T> clz){
        try {
            Constructor<T> c = clz.getConstructor();
            return new ModelBuilder<>(c.newInstance(), clz);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public T build(){
        return t;
    }

    public ModelBuilder<T> put(String field, Object value){
        if (StringUtils.isBlank(field)) {
            throw new RuntimeException("field name is blank.");
        }
        String setMethodName = "set" + field.substring(0,1).toUpperCase() + field.substring(1, field.length());
        try {
            Method setMethod = clz.getMethod(setMethodName, value.getClass());
            setMethod.invoke(t, value);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
