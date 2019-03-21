package com.sandu.api.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MgrRechargeLog implements Serializable {

    private Integer id;

    /**  用户id  **/
    private Integer userId;
    /**  充值金额(元)  **/
    private Double rechargeAmount;

    /**  实时余额(元)  **/
    private Double currentBalance;

    /**  充值类型  **/
    private Integer rechargeType;
    /**  充值状态  **/
    private Integer rechargeStatus;
    /**  充值操作人员id  **/
    private Integer administratorId;
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
    /**被充值人员用户名*/
    private String username;
    /**充值类型(name)*/
    private String rechargeTypeInfo;
    /**充值状态(name)*/
    private String rechargeStatusInfo;
    /**充值人员姓名*/
    private String administratorName;
    /**被充值人员手机号*/
    private String mobile;
    //平台编码
    private String platformName;
    //订单号
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String orderNo;
}
