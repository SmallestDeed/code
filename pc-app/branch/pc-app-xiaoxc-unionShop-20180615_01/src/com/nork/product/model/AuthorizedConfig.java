package com.nork.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: AuthorizedConfig.java 
 * @Package com.nork.product.model
 * @Description:产品-授权配置
 * @createAuthor pandajun 
 * @CreateDate 2016-04-27 14:07:34
 * @version V1.0   
 */
public class AuthorizedConfig  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }
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
    
    
	public List<Integer> getBrandIdList() {
		return brandIdList;
	}

	public void setBrandIdList(List<Integer> brandIdList) {
		this.brandIdList = brandIdList;
	}

 
	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public String getActivityDetails() {
		return activityDetails;
	}

	public void setActivityDetails(String activityDetails) {
		this.activityDetails = activityDetails;
	}

	public String getValidTimeBegin() {
		return validTimeBegin;
	}

	public void setValidTimeBegin(String validTimeBegin) {
		this.validTimeBegin = validTimeBegin;
	}

	public String getValidTimeEnd() {
		return validTimeEnd;
	}

	public void setValidTimeEnd(String validTimeEnd) {
		this.validTimeEnd = validTimeEnd;
	}
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
	private Integer userId;
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
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getActivationTimeType() {
		return activationTimeType;
	}

	public void setActivationTimeType(Integer activationTimeType) {
		this.activationTimeType = activationTimeType;
	}

	public Date getLatestActivationDate() {
		return latestActivationDate;
	}

	public void setLatestActivationDate(Date latestActivationDate) {
		this.latestActivationDate = latestActivationDate;
	}
	
	public Integer getNotOutCount() {
		return notOutCount;
	}

	public void setNotOutCount(Integer notOutCount) {
		this.notOutCount = notOutCount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getValidDay() {
		return validDay;
	}

	public void setValidDay(Integer validDay) {
		this.validDay = validDay;
	}

	public Integer getValidStrategyRadio() {
		return validStrategyRadio;
	}

	public void setValidStrategyRadio(Integer validStrategyRadio) {
		this.validStrategyRadio = validStrategyRadio;
	}

	public String getPriceStrategy() {
		return priceStrategy;
	}

	public void setPriceStrategy(String priceStrategy) {
		this.priceStrategy = priceStrategy;
	}

	public String getValidStrategy() {
		return validStrategy;
	}

	public void setValidStrategy(String validStrategy) {
		this.validStrategy = validStrategy;
	}

	public String getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(String activityIds) {
		this.activityIds = activityIds;
	}

	public Integer getActivityId() {
		return activityId;
	}
	public String getStartTimeBegin() {
		return startTimeBegin;
	}

	public void setStartTimeBegin(String startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}

	public String getStartTimeEnd() {
		return startTimeEnd;
	}

	public void setStartTimeEnd(String startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setId(Integer id) {
        this.id = id;
    }
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getValidTimeTranscoding() {
		return validTimeTranscoding;
	}

	public void setValidTimeTranscoding(String validTimeTranscoding) {
		this.validTimeTranscoding = validTimeTranscoding;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getSalerId() {
		return salerId;
	}

	public void setSalerId(Integer salerId) {
		this.salerId = salerId;
	}

	public Integer getPayMethodValue() {
		return payMethodValue;
	}

	public void setPayMethodValue(Integer payMethodValue) {
		this.payMethodValue = payMethodValue;
	}

	public Integer getAuthorizedCodeType() {
		return authorizedCodeType;
	}

	public void setAuthorizedCodeType(Integer authorizedCodeType) {
		this.authorizedCodeType = authorizedCodeType;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Integer getValidState() {
		return validState;
	}

	public void setValidState(Integer validState) {
		this.validState = validState;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getPayStatusValue() {
		return payStatusValue;
	}

	public void setPayStatusValue(Integer payStatusValue) {
		this.payStatusValue = payStatusValue;
	}

	public Integer getPayModelValue() {
		return payModelValue;
	}

	public void setPayModelValue(Integer payModelValue) {
		this.payModelValue = payModelValue;
	}

	public  String getAuthorizedCode() {
		return authorizedCode;
	}
	public void setAuthorizedCode(String authorizedCode){
		this.authorizedCode = authorizedCode;
	}
	public  String getPassword() {
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public  String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}
	public  String getBrandIds() {
		return brandIds;
	}
	public void setBrandIds(String brandIds){
		this.brandIds = brandIds;
	}
	public  String getBigType() {
		return bigType;
	}
	public void setBigType(String bigType){
		this.bigType = bigType;
	}
	public  String getSmallType() {
		return smallType;
	}
	public void setSmallType(String smallType){
		this.smallType = smallType;
	}
	public  String getSmallTypeIds() {
		return smallTypeIds;
	}
	public void setSmallTypeIds(String smallTypeIds){
		this.smallTypeIds = smallTypeIds;
	}
	public  String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds){
		this.productIds = productIds;
	}
	public  Integer getState() {
		return state;
	}
	public void setState(Integer state){
		this.state = state;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getTerminalImei() {
		return terminalImei;
	}
	public void setTerminalImei(String terminalImei){
		this.terminalImei = terminalImei;
	}
	public  String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}
	public  String getCreator() {
		return creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public  Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	public  String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	public  Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}
	public  Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public  String getAtt1() {
		return att1;
	}
	public void setAtt1(String att1){
		this.att1 = att1;
	}
	public  String getAtt2() {
		return att2;
	}
	public void setAtt2(String att2){
		this.att2 = att2;
	}
	public  Integer getNuma1() {
		return numa1;
	}
	public void setNuma1(Integer numa1){
		this.numa1 = numa1;
	}
	public  Integer getNuma2() {
		return numa2;
	}
	public void setNuma2(Integer numa2){
		this.numa2 = numa2;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}


   @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AuthorizedConfig other = (AuthorizedConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAuthorizedCode() == null ? other.getAuthorizedCode() == null : this.getAuthorizedCode().equals(other.getAuthorizedCode()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getBrandIds() == null ? other.getBrandIds() == null : this.getBrandIds().equals(other.getBrandIds()))
            && (this.getBigType() == null ? other.getBigType() == null : this.getBigType().equals(other.getBigType()))
            && (this.getSmallType() == null ? other.getSmallType() == null : this.getSmallType().equals(other.getSmallType()))
            && (this.getSmallTypeIds() == null ? other.getSmallTypeIds() == null : this.getSmallTypeIds().equals(other.getSmallTypeIds()))
            && (this.getProductIds() == null ? other.getProductIds() == null : this.getProductIds().equals(other.getProductIds()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getTerminalImei() == null ? other.getTerminalImei() == null : this.getTerminalImei().equals(other.getTerminalImei()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getNuma1() == null ? other.getNuma1() == null : this.getNuma1().equals(other.getNuma1()))
            && (this.getNuma2() == null ? other.getNuma2() == null : this.getNuma2().equals(other.getNuma2()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAuthorizedCode() == null) ? 0 : getAuthorizedCode().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getBrandIds() == null) ? 0 : getBrandIds().hashCode());
        result = prime * result + ((getBigType() == null) ? 0 : getBigType().hashCode());
        result = prime * result + ((getSmallType() == null) ? 0 : getSmallType().hashCode());
        result = prime * result + ((getSmallTypeIds() == null) ? 0 : getSmallTypeIds().hashCode());
        result = prime * result + ((getProductIds() == null) ? 0 : getProductIds().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getTerminalImei() == null) ? 0 : getTerminalImei().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getNuma1() == null) ? 0 : getNuma1().hashCode());
        result = prime * result + ((getNuma2() == null) ? 0 : getNuma2().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
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
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("authorizedCode",this.authorizedCode);
       map.put("password",this.password);
       map.put("companyId",this.companyId);
       map.put("brandIds",this.brandIds);
       map.put("bigType",this.bigType);
       map.put("smallType",this.smallType);
       map.put("smallTypeIds",this.smallTypeIds);
       map.put("productIds",this.productIds);
       map.put("state",this.state);
       map.put("userId",this.userId);
       map.put("terminalImei",this.terminalImei);
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
    
    public String companyName ;
    public String brandNames ;
    public String bigTypeNames ;
    public String smallTypeNames ;
    public String productNames;
    public String nickName;
    
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBrandNames() {
		return brandNames;
	}

	public void setBrandNames(String brandNames) {
		this.brandNames = brandNames;
	}

	public String getBigTypeNames() {
		return bigTypeNames;
	}

	public void setBigTypeNames(String bigTypeNames) {
		this.bigTypeNames = bigTypeNames;
	}

	public String getSmallTypeNames() {
		return smallTypeNames;
	}

	public void setSmallTypeNames(String smallTypeNames) {
		this.smallTypeNames = smallTypeNames;
	}

	public String getProductNames() {
		return productNames;
	}

	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	private List<BaseBrand> list = new ArrayList<BaseBrand>();
	private List<String> bigTypeList = new ArrayList<String>();
	private List<String> smallTypeList = new ArrayList<String>();
	private List<String> productList = new ArrayList<String>();

	public List<BaseBrand> getList() {
		return list;
	}
	
	public void setList(List<BaseBrand> list) {
		this.list = list;
	}

	public List<String> getBigTypeList() {
		return bigTypeList;
	}

	public void setBigTypeList(List<String> bigTypeList) {
		this.bigTypeList = bigTypeList;
	}

	public List<String> getSmallTypeList() {
		return smallTypeList;
	}

	public void setSmallTypeList(List<String> smallTypeList) {
		this.smallTypeList = smallTypeList;
	}

	public List<String> getProductList() {
		return productList;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

    
}
