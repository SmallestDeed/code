/**
 * 文件名：UserLevelCfg.java
 * <p>
 * 版本信息：
 * 日期：2017年8月9日
 * Copyright 足下 Corporation 2017
 * 版权所有
 */
package com.sandu.user.model;

import java.io.Serializable;

/**
 *
 * 项目名称：timeSpace 类名称：UserLevelCfg 类描述： 创建人：Timy.Liu 创建时间：2017年8月9日 下午2:56:09
 * 修改人：Timy.Liu 修改时间：2017年8月9日 下午2:56:09 修改备注：
 *
 * @version
 *
 */
public class UserLevelCfg implements Serializable {

    /**
     * serialVersionUID:TODO 方法描述：
     *
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 8641913679805322812L;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 用户群体类型，见constant定义
     */
    private int userGroupType;

    /**
     * 用户群体类型 使用版本，见constant定义
     */
    private int version;

    /**
     * 用户级别
     */
    private int userLevel;

    /**
     * 移动端登录数量限制，系统数据字典中的valueKey
     */
    private String mobileLimitKey;

    /**
     * pc端登录数量限制，系统数据字典中的valueKey
     */
    private String pcLimitKey;

    /** 业务类型（1，设备数量 2 资源数量）常量也行 */
    private int businessTypeId;

    /** 设备资源表id（数据字典）与sys_product_level_cfg表的主键 **/
    private int businessId;

    /** 资源大类（0 硬装产品,1软装产品,2电器产品,3资源大类空间） */
    private int resBigTypeId;
    /**
     * 产品大类在数据字典中点编。空间和样板房是自定义的key
     */
    private String resBigTypeNum;
    /** 资源小类（客餐厅/卧室/厨房；表中资源，在数据字典中存在） */
    int resSmallTypeId;

    /** 1百分比/0数字 */
    private String resNumMothod;

    /** 值百分比整数，数字整数 */
    private String resNum;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserGroupType() {
        return userGroupType;
    }

    public void setUserGroupType(int userGroupType) {
        this.userGroupType = userGroupType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getMobileLimitKey() {
        return mobileLimitKey;
    }

    public void setMobileLimitKey(String mobileLimitKey) {
        this.mobileLimitKey = mobileLimitKey;
    }

    public String getPcLimitKey() {
        return pcLimitKey;
    }

    public void setPcLimitKey(String pcLimitKey) {
        this.pcLimitKey = pcLimitKey;
    }

    public int getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(int businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getResBigTypeId() {
        return resBigTypeId;
    }

    public void setResBigTypeId(int resBigTypeId) {
        this.resBigTypeId = resBigTypeId;
    }

    public int getResSmallTypeId() {
        return resSmallTypeId;
    }

    public void setResSmallTypeId(int resSmallTypeId) {
        this.resSmallTypeId = resSmallTypeId;
    }

    public String getResNumMothod() {
        return resNumMothod;
    }

    public void setResNumMothod(String resNumMothod) {
        this.resNumMothod = resNumMothod;
    }

    public String getResNum() {
        return resNum;
    }

    public void setResNum(String resNum) {
        this.resNum = resNum;
    }

    public String getResBigTypeNum() {
        return resBigTypeNum;
    }

    public void setResBigTypeNum(String resBigTypeNum) {
        this.resBigTypeNum = resBigTypeNum;
    }

}
