package com.sandu.common.comparator;


import java.util.Comparator;


import com.sandu.render.model.AutoRenderTask;

public class AutoRenderTaskTimeAscComparator implements Comparator {

	
	public int compare(Object o1, Object o2) {
		AutoRenderTask autoRenderTask1 = (AutoRenderTask)o1;
		AutoRenderTask autoRenderTask2 = (AutoRenderTask)o2;
		int flag = autoRenderTask1.getGmtCreate().compareTo(autoRenderTask2.getGmtCreate());
		return flag;
	}	
}

