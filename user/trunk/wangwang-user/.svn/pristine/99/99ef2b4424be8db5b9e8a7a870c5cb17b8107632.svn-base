package com.sandu.api.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class AuthorizedConfig implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    /*开始时间*/
	private Date startTime;
	
	private String startTimeBegin;
	private String startTimeEnd;
 
    private String validTimeBegin;
    
    private String validTimeEnd;
    
    private String activityDetails;
    
    /**
     * 只作用于vendorAuthorizedConfigList  方法的查询条件 ， 查询 该授权码 和 公司的经营类型
     */
    private Integer companyType;
    
    /**
     * 只作用于getDealersAuthorizedConfigByBrandId  方法的查询条件 ， 通过 品牌集合 查询所有经销商
     */
    private List<Integer>brandIdList = new ArrayList<Integer>();

	/**  序列号  **/
	private String authorizedCode;
	/**  密码  **/
	private String password;
	/**  企业ID  **/
	private String companyId;
	/**  品牌ID  **/
	private String brandIds;
	/**  大分类  **/
	private String bigType;
	/**  小分类  **/
	private String smallType;
	/**  小分类ids  **/
	private String smallTypeIds;
	/**  产品IDS  **/
	private String productIds;
	/**  授权状态
	 * 	  0:未使用;
	 *    1:正常 
	 *    2:停用 **/
	private Integer state;
	/**  代理商  **/
	private Long userId;
	/**  终端IMEI  **/
	private String terminalImei;
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
	/*付款来源(数据字典value)*/
	private Integer payMethodValue;
	/*付费模式value值*/
	private Integer payModelValue;
	/*付款状态(数据字典value)*/
	private Integer payStatusValue;
	/*单价*/
	private Double unitPrice;
	
	/*销售人员id*/
	private Integer salerId;
	/**有效时间*/
	private Date validTime;
	
	private Integer validState;
	
	/*序列号类型*/
	private Integer authorizedCodeType;
	
	/*是否开通*/
	private Integer isOpen;
	
	private String validTimeTranscoding;
	
	/**  确认单号  **/
	private String orderNo ;
	
	/*活动id*/
	private Integer activityId;

	
	/*有效时长*/
	private Integer validDay;
	
	/*1延长 2缩短  和 有效时长策略validStrategy关联 */
	private Integer validStrategyRadio;
	
	/*价格策略*/
	private String priceStrategy;
	
	/*有效时长策略*/
	private String validStrategy;
	
	/*活动ids*/
	private String activityIds;
	
	private Double originalPrice ;
	//手机
	private String  mobile;
	
	private Integer notOutCount;
	
	/**
	 * 序列号类型
	 */
	private Integer type;
	/**
	 * 序列号激活期限类型
	 */
	private Integer activationTimeType;
	/**
	 * 序列号最晚激活日期
	 */
	private Date latestActivationDate;

    
    /**获取对象的copy**/
    public AuthorizedConfig copy(){
       AuthorizedConfig obj =  new AuthorizedConfig();
       obj.setAuthorizedCode(this.authorizedCode);
       obj.setPassword(this.password);
       obj.setCompanyId(this.companyId);
       obj.setBrandIds(this.brandIds);
       obj.setBigType(this.bigType);
       obj.setSmallType(this.smallType);
       obj.setSmallTypeIds(this.smallTypeIds);
       obj.setProductIds(this.productIds);
       obj.setState(this.state);
       obj.setUserId(this.userId);
       obj.setTerminalImei(this.terminalImei);
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
    
    
    
    public String companyName ;
    public String brandNames ;
    public String bigTypeNames ;
    public String smallTypeNames ;
    public String productNames;
    public String nickName;

}
