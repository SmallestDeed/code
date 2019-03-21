package com.sandu.common.util;

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
}
