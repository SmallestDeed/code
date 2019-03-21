
package com.sandu.api.commom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonListModel<T> extends JsonModel {

	private JsonListData<T> data; // the data returned
	
	private Map extra; // the extra data returned 
	
	private JsonDataMeta metaData; // the meta data returned
	
	private JsonListModel(String i18nMessage) {
		super(i18nMessage);
	}


	private JsonListModel(JsonListData<T> data) {
		super(true);
		this.data = data;
	}

	private JsonListModel(JsonListData<T> data, JsonDataMeta metaData) {
		super(true);
		this.data = data;
		this.metaData = metaData;
	}
	
	public JsonListData<T> getData() {
		return data;
	}

	public JsonDataMeta getMetaData() {
		return metaData;
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


	public static <T> JsonListModel<T> createByList(List<T> list) {
		JsonListData<T> data = new JsonListData<T>(list);
		return new JsonListModel<T>(data);
	}

	public static <T> JsonListModel<T> createByListWithExecutionResults(List<T> list, Class<?> listDataClass) {
		JsonListModel<T> model = JsonListModel.createByList(list);
		List<ExecutionResult> er = new ArrayList<ExecutionResult>(list.size());
		for (T data: list) {
			er.add(ExecutionResult.createSuccessfulExecutionResult(data));
		}
		model.setExecutionResults(er);
		return model;
	}

	public static <T> JsonListModel<T> createByListWithExecutionResults(List<T> list,  List<ExecutionResult> er) {
		JsonListModel<T> model = JsonListModel.createByList(list);
		model.setExecutionResults(er);
		return model;
	}

	public static <T> JsonListModel<T> createByListData(JsonListData<T> data) {
		return new JsonListModel<T>(data);
	}
	public static <T> JsonListModel<T> createByListDataWithExecutionResults(JsonListData<T> data, Class<?> listDataClass, List<ExecutionResult> er) {
		JsonListModel<T> model = JsonListModel.createByListData(data);
		model.setExecutionResults(er);
		return model;
	}

	public static JsonListModel<Object> createFailedJsonModel(String i18nMessage) {
		return new JsonListModel<Object>(i18nMessage);
	}
}
