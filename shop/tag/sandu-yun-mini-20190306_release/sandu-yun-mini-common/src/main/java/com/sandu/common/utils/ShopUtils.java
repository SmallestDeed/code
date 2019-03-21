package com.sandu.common.utils;

public class ShopUtils {
	   /***
	    * 根据店铺业务类型ID获取类型名称
	    * @param businessType
	    * @return
	    */
	   public static String getBusinessTypeName(int businessType) {
		   String name="品牌馆";
		   if(businessType==2) {
			   name="建材家居";
		   }
		   if(businessType==3) {
			   name="设计师";
		   }
		   if(businessType==4) {
			   name="设计公司";
		   }
		   if(businessType==5) {
			   name="装修公司";
		   }
		   if(businessType==6) {
			   name="工长";
		   }
		   return name;
	   }
}
