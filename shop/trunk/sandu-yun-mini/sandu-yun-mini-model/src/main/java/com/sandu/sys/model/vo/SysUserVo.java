package com.sandu.sys.model.vo;

import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 用户信息
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "用户信息", description = "用户信息")
public class SysUserVo extends BaseVo<SysUserVo> {
	private static final long serialVersionUID = -3511042166430005742L;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "手机号")
	private String mobile;
	@ApiModelProperty(value = "微信OpenId")
	private String openId;
	@ApiModelProperty(value = "昵称")
	private String nickName;
	@ApiModelProperty(value = "用户类别")
	private int userType;
	@ApiModelProperty(value = "用户Token")
	private String token;

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
