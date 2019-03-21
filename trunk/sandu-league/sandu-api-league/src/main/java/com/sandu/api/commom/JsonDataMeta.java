
package com.sandu.api.commom;

import java.util.ArrayList;
import java.util.List;

public class JsonDataMeta {
	
	public static class FieldMeta {
		private String name;

		public FieldMeta(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
	}
	
	private String idProperty = "key";
	private String root = "data.list";
	private String totalProperty = "data.totalCount";
	private String successProperty = "success";
	private String messageProperty = "message";
	
	private List<FieldMeta> fields = new ArrayList<FieldMeta>();

	
	public JsonDataMeta(List<FieldMeta> fields) {
		this.fields = fields;
	}

	public String getRoot() {
		return root;
	}
	
	public String getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}
	
	public String getTotalProperty() {
		return totalProperty;
	}

	public String getMessageProperty() {
		return messageProperty;
	}
	
	public String getSuccessProperty() {
		return successProperty;
	}

	public List<FieldMeta> getFields() {
		return fields;
	}

}
