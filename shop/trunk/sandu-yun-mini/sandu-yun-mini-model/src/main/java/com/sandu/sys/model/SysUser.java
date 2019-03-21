package com.sandu.sys.model;

import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 用户信息
 * @author Administrator
 *
 */
@ApiModel(value = "用户信息", description = "用户信息")
public class SysUser extends BaseVo<SysUser>{
	private static final long serialVersionUID = 6016966863134886649L;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "手机号")
	private String mobile;
	@ApiModelProperty(value = "微信公众号用户ID")
	private String openId;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
