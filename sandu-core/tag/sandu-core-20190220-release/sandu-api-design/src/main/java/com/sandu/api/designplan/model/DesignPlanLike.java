package com.sandu.api.designplan.model;


import com.sandu.api.base.common.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Title: DesignPlanLike.java 
 * @Package com.nork.设计方案.model
 * @Description:设计方案点赞库-设计方案点赞
 * @createAuthor pandajun 
 * @CreateDate 2015-11-25 14:36:56
 * @version V1.0   
 */
@Data
public class DesignPlanLike extends Mapper implements Serializable{
	private static final long serialVersionUID = 1L;

    private Integer id;
	/**  用户id  **/
	private Integer userId;
	/**  方案id  **/
	private Integer designId;
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
	/**  备注  **/
	private String remark;
	/**  点赞状态（0：未点赞，1：已点赞）  **/
	private Integer status;

	@Override
	public String toString() {
		return "DesignPlanLike{" +
				"id=" + id +
				", userId=" + userId +
				", designId=" + designId +
				", sysCode='" + sysCode + '\'' +
				", creator='" + creator + '\'' +
				", gmtCreate=" + gmtCreate +
				", modifier='" + modifier + '\'' +
				", gmtModified=" + gmtModified +
				", isDeleted=" + isDeleted +
				", remark='" + remark + '\'' +
				'}';
	}
}
