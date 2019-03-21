package com.nork.sandu.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
    public static List<Integer> getList(String ids){
    	List<Integer> lst=new ArrayList<Integer>();
    	if(ids!=null && ids.length()>0){
    		String[] arrayIds=ids.split(",");
    		for(String id : arrayIds){
    			lst.add(Integer.parseInt(id));
    		}
    	}
    	return lst;
    }
}
