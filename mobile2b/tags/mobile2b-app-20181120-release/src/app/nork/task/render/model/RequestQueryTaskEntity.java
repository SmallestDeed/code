package app.nork.task.render.model;

public class RequestQueryTaskEntity {
	
	//任务ID
	private String task_id;
	//项目名称  精确查询
	private String project_name;
	//是否包含子账号0：不包含（默认值）1：包含
	private String is_sub_included;
	//是否包含任务详情 0：不包含（默认值）
	private String is_jobs_included;
	//任务状态
	private String task_status;
	//任务类型
	private String task_type;
	//渲染软件
	private String cg_soft_name;
	//场景名
	private String scene_name;
	
	private String login_name;
	
	
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getIs_sub_included() {
		return is_sub_included;
	}
	public void setIs_sub_included(String is_sub_included) {
		this.is_sub_included = is_sub_included;
	}
	public String getIs_jobs_included() {
		return is_jobs_included;
	}
	public void setIs_jobs_included(String is_jobs_included) {
		this.is_jobs_included = is_jobs_included;
	}
	public String getTask_status() {
		return task_status;
	}
	public void setTask_status(String task_status) {
		this.task_status = task_status;
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
	public String getScene_name() {
		return scene_name;
	}
	public void setScene_name(String scene_name) {
		this.scene_name = scene_name;
	} 
	

}
