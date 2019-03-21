package com.sandu.common.comparator;


import java.util.Comparator;


import com.sandu.render.model.AutoRenderTask;

public class AutoRenderTaskTimeDescComparator implements Comparator {

	
	public int compare(Object o1, Object o2) {
		AutoRenderTask autoRenderTask1 = (AutoRenderTask)o1;
		AutoRenderTask autoRenderTask2 = (AutoRenderTask)o2;
		int flag = autoRenderTask2.getGmtCreate().compareTo(autoRenderTask1.getGmtCreate());
		return flag;
	}	
}

