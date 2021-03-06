package com.sandu.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class NumberUtil {
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static int parseInt(String str){
		int i=0;
		if(isNumeric(str)){
			i=Integer.parseInt(str);
		}
		return i;
	}

	/**
	 * 求数字开头的字符串中数字的长度 比如1BC1，输出是1
	 *
	 * @param str
	 * @return
	 */
	public static int getLengthFromStr(String str) {
		int numlength = 0;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				numlength++;
			} else {
				break;
			}
		}
		return numlength;
	}

	public static void main(String[] args) {/*
											 * //////System.out.println(NumberUtil.
											 * getLengthFromStr("12BC1"));
											 */
		String s = "1BC22";
		//////System.out.println(s.endsWith("12"));
	}

	/**
	 * @Description: 找出大于下限floor的最小值的索引
	 * @Date: 17:32 2018/7/3
	 */
	public static <T extends Comparable<? super T>> Integer findMinIndex(List<T> list, T floor, Boolean contain)
	{
		if (list == null || list.size() == 0)
		{
			return -1;
		}
		T minT = list.get(0);
		for (T t : list)
		{
			if (floor != null)
			{
				if (contain ? t.compareTo(floor) >= 0 : t.compareTo(floor) > 0)
				{
					if ((contain ? minT.compareTo(floor) < 0 : minT.compareTo(floor) <= 0) || t.compareTo(minT) < 0)
					{
						minT = t;
					}
				}
			}else
			{
				if (t.compareTo(minT) < 0)
				{
					minT = t;
				}
			}
		}
		if (floor != null)
		{
			if (contain ? minT.compareTo(floor) < 0 : minT.compareTo(floor) <= 0)
			{
				return -1;
			}
		}
		return list.indexOf(minT);
	}

	/**
	 * @Description: 找出最小值的索引
	 * @Date: 17:33 2018/7/3
	 */
	public static <T extends Comparable<? super T>> Integer findMinIndex(List<T> list)
	{
		return findMinIndex(list,null,null);
	}

	/**
	 * @Description: 找出大于下限floor的最小值
	 * @Date: 17:38 2018/7/3
	 */
	public static <T extends Comparable<? super T>> T findMin(List<T> list, T floor, Boolean contain)
	{
		return findMinIndex(list,floor,contain) == -1?null:list.get(findMinIndex(list,floor,contain));
	}

	/**
	 * @Description: 找出最小值
	 * @Date: 17:38 2018/7/3
	 */
	public static <T extends Comparable<? super T>> T findMin(List<T> list)
	{
		return findMinIndex(list) == -1? null:list.get(findMinIndex(list));
	}

	/**
	 * @Description: 字符串转化为数字集合
	 * @Date: 15:32 2018/7/2
	 */
	public static <T extends Number> List<T> convertStrToNumList(String str, Class<T> cls)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		if (str == null || "".equals(str.trim()))
		{
			return new ArrayList<>();
		}else
        {
            String[] strs = str.split(",");
            List<T> list = new ArrayList<>();
            for (String s : strs)
            {
                Constructor<T> con = cls.getConstructor(s.getClass());
                T t = con.newInstance(s);
                list.add(t);
            }
            return list;
        }
	}
}
