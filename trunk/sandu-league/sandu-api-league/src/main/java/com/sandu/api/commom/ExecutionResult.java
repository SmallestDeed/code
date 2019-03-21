
package com.sandu.api.commom;

public class ExecutionResult {
	
	private Object resultObject;
	
	private Throwable throwable;
	
	private ExecutionResult(Throwable throwable, Object resultObject) {
		this.throwable = throwable;
		this.resultObject = resultObject;
	}
	
	public static ExecutionResult createFailedExecutionResult(Object resultObject, Throwable throwable) {
		return new ExecutionResult(throwable, resultObject);
	}
	
	public static ExecutionResult createSuccessfulExecutionResult(Object resultObject) {
		return new ExecutionResult(null, resultObject);
	}
	
	public Throwable getThrowable() {
		return throwable;
	}
	
	public boolean isSuccessful() {
		return this.throwable == null;
	}

	public Object getResultObject() {
		return resultObject;
	}
}
