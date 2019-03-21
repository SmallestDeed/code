
package com.sandu.api.commom;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonDataModel<T> extends JsonModel {

	private T data; // the data returned 
	
	private Map extra; // // the extra data returned 
	
	public JsonDataModel(String i18nMessage) {
		super(i18nMessage);
	}
	
	private JsonDataModel(T data) {
		super(true);
		this.data = data;
	}
	
	
	public T getData() {
		return data;
	}
	
	public Map getExtra() {
        return extra;
    }

    public void setExtra(Map extra) {
        this.extra = extra;
    }
    
    public void putExtra(String key, Object value) {
        if (extra == null) {
            extra = new HashMap();
        }
        extra.put(key, value);
    }

	/*****************************************************************************/
	/* Helper methods to construct JsonModel                                     */
	/*****************************************************************************/
	
	public static <T> JsonDataModel<T> createByData(T data) {
		return new JsonDataModel<T>(data);
	}
	
	public static <T> JsonDataModel<T> createByDataWithExecutionResult(T data) {
		JsonDataModel<T> model = new JsonDataModel<T>(data);
		model.setExecutionResults(Collections.singletonList(ExecutionResult.createSuccessfulExecutionResult(data)));
		return model;
	}
	
	public static <T> JsonDataModel<T> createByDataWithExecutionResult(T data, List<ExecutionResult> er) {
		JsonDataModel<T> model = new JsonDataModel<T>(data);
		model.setExecutionResults(er);
		return model;
	}
	
	public static JsonDataModel<?> createFailedJsonModel(String i18nMessage) {
		return new JsonDataModel<Object>(i18nMessage);
	}
	
	
}
