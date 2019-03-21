package com.sandu.common.utils;

import java.util.List;
import com.sandu.sys.model.vo.SysDictionaryVo;

/***
 * 数据字典工具类
 * @author Administrator
 *
 */
public class SysDictionaryUtils {
   
   private static SysDictionaryVo get(List<SysDictionaryVo> lstDictionary,long id) {
	   SysDictionaryVo find=null;
	   if(lstDictionary!=null && lstDictionary.size()>0) {
		   for(SysDictionaryVo vo : lstDictionary) {
			   if(vo.getId()==id) {
				   find=vo;
				   break;
			   }
		   }
	   }
	   return find;
   }
   
   public static String getNames(List<SysDictionaryVo> lstDictionary,String ids) {
	   StringBuilder sb=new StringBuilder();
	   String[] arrIds=ids.split(",");
	   SysDictionaryVo find=null;
	   for(String id:arrIds) {
		   find=get(lstDictionary,Long.parseLong(ids));
		   if(find!=null) {
			   sb.append(find.getName()+"、");
		   }
	   }
	   if(sb.length()>2) {
		   sb=sb.delete(sb.length()-2, sb.length()-1);
	   }
	   return sb.toString();
   }
}
