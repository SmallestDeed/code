package com.sandu.api.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class AuthorizedConfig implements Serializable {
    private long id;
    private String authorizedCode;
    private String password;
    private String companyId;
    private String brandIds;
    private String bigType;
    private String smallType;
    private String smallTypeIds;
    private String productIds;
    private long state;
    private long userId;
    private String terminalImei;
    private String sysCode;
    private String creator;
    private long gmtCreate;
    private String modifier;
    private long gmtModified;
    private long isDeleted;
    private String att1;
    private String att2;
    private long numa1;
    private long numa2;
    private String remark;
    private long upgradMethod;
    private String orderNo;
    private long payModelValue;
    private double unitPrice;
    private long salerId;
    private long payStatusValue;
    private long payMethodValue;
    private long validTime;
    private long validState;
    private long activityId;
    private String activityDetails;
    private long isOpen;
    private long authorizedCodeType;
    private long startTime;
    private long validDay;
    private String activityIds;
    private String priceStrategy;
    private String validStrategy;
    private int type;
    private int activationTimeType;
    private long latestActivationDate;
}
