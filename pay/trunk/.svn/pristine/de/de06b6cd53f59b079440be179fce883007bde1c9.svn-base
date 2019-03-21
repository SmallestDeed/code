package com.sandu.pay.order.model;

import com.sandu.common.model.Mapper;
import com.sandu.common.pay.Config;
import com.sandu.pay.goods.model.Goods;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**   
 * @Title: PayOrder.java 
 * @Package com.sandu.pay.model
 * @Description:支付单
 * @createAuthor pandajun 
 * @CreateDate 2016-09-19 16:17:40
 * @version V1.0   
 */
public class PayOrder extends Mapper implements Serializable{

	private static final long serialVersionUID = 1L;

    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  用户ID  **/
	private Integer userId;
	/**  产品类型  **/
	private String productType;
	/**  产品ID  **/
	private Integer productId;
	private String productName;
	private String productDesc;
	
	/**  订单号  **/
	private String orderNo;
	/**  支付金额，单位为分  **/
	private Integer totalFee;
	/***
	 * 调节金额
	 */
	private Integer  adjustFee=0;
	/**  支出类型  **/
	private String payType;
	/**  支出状态  **/
	private String payState;
	/**  预付id  **/
	private String prepayId;
	/**  编码路径  **/
	private String codeUrl;
	/**  参考id  **/
	private String refId;
	/**  公开Id  **/
	private String openId;
	/**  凭证时间  **/
	private Date orderDate;
	/**业务类别**/
	private String bizType;
	/**财务类别**/
	private String financeType;
	
	private String tradeType;
	
	private String storeId;
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
	/**渲染任务id*/
	private Integer taskId;
	/**
	 *  超时时间
	 *  订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
		 注意：最短失效时间间隔必须大于5分钟
	 */
	private String timeoutExpress;

	private Goods goods;

	//方案ID
	private Integer planId;

	/**方案名称*/
	private String planName;

	//户型ID
	private Integer houseId;
	//当前度币
	private Integer currentIntegral;
	//用户度币
	private Integer userTotalIntegral;

	// 平台id
	private Integer platformId;

	
	/**
	 * 回调地址
	 */
	private String notifyUrl;
	
	private String tradeNo;
    /**
     * 支付网关生成的交易号
     */
    private String payTradeNo;
    
    /**
     * 退款单号
     */
    private String refundNo;
    
    /**
     * 支付网关生成的退款单号
     */
    private String payRefundNo;
    
    
    public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getPayRefundNo() {
		return payRefundNo;
	}

	public void setPayRefundNo(String payRefundNo) {
		this.payRefundNo = payRefundNo;
	}

	public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getCurrentIntegral() {
		return currentIntegral;
	}

	public void setCurrentIntegral(Integer currentIntegral) {
		this.currentIntegral = currentIntegral;
	}

	public Integer getUserTotalIntegral() {
		return userTotalIntegral;
	}

