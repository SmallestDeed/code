package com.sandu.rendermachine.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Title: SysUserMessage.java 
 * @Package com.nork.system.model
 * @Description:系统-我的消息表
 * @createAuthor pandajun 
 * @CreateDate 2017-12-21 14:50:38
 * @version V1.0   
 */
@Data
public class SysUserMessage implements Serializable{

private static final long serialVersionUID = 1L;

    private Integer id;
	/**  消息标题  **/
	private String title;
	/**  消息内容  **/
	private String content;
	/**  消息类型（1:方案渲染,2:系统消息，3:活动推送）  **/
	private Integer messageType;
	/**  业务id  **/
	private Integer taskId;
	/**  用户id  **/
	private Integer userId;
	/**  是否已读（0:未读，1:已读）  **/
	private Integer isRead;
	/**  状态（0:失败，1:成功）  **/
	private Integer status;
	/**  失败原因  **/
	private String failingReason;
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
	/**  是否删除（0:否，1:是）  **/
	private Integer isDeleted;
	/**  备注  **/
	private String remark;
	/**  平台id  */
	private Integer platformId;
	/**  平台所属分类 */
	private String platformBussinessType;
	/**
     * 经销商(主账号)id
     */
    private Integer franchiserId;

}
