package com.sandu.api.commom;

import java.util.List;


public class ExecutionResultHolderImpl implements ExecutionResultHolder {

	private List<ExecutionResult> executionResults;

	@Override
	public List<ExecutionResult> getExecutionResults() {
		return executionResults;
	}

	@Override
	public void setExecutionResults(List<ExecutionResult> executionResults) {
		this.executionResults = executionResults;
	}
}
