package com.sandu.api.shopOffline.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangHaiLin
 * @date 2018/12/8  16:04
 */
@Data
public class CompanyShopOffline implements Serializable{

    private Integer id;
    private Integer companyId;
    private String shopName;
    private String provinceCode;
    private String cityCode;
    private String areaCode;
    private String streetCode;
    private String longAreaCode;
    private String contactName;
    private String contactPhone;
    private String shopAddress;
    private Integer logoPicId;
    private String coverPicId;
    private Integer claimStatus;
    private Date claimTime;
    private Integer claimUserId;
    private Integer claimCompanyId;
    private Integer isDeleted;
    private Integer isRelease;
    private String creator;
    private Date gmtCreate;
    private String modifier;
    private Date gmtModified;

}
