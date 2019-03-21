package app.nork.task.render.model;

import net.sf.json.JSONArray;

public class CreateTaskJson {
	
	private JSONArray data;
	
	private Integer total_size;

	public JSONArray getData() {
		return data;
	}

	public void setData(JSONArray data) {
		this.data = data;
	}

	public Integer getTotal_size() {
		return total_size;
	}

	public void setTotal_size(Integer total_size) {
		this.total_size = total_size;
	}
	

}
