package app.nork.task.render.model;

public class TaskResult {
	
	private String task_id;
	
	private String submit_result;
	
	private String project_name;
	
	private String task_status;

	private String operate_result;
	
	private String task_type;
	
	private String cg_soft_name;
	
	private String input_scene_path;
	
	private String description;

	private String fee;

	private String submit_time;

	public String getSubmit_time() {
		return submit_time;
	}

	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getSubmit_result() {
		return submit_result;
	}

	public void setSubmit_result(String submit_result) {
		this.submit_result = submit_result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getTask_status() {
		return task_status;
	}

	public void setTask_status(String task_status) {
		this.task_status = task_status;
	}
	
	public String getOperate_result() {
		return operate_result;
	}

	public void setOperate_result(String operate_result) {
		this.operate_result = operate_result;
	}

	public String getTask_type() {
		return task_type;
	}

	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}

	public String getCg_soft_name() {
		return cg_soft_name;
	}

	public void setCg_soft_name(String cg_soft_name) {
		this.cg_soft_name = cg_soft_name;
	}

	public String getInput_scene_path() {
		return input_scene_path;
	}

	public void setInput_scene_path(String input_scene_path) {
		this.input_scene_path = input_scene_path;
	}

}
