package com.sandu.api.servicepurchase.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:service_action_details_log表的实体类
 *
 * @author: Sandu
 * @创建时间: 2019-01-05
 */
public class ServiceActionDetailsLog implements Serializable {
	/**
	 *
	 */
	private Integer id;

	/**
	 * 操作类型:1:套餐信息操作,2:套餐购买续费操作
	 */
	private Integer actionType;

	/**
	 * 购买续费操作前,Services_Account_Rel ID:account_id 购买套餐为0
	 */
	private Integer preAccountId;

	/**
	 * 购买续费操作后,产生的Services_Account_Rel ID:account_id
	 */
	private Integer curAccountId;

	/**
	 *
	 */
	private String remark;

	/**
	 * 处理人
	 */
	private String creator;

	/**
	 *
	 */
	private Date gmtCreate;

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 操作类型:1:套餐信息操作,2:套餐购买续费操作
	 *
	 * @return action_type 操作类型:1:套餐信息操作,2:套餐购买续费操作
	 */
	public Integer getActionType() {
		return actionType;
	}

	/**
	 * 操作类型:1:套餐信息操作,2:套餐购买续费操作
	 *
	 * @param actionType 操作类型:1:套餐信息操作,2:套餐购买续费操作
	 */
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	/**
	 * 购买续费操作前,Services_Account_Rel ID:account_id 购买套餐为0
	 *
	 * @return pre_account_id 购买续费操作前,Services_Account_Rel ID:account_id 购买套餐为0
	 */
	public Integer getPreAccountId() {
		return preAccountId;
	}

	/**
	 * 购买续费操作前,Services_Account_Rel ID:account_id 购买套餐为0
	 *
	 * @param preAccountId 购买续费操作前,Services_Account_Rel ID:account_id 购买套餐为0
	 */
	public void setPreAccountId(Integer preAccountId) {
		this.preAccountId = preAccountId;
	}

	/**
	 * 购买续费操作后,产生的Services_Account_Rel ID:account_id
	 *
	 * @return cur_account_id 购买续费操作后,产生的Services_Account_Rel ID:account_id
	 */
	public Integer getCurAccountId() {
		return curAccountId;
	}

	/**
	 * 购买续费操作后,产生的Services_Account_Rel ID:account_id
	 *
	 * @param curAccountId 购买续费操作后,产生的Services_Account_Rel ID:account_id
	 */
	public void setCurAccountId(Integer curAccountId) {
		this.curAccountId = curAccountId;
	}

	/**
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * 处理人
	 *
	 * @return creator 处理人
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 处理人
	 *
	 * @param creator 处理人
	 */
	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	/**
	 * @return gmt_create
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * @param gmtCreate
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
}