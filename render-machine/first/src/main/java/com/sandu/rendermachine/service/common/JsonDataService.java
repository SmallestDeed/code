package com.sandu.rendermachine.service.common;

import java.util.List;
import java.util.Map;


public interface JsonDataService<T> {
	public String getBeanToJsonData(Object bean);
	
	public String getBeanToJsonData(Object bean, String[] excludeProperties);

	public String getListToJsonData(List<?> dataList);

	public String getMapToJsonData(Map<String, ?> map);

	public T getJsonToBean(String jsonData, Class<?> clzz);

	public List<T> getJsonToBeanList(String jsonData, Class<?> clzz);
}