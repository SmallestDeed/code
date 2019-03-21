package com.sandu.designplan.model;


import com.sandu.common.model.Mapper;

import java.io.Serializable;

/**   
 * @Title: DesignPlanLike.java 
 * @Package com.nork.设计方案.model
 * @Description:设计方案点赞库-设计方案点赞
 * @createAuthor pandajun 
 * @CreateDate 2015-11-25 14:36:56
 * @version V1.0   
 */
public class DesignPlanLikeInput  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;


	/**  方案id  **/
	private Integer designId;
	/**  点赞状态（0：未点赞，1：已点赞）  **/
	private Integer status;
	
	
	
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
    
	
}
