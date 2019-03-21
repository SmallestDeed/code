
package com.sandu.api.commom;

import net.minidev.json.annotate.JsonIgnore;

import java.util.Collections;
import java.util.List;


public class JsonModel implements ExecutionResultHolder {
	
	protected boolean success = false; // indicate whether it is a successful return

	protected JsonModelError error;

	@JsonIgnore
	protected List<ExecutionResult> executionResults; // used to hold data for audit or exception handler

	/* for test case! */
	public JsonModel() {
	}

	protected JsonModel(boolean success) {
		this.success = success;
	}

	protected JsonModel(String i18nMessage) {
		this.success = false;
		this.error = new JsonModelError(i18nMessage);
	}

	public boolean isSuccess() {
		return success;
	}

	public JsonModelError getError() {
		return error;
	}

	@Override
	public List<ExecutionResult> getExecutionResults() {
		return executionResults;
	}

	@Override
	public void setExecutionResults(List<ExecutionResult> executionResults) {
		this.executionResults = executionResults;
	}

	/*****************************************************************************/
	/* Helper methods to construct JsonModel                                     */
	/*****************************************************************************/
	public static JsonModel createSuccessfulEmptyJsonModel() {
		return new JsonModel(true);
	}

	public static JsonModel createFailedJsonModel(String i18nMessage) {
		return new JsonModel(i18nMessage);
	}

	public static JsonModel createWithSingleExecutionResult(ExecutionResult executionResult) {
		JsonModel model = new JsonModel(true);
		model.setExecutionResults(Collections.singletonList(executionResult));
		return model;
	}
	
	public static JsonModel createWithExecutionResults(List<ExecutionResult> executionResults) {
		JsonModel model = new JsonModel(true);
		model.setExecutionResults(executionResults);
		return model;
	}

}
