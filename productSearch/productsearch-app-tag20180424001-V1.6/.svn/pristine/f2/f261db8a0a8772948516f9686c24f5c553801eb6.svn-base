package com.nork.common.collection;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;


public class Maps extends MapUtils
{
  public static <K, V> Map.Entry<K, V> first(Map<K, V> map)
  {
    if (map.isEmpty())
      return null;
    return (Map.Entry)map.entrySet().toArray()[0];
  }

  public static <V> V valueOfFirst(Map<?, V> map)
  {
    Map.Entry e = first(map);
    return (V) e.getValue();
  }

  public static <K, V> Map<K, V> newMap()
  {
    return new HashMap();
  }

  public static <K, V> Map<K, V> map(Object[] keyValues)
  {
    if ((Arrays.isNotEmpty(keyValues)) && (keyValues.length > 1)) {
      Class kClass = keyValues[0].getClass();
      Class vClass = keyValues[1].getClass();
      return map(kClass, vClass, keyValues);
    }
    return null;
  }

  public static <K, V> Map<K, V> map(Class<K> kClass, Class<V> vClass, Object[] keyValues)
  {
    Map m = newMap();
    int i = 1;
    Object preObj = null;
    for (Object o : keyValues) {
      if (i % 2 == 0) {
        Object k = kClass.cast(preObj);
        Object v = vClass.cast(o);
        m.put(k, v);
      }
      preObj = o;
      i++;
    }
    return m;
  }

  public static Map<Object, Object> mapByAarray(Object... keyValues)
  {
    Map<Object,Object> m = Maps.newMap();
    int i = 1;
    Object key = null;
    for (Object value : keyValues) {
      if (i % 2 == 0) {
        m.put(key, value);
      }
      key = value;
      i++;
    }
    return m;
  }
}