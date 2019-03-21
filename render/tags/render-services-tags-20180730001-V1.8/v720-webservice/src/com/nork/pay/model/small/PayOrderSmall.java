package com.nork.pay.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: PayOrder.java 
 * @Package com.nork.pay.model.small
 * @Description:支付-支出凭证
 * @createAuthor pandajun 
 * @CreateDate 2016-09-19 16:17:40
 * @version V1.0   
 */
public class PayOrderSmall  implements Serializable{
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
	/**  订单号  **/
	private String orderNo;
	/**  订单总金额，单位为分  **/
	private Integer totalFee;
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


}
