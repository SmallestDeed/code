package com.sandu.sys.model.query;

import com.sandu.base.model.query.BaseQuery;

import io.swagger.annotations.ApiModel;

/***
 * 用户信息查询条件
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "用户信息查询条件", description = "用户信息查询条件")
public class SysUserQuery extends BaseQuery<SysUserQuery> {
	private String openId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
