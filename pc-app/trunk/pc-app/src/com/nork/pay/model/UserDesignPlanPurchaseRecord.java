package com.nork.pay.model;

import java.io.Serializable;
import java.util.Date;

public class UserDesignPlanPurchaseRecord implements Serializable{
    private static final long serialVersionUID = -7338880875564583514L;

    private String notifyUrl;

    private String payUrl;

    private String wxPayIp;

    private String purchaseSource;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getWxPayIp() {
        return wxPayIp;
    }

    public void setWxPayIp(String wxPayIp) {
        this.wxPayIp = wxPayIp;
    }

    public String getPurchaseSource() {
        return purchaseSource;
    }

    public void setPurchaseSource(String purchaseSource) {
        this.purchaseSource = purchaseSource;
    }

    /**
     * 
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 推荐方案id
     */
    private Long designPlanId;

    /**
     * 方案名称
     */
    private String planName;

    /**
     * 交易单号
     */
    private String tradeNo;

    /**
     * 支付状态; 0未付款;1付款中;2已付款;3支付失败
     */
    private Integer tradeStatus;

    /**
     * 交易金额
     */
    private Double tradeAmount;

    /**
     * 购买方式(0-支付宝;1-微信;2-其他)
     */
    private Integer tradeType;

    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除（0:否，1:是）
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0.装进我家,1.产品替换,2.方案改造,3.硬装替换，4.全屋替换
     * */
    private Integer useType;

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户id
     * @return user_id 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 推荐方案id
     * @return design_plan_id 推荐方案id
     */
    public Long getDesignPlanId() {
        return designPlanId;
    }

    /**
     * 推荐方案id
     * @param designPlanId 推荐方案id
     */
    public void setDesignPlanId(Long designPlanId) {
        this.designPlanId = designPlanId;
    }

    /**
     * 方案名称
     * @return plan_name 方案名称
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * 方案名称
     * @param planName 方案名称
     */
    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    /**
     * 交易单号
     * @return trade_no 交易单号
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * 交易单号
     * @param tradeNo 交易单号
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    /**
     * 支付状态; 0未付款;1付款中;2已付款;3支付失败
     * @return trade_status 支付状态; 0未付款;1付款中;2已付款;3支付失败
     */
    public Integer getTradeStatus() {
        return tradeStatus;
    }

    /**
     * 支付状态; 0未付款;1付款中;2已付款;3支付失败
     * @param tradeStatus 支付状态; 0未付款;1付款中;2已付款;3支付失败
     */
    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    /**
     * 交易金额
     * @return trade_amount 交易金额
     */
    public Double getTradeAmount() {
        return tradeAmount;
    }

    /**
     * 交易金额
     * @param tradeAmount 交易金额
     */
    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    /**
     * 购买方式(0-支付宝;1-微信;2-其他)
     * @return trade_type 购买方式(0-支付宝;1-微信;2-其他)
     */
    public Integer getTradeType() {
        return tradeType;
    }

    /**
     * 购买方式(0-支付宝;1-微信;2-其他)
     * @param tradeType 购买方式(0-支付宝;1-微信;2-其他)
     */
    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 交易时间
     * @return trade_time 交易时间
     */
    public Date getTradeTime() {
        return tradeTime;
    }

    /**
     * 交易时间
     * @param tradeTime 交易时间
     */
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    /**
     * 创建者
     * @return creator 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 创建时间
     * @return gmt_create 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 创建时间
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改人
     * @return modifier 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 修改人
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 修改时间
     * @return gmt_modified 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 是否删除（0:否，1:是）
     * @return is_deleted 是否删除（0:否，1:是）
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除（0:否，1:是）
     * @param isDeleted 是否删除（0:否，1:是）
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}