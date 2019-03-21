package com.nork.system.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: SysUserLastLoginLog.java 
 * @Package com.nork.系统模块.model.small
 * @Description:system-用户最后登录时间
 * @createAuthor pandajun 
 * @CreateDate 2017-07-04 10:03:13
 * @version V1.0   
 */
public class SysUserLastLoginLogSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  与sys_user主键关联  **/
	private Integer userId;
	/**  最后登录时间  **/
	private Date lastLoginTime;
	/**  客户端IP  **/
	private String clientIp;
	/**  服务器IP  **/
	private String serverIp;
	/**  登录设备  **/
	private String loginDevice;
	/**  操作系统型号  **/
	private String systemModel;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  整数备用1  **/
	private Integer numa1;
	/**  整数备用2  **/
	private Integer numa2;
	/**  备注  **/
	private String remark;

	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}
	public  String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp){
		this.clientIp = clientIp;
	}
	public  String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp){
		this.serverIp = serverIp;
	}
	public  String getLoginDevice() {
		return loginDevice;
	}
	public void setLoginDevice(String loginDevice){
		this.loginDevice = loginDevice;
	}
	public  String getSystemModel() {
		return systemModel;
	}
	public void setSystemModel(String systemModel){
		this.systemModel = systemModel;
	}
	public  String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}
	public  String getCreator() {
		return creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public  Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	public  String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	public  Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}
	public  Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public  String getAtt1() {
		return att1;
	}
	public void setAtt1(String att1){
		this.att1 = att1;
	}
	public  String getAtt2() {
		return att2;
	}
	public void setAtt2(String att2){
		this.att2 = att2;
	}
	public  Integer getNuma1() {
		return numa1;
	}
	public void setNuma1(Integer numa1){
		this.numa1 = numa1;
	}
	public  Integer getNuma2() {
		return numa2;
	}
	public void setNuma2(Integer numa2){
		this.numa2 = numa2;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}


}
