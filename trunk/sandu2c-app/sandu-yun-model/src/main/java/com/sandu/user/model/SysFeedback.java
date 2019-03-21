package com.sandu.user.model;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**   
 * @Title: SysFeedback.java 
 * @Package com.nork.customerservice.model
 * @Description:客服中心-问题反馈
 * @createAuthor pandajun 
 * @CreateDate 2016-04-29 15:34:27
 * @version V1.0   
 */
@Data
public class SysFeedback extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
	/**  提问人id  **/
	private Integer userId;
	/**  提问人用户名  **/
	private String username;
	/**  问题标题  **/
	private String title;
	/**  问题内容  **/
	private String content;
	/**  问题状态  **/
	private Integer status;
	/**  处理人id  **/
	private Integer chargePersonId;
	/**  处理时间  **/
	private Date handlingTime;
	/**  处理结果  **/
	private String handlingResult;
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
	/** 判断反馈信息是否为已读,0为未读,1为已读   by chenming**/
	private Integer numa1;
	/**  整数备用2  **/
	private Integer numa2;
	/**  备注  **/
	private String remark;

    
    /**获取对象的copy**/
    public SysFeedback copy(){
       SysFeedback obj =  new SysFeedback();
       obj.setUserId(this.userId);
       obj.setUsername(this.username);
       obj.setTitle(this.title);
       obj.setContent(this.content);
       obj.setStatus(this.status);
       obj.setChargePersonId(this.chargePersonId);
       obj.setHandlingTime(this.handlingTime);
       obj.setHandlingResult(this.handlingResult);
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setNuma1(this.numa1);
       obj.setNuma2(this.numa2);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("userId",this.userId);
       map.put("username",this.username);
       map.put("title",this.title);
       map.put("content",this.content);
       map.put("status",this.status);
       map.put("chargePersonId",this.chargePersonId);
       map.put("handlingTime",this.handlingTime);
       map.put("handlingResult",this.handlingResult);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("numa1",this.numa1);
       map.put("numa2",this.numa2);
       map.put("remark",this.remark);

       return map;
    }
}
