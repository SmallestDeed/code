package com.nork.common.async;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class TestTask implements Callable<String> {

	private int id;
    private static int count =10;
    private final int time =count--;
    public TestTask(int id){
        this.id = id;
    }
    
	@Override
	public String call() throws Exception {
		TimeUnit.MILLISECONDS.sleep(1000);
        return "Result of TaskWithResult : "+ id+", Time= "+time;
	}
	
}
