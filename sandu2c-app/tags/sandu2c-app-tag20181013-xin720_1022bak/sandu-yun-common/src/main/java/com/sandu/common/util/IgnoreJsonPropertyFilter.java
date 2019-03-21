package com.sandu.common.util;

import net.sf.json.util.PropertyFilter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/9.
 */
public class IgnoreJsonPropertyFilter implements PropertyFilter {

    private Map<Class<?>,Set<String>> includeMap = new HashMap<Class<?>,Set<String>>();

    public IgnoreJsonPropertyFilter(){}

    public IgnoreJsonPropertyFilter(Map<Class<?>,Set<String>> includeMap){
        this.includeMap = includeMap;
    }

    @Override
    public boolean apply(Object o, String s, Object o1) {
        try {
            char[] chars = s.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            s = new String(chars);
            Field declaredField = o.getClass().getDeclaredField(s);
        } catch (NoSuchFieldException e) {
            return true;
        }
        return false;
    }
}
