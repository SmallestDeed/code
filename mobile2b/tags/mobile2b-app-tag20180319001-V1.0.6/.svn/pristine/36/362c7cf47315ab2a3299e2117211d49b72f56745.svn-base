package app.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nork.common.cache.CacheUtil;
import com.nork.sandu.model.dto.TUser;



public class TestRedis {
    public void testRedis(){  
    	 CacheUtil  cacheUtil=null;
        //简单字符串处理  
        cacheUtil.put("name", "test");  
        //System.out.println("String---name--"+cacheUtil.get("name"));  
          
        //map  
        Map<String,Object> map = new HashMap<String,Object>();  
        map.put("key", "value");  
        map.put("key1", "value1");  
        cacheUtil.put("map", map);  
        //第一种取值方式  
        Map map1 = cacheUtil.get("map",Map.class);  
        if(map1 != null){  
            //System.out.println("first map---"+map1.get("key"));  
        }  
        //第二种取值方式  
        Map map2 = new Gson().fromJson(cacheUtil.get("map"), new TypeToken<Map<String,Object>>() {}.getType());  
        if(map2 != null){  
            //System.out.println("second map---"+map2.get("key1"));  
        }  
          
          
        //JavaBean处理  
        TUser user = new TUser();  
        user.setUserName("test");  
        cacheUtil.put("user",user);  
        TUser user1 = cacheUtil.get("user",TUser.class);  
        //System.out.println("javaBean--name--"+user1.getUserName());  
          
        //List<JavaBean>处理  
        List<TUser> list = new ArrayList<TUser>();  
        list.add(user);  
        cacheUtil.put("list", list);  
        List<TUser> list1 = new Gson().fromJson(cacheUtil.get("list"), new TypeToken<List<TUser>>() {}.getType());  
        if(list1 != null){  
            //System.out.println("List<JavaBean>--"+list1.get(0).getUserName());  
        }  
          
          
        //list<String>  
        List<String> newlist = new ArrayList<String>();  
        newlist.add("str1");  
        newlist.add("sr2");  
        cacheUtil.put("newlist", newlist);  
        List<String> newlist1 =  new Gson().fromJson(cacheUtil.get("newlist"), new TypeToken<List<String>>(){}.getType());  
        //System.out.println("list<String>--"+newlist1);  
          
        //List<Map<String,Object>>  
        List<Map<String,Object>> nowlist = new ArrayList<Map<String,Object>>();  
        Map<String,Object> newmap = new HashMap<String,Object>();  
        newmap.put("key1", "value1");  
        newmap.put("key2", "value2");  
        nowlist.add(newmap);  
        cacheUtil.put("nowlist", nowlist);  
        List<Map<String,Object>> nowlist1 =  new Gson().fromJson(cacheUtil.get("nowlist"), new TypeToken<List<Map<String,Object>>>(){}.getType());  
        if(nowlist1 !=null ){  
            //System.out.println(nowlist1.get(0).get("key1"));  
        }  
        //System.out.println("List<Map<String,Object>>--"+nowlist1);  
          
        //List<Map<String,TUser>>  
        List<Map<String,TUser>> lastList = new ArrayList<Map<String,TUser>>();  
        Map<String,TUser> lastMap = new HashMap<String, TUser>();  
        lastMap.put("user", user);  
        lastList.add(lastMap);  
        cacheUtil.put("lastList", lastList);  
        List<Map<String,TUser>> lastList1 =  new Gson().fromJson(cacheUtil.get("lastList"), new TypeToken<List<Map<String,TUser>>>(){}.getType());  
        if(lastList1 != null){  
            //System.out.println("List<Map<String,TUser>>---"+lastList1.get(0).get("user").getUserName());  
        }  
        //System.out.println(lastList1);  
    }  
	
}