	public void setUserTotalIntegral(Integer userTotalIntegral) {
		this.userTotalIntegral = userTotalIntegral;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public String getTimeoutExpress() {
		return timeoutExpress;
	}

	public void setTimeoutExpress(String timeoutExpress) {
		this.timeoutExpress = timeoutExpress;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getProductType() {
		return productType;
	}
	public void setProductType(String productType){
		this.productType = productType;
	}
	public  Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId){
		this.productId = productId;
	}
	public  String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	public  Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee){
		this.totalFee = totalFee;
	}
	public  String getPayType() {
		return payType;
	}
	public void setPayType(String payType){
		this.payType = payType;
	}
	public  String getPayState() {
		return payState;
	}
	public void setPayState(String payState){
		this.payState = payState;
	}
	public  String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId){
		this.prepayId = prepayId;
	}
	public  String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl){
		this.codeUrl = codeUrl;
	}
	public  String getRefId() {
		return refId;
	}
	public void setRefId(String refId){
		this.refId = refId;
	}
	public  String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId){
		this.openId = openId;
	}
	public  Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate){
		this.orderDate = orderDate;
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

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
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
        PayOrder other = (PayOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getProductType() == null ? other.getProductType() == null : this.getProductType().equals(other.getProductType()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getTotalFee() == null ? other.getTotalFee() == null : this.getTotalFee().equals(other.getTotalFee()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()))
            && (this.getPayState() == null ? other.getPayState() == null : this.getPayState().equals(other.getPayState()))
            && (this.getPrepayId() == null ? other.getPrepayId() == null : this.getPrepayId().equals(other.getPrepayId()))
            && (this.getCodeUrl() == null ? other.getCodeUrl() == null : this.getCodeUrl().equals(other.getCodeUrl()))
            && (this.getRefId() == null ? other.getRefId() == null : this.getRefId().equals(other.getRefId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getOrderDate() == null ? other.getOrderDate() == null : this.getOrderDate().equals(other.getOrderDate()))
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
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getProductType() == null) ? 0 : getProductType().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getTotalFee() == null) ? 0 : getTotalFee().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        result = prime * result + ((getPayState() == null) ? 0 : getPayState().hashCode());
        result = prime * result + ((getPrepayId() == null) ? 0 : getPrepayId().hashCode());
        result = prime * result + ((getCodeUrl() == null) ? 0 : getCodeUrl().hashCode());
        result = prime * result + ((getRefId() == null) ? 0 : getRefId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getOrderDate() == null) ? 0 : getOrderDate().hashCode());
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
        return result;
    }
    
    /**获取对象的copy**/
    public PayOrder copy(){
       PayOrder obj =  new PayOrder();
       obj.setUserId(this.userId);
       obj.setProductType(this.productType);
       obj.setProductId(this.productId);
       obj.setOrderNo(this.orderNo);
       obj.setTotalFee(this.totalFee);
       obj.setPayType(this.payType);
       obj.setPayState(this.payState);
       obj.setPrepayId(this.prepayId);
       obj.setCodeUrl(this.codeUrl);
       obj.setRefId(this.refId);
       obj.setOpenId(this.openId);
       obj.setOrderDate(this.orderDate);
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
       map.put("productType",this.productType);
       map.put("productId",this.productId);
       map.put("productName",this.productName);
       map.put("productDesc",this.productDesc);
       map.put("orderNo",this.orderNo);
       map.put("totalFee",this.totalFee);
       map.put("payType",this.payType);
       map.put("payState",this.payState);
       map.put("prepayId",this.prepayId);
       map.put("codeUrl",this.codeUrl);
       map.put("refId",this.refId);
       map.put("openId",this.openId);
       map.put("orderDate",this.orderDate);
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

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getStoreId() {
		if(StringUtils.isBlank(storeId)){
			storeId = Config.getStore_id();
		}
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getFinanceType() {
		return financeType;
	}

	public void setFinanceType(String financeType) {
		this.financeType = financeType;
	}

	public Integer getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(Integer adjustFee) {
		this.adjustFee = adjustFee;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@Override
	public String toString() {
		return "PayOrder{" +
				"id=" + id +
				", userId=" + userId +
				", productType='" + productType + '\'' +
				", productId=" + productId +
				", productName='" + productName + '\'' +
				", productDesc='" + productDesc + '\'' +
				", orderNo='" + orderNo + '\'' +
				", totalFee=" + totalFee +
				", adjustFee=" + adjustFee +
				", payType='" + payType + '\'' +
				", payState='" + payState + '\'' +
				", prepayId='" + prepayId + '\'' +
				", codeUrl='" + codeUrl + '\'' +
				", refId='" + refId + '\'' +
				", openId='" + openId + '\'' +
				", orderDate=" + orderDate +
				", bizType='" + bizType + '\'' +
				", financeType='" + financeType + '\'' +
				", tradeType='" + tradeType + '\'' +
				", storeId='" + storeId + '\'' +
				", sysCode='" + sysCode + '\'' +
				", creator='" + creator + '\'' +
				", gmtCreate=" + gmtCreate +
				", modifier='" + modifier + '\'' +
				", gmtModified=" + gmtModified +
				", isDeleted=" + isDeleted +
				", att1='" + att1 + '\'' +
				", att2='" + att2 + '\'' +
				", numa1=" + numa1 +
				", numa2=" + numa2 +
				", remark='" + remark + '\'' +
				", taskId=" + taskId +
				", timeoutExpress='" + timeoutExpress + '\'' +
				", goods=" + goods +
				", planId=" + planId +
				", houseId=" + houseId +
				", currentIntegral=" + currentIntegral +
				", userTotalIntegral=" + userTotalIntegral +
				'}';
	}
}
