package com.nork.common.util.collections;

import java.util.Collection;

import org.apache.commons.collections.ListUtils;

public abstract class Lists extends ListUtils
{

  public static boolean isEmpty(Collection<?> c)
  {
    return (c == null) || (c.size() == 0);
  }

  public static boolean isNotEmpty(Collection<?> c)
  {
    return !isEmpty(c);
  }

}