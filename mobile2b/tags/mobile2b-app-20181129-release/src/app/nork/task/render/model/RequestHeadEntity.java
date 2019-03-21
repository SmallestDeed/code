package app.nork.task.render.model;

public class RequestHeadEntity {

	private String access_key;
	private String account;
	private String msg_locale;
	private String action;
	
	public String getAccess_key() {
		return access_key;
	}
	public void setAccess_key(String access_key) {
		this.access_key = access_key;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMsg_locale() {
		return msg_locale;
	}
	public void setMsg_locale(String msg_locale) {
		this.msg_locale = msg_locale;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
