package com.sandu.common.utils;

import java.text.DecimalFormat;

/***
 * 数字工具类
 * @author Administrator
 *
 */
public class DigitalUtils {
   
	/***
	 * 浮点型数字转换为百分比显示
	 * @param f
	 * @return
	 */
   public static String FloatToPercent(float f) {
       DecimalFormat df = new DecimalFormat("0.00%");  
       return df.format(f);
   }
   
   /***
    * 字符转换为整型
    * @param value
    * @return
    */
   public static int StringToInt(String value) {
	   int i=0;
	   try {
		   if(value!=null && value.length()>0) {
		      i=Integer.parseInt(value);
		   }
	   }
	   catch(Exception e) {
		   e.printStackTrace();
	   }
	   return i;
   }
}
