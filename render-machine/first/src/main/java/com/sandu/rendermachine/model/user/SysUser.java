package com.sandu.rendermachine.model.user;

import com.sandu.rendermachine.model.common.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.*;


/**   
 * @Title: SysUser.java 
 * @Package com.nork.system.model
 * @Description:系统-用户
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:30:46
 * @version V1.0   
 */
@Data
public class SysUser extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;

    private String webSocketServer;
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
	/**  用户名  **/
	private String userName;
	/**  昵称  **/
	private String nickName;
	/**  密码  **/
	private String password;
	/**  邮箱  **/
	private String email;
	/**  移动  **/
	private String mobile;
	/**  用户类型  **/
	private Integer userType;
	/**  性别  **/
	private Integer sex;
	/**  职业  **/
	private String job;
	/**  区域  **/
	private Integer areaId;
	/**  区域 长编码（省市） **/
	private String areaLongCode;
	/**  头像id  **/
	private Integer picId;
	/**  媒介类型  **/
	private Integer mediaType;
	/**  字符备用1  **/
	private String appKey;
	/**  字符备用2  **/
	private String token;
	/**  字符备用3  **/
	private String att3;
	/**  字符备用4  **/
	private String att4;
	/**  字符备用5  **/
	private String att5;
	/**  字符备用6  **/
	private String att6;
	/**  时间备用1  **/
	private Date dateAtt1;
	/**  时间备用2  **/
	private Date dateAtt2;
	/**  组织id  **/
	private Integer groupId;
	/**  整数备用2  **/
	private Integer numAtt2;
	/**  数字备用1  **/
	private Double numAtt3;
	/**  数字备用2  **/
	private Double numAtt4;
	/**  备注  **/
	private String remark;
	/**  真实姓名  **/
	private String realName;
	/**  QQ  **/
	private String qq;
	/**  公司电话  **/
	private String companyTel;
	/**  公司地址 **/
	private String companyAddress;
	/**  个人专长 **/
	private String specialityValue;
	/**  个人简介  **/
	private String intro;
	/**  申请时间  **/
	private Date applicationDate;
	/**  邮箱验证状态  **/
	private Integer emailVerifyState;
	/**  手机验证状态  **/
	private Integer mobileVerifyState;
	/**  身份证验证状态  **/
	private Integer idcardVerifyState;
	/**  等级  **/
	private Integer level;
	/**  验证人员  **/
	private String verifyUser;
	/**  验证时间  **/
	private Date verifyDate;
	/**  用户角色id集合  **/
	private String roleIds;
	//******登录账号
	private String account;
	//******用户类型
	private String userTypeName;
	//头像路径
	private String picPath;
	private String userPic;
	/*心跳间隔*/
	private Integer heartbeatTime;
	/*登录有效时间*/
	private Long validTime;
	/**识别该用户是否在其他设备登录
	 * 0:未在其他设备登录
	 * 1:已在其他设备登录*/
	private Integer isLoginIn;
	/*用户登录的设备号*/
	private String terminalImei;
	/*消费总金额*/
	private Double  consumAmount;
	/*账户余额*/
	private Double  balanceAmount;
	//登录设备限制1:允许所有类型网卡登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录
	private Integer deviceRestrict;
	/**详细地址*/
	private String address;
	
	private String payCallBackServer;
	
	private String webSocketMessage;
	
	private String serverUrl;
	/*该用户是否为 方案推荐审核管理员    1 为是  */
	private Integer  designPlanCheckAdministrator;
	/**
	 * 文件加密key，app端用来解密
	 * add by yanghz
	 */
	private String cryptKey;
	/** 是否开通移动端 (0:未开通，1：已开通) **/
    private Integer existsMobile;
    /** 移动端开通时间 **/
	private Date mobileOpenedDate;
	/** 移动端截止时间 **/
	private Date mobileClosedDate;
    /** 移动端续费年限 **/
	private Integer renewNumber;
	//服务化地址集合
	private List<Map<String,String>> servitizationList;
	//企业/经销商id
	private Integer businessAdministrationId;
	//经销商账号类型：0(散)、1(主账号)、2(子账号)
	private Integer franchiserAccountType;
	//经销商账号组id   
	private Integer franchiserGroupId;
	//是否需要更新密码(0:不需要更新,1:需要更新)
	private Integer passwordUpdateFlag;
	/**
	 * 分布式资源存储,域名配置
	 */
	private List<ResourcesUrl> resourcesUrls;
	/**
	 * 用户获取验证码时的状态
	 *  register 表示注册
	 *  updateMobileByLogin 表示必须登录才可以修改密码 
	 *  updateMobileByForget 表示不必登录也可以修改密码
	 */
	private String type;

	public class ResourcesUrl{
		
		private List<String> modelName;
		
		private String domain;

		public List<String> getModelName() {
			return modelName;
		}

		public void setModelName(List<String> modelName) {
			this.modelName = modelName;
		}

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

	}
	/**用作压力测试*/
	private String designPlanId;
	//个人专长
	private String specialityName;
	//驳回原因
	private String reasonRejected;
	//个人资料旧手机
	private String oldMobile;
    //是否已关注
    private Integer attentionState;
    //绑定品牌id
    private String brandIds;
	//省份
	private String provinceCode;
	//市
	private String cityCode;
	/**
	 * 组织ID
	 */
	private Integer orgId;
	/**
	 * 组织名称
	 */
	private String orgName;
	/**所在地**/
	private String areaName;
	/**所在地**/
	private String jobName;

	private String serviceUrl;

	private String resourcesUrl;
	
	private String siteName;
	
	private String sitekey;
	//粉丝数量
	private Integer fansCount;
	//关注数量
	private Integer attentionCount;
	//作品数量
	private Integer worksCount;
	//访问数量
	private Integer accessCount;
	//审核1人员
	private String auditor1;
	//审核2人员
	private String auditor2;
	//审核3人员
	private String auditor3;
	/*用户绑定设备号*/
	private String userImei;
	
	private Map<String,String> resourceMap;
	/**
	 * 经销商主账号id
	 */
	private Integer franchiserId;

	/**获取对象的copy**/
	public LoginUser toLoginUser(){
		LoginUser obj =  new LoginUser();
		obj.setId(this.id);
		obj.setUserType(this.userType);
		obj.setLoginId(this.sysCode);
		obj.setLoginName(this.nickName);
		obj.setLoginPhone(this.mobile==null?"":this.mobile.toString());
		obj.setLoginEmail(this.email);
		obj.setName(this.userName);
		obj.setAppKey(this.appKey);
		obj.setToken(this.token);
		obj.setGroupId(this.groupId);
		obj.setSex(this.sex);
		obj.setPicPath(this.picPath);
		obj.setBrandIds(this.brandIds);
		return obj;
	}
	
}
