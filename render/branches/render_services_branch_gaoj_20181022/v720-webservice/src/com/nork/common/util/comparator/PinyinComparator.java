package com.nork.common.util.comparator;

import java.util.Comparator;

import com.nork.system.model.BaseArea;



/**
 * 
 * @author zhangjin
 *
 */
public class PinyinComparator implements Comparator<BaseArea> {

	public int compare(BaseArea o1, BaseArea o2) {
		
		String s1 = o1.getPinyin().toUpperCase();
		String s2 = o2.getPinyin().toUpperCase();
		
		if (s1.equals("@")
				|| s2.equals("#")) {
			return 1;
		} else if (s1.equals("#")
				|| s2.equals("@")) {
			return -1;
		} else {
			 int length = Math.min(s1.length(), s2.length());
		        
		        for(int i=0;i<length;i++){
		        	if(s1.charAt(i) > s2.charAt(i)){
		        		return 1;
		        	}else if(s1.charAt(i) < s2.charAt(i)){
		        		return -1;
		        	}else{
		        		continue;
		        	}
		        }
		        if(length == s1.length()){
		        	return -1;
		        }else{
		        	return 1;
		        }
			
		}
	}

}
