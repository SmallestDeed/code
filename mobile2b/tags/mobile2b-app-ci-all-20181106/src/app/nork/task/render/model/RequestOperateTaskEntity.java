package app.nork.task.render.model;

public class RequestOperateTaskEntity {
	//任务ID
	private String task_id;
	//操作指令 0：暂停任务 1：重提任务 2：删除任务
	private String operate_order;
	//重提类型 
	//	0：重提失败帧
	//	1：重提放弃帧
	//	2：重提完成帧
	//	3：重提开始帧
	//	4：重提等待帧
	//	当operate_order 为1时，该字段需填值。否则默认为0
	private String restart_type;
	
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getOperate_order() {
		return operate_order;
	}
	public void setOperate_order(String operate_order) {
		this.operate_order = operate_order;
	}
	public String getRestart_type() {
		return restart_type;
	}
	public void setRestart_type(String restart_type) {
		this.restart_type = restart_type;
	}
	
}
