package com.sandu.gateway.pay.common.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	private static Gson gson = new GsonBuilder().create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }


    public static <T> T fromJson(String jsonStr, Class<T> objClass) {
        return gson.fromJson(jsonStr, objClass);
    }


    public static void main(String[] args) {
		  /* Gson gson = new Gson();
		   Type founderSetType = new TypeToken<Set<String>>(){}.getType();
		   String testJson = "[\"Marcus\",\"Christian\",\"Norman\"]";
		   HashSet<String> founderSet = gson.fromJson(testJson, founderSetType);
		   System.out.println(founderSet);
		   Set set = fromJson(testJson,Set.class);
		   System.out.println(set);*/
		
		 /*Gson gson = new Gson();
		 Map<String,List<String>> resultMap = new HashMap<String,List<String>>();
		 String aa ="{'cfg':[{'modelName':'d_userdesign,e_userlogs','domain':'http://localhost:8090/user'},{'modelName':'b_base,c_basedesign','domain':'http://localhost:8090/base'},{'modelName':'a_common,f_resource,g_other','domain':'http://localhost:8090/other'}]}";
		 Map map = gson.fromJson(aa,Map.class);
		 List<Map> list = (List)map.get("cfg");
		 for(Map mapEntity:list) {
			 String[] modelNameArray = mapEntity.get("modelName").toString().split(",");
			 List<String> modelNameList = Arrays.asList(modelNameArray);
			 String domain = mapEntity.get("domain").toString();
			 if(!resultMap.containsKey(domain)) {
				 resultMap.put(domain, modelNameList);
			 }else {
				 resultMap.get(domain).addAll(modelNameList);
			 }
			 
		 }
		 System.out.println(resultMap);*/
		/* String aa = "[{'serverKey':'onekey','serverUrl':'http://onekey.ci.sanduspace.cn/onekey-app/online/'},{'serverKey':'productSearch','serverUrl':'http://productsearch.ci.sanduspace.cn/productsearch-app/online/'},{'serverKey':'v720','serverUrl':'http://render.ci.sanduspace.cn/render-app/'},{'serverKey':'decoration','serverUrl':'http://decoration.ci.sanduspace.cn/decoration-app/online/'},{'serverKey':'pay','serverUrl':'http://zhifu.ci.sanduspace.cn/'},{'serverKey':'fullSearch','serverUrl':'http://fullsearch.ci.sanduspace.cn/fullsearch-app/'}]";
		 Gson gson = new Gson();
		 List<Map<String,String>> l = gson.fromJson(aa, List.class);
		 System.out.println(l);
		 String[] array = new String[3];
		 array[0]="aa";
		 array[1]="bb";
		 array[2]="cc";
		 System.out.println(gson.toJson(array));*/
        String obj = "";
        System.out.println(GsonUtil.toJson(obj));
    }
}
