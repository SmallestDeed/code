package com.sandu.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MediationBankInfo implements Serializable{
    private static final long serialVersionUID = 750313590741884063L;
    /**
     * 
     */
    private Integer id;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 银行卡号
     */
    private String bankNumber;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行支行名称
     */
    private String bankNameInfo;

    /**
     * 银行预留手机号
     */
    private String preMobile;

    /**
     * 身份证号码
     */
    private String cardNumber;

    /**
     * 身份证姓名
     */
    private String cardName;

    /**
     * 是否解绑(0:未解绑、1:已解绑)
     */
    private Integer isUnbind;

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
     * 是否删除(0:未删除、1:删除)
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;


}