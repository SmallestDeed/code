package com.sandu.api.supply.input;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 11:22 2018/4/28 0028
 * @Modified By:
 */
@Data
public class SupplyDemandAdd implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer type;

    private Integer creatorId;

    private Integer creatorTypeValue;

    private Integer category;

    private String province;

    private String city;

    private String district;

    private String address;

    private Integer coverPicId;

    private String title;

    private String description;

    private String descriptionPicId;

    private Integer decorationCompany;

    private Integer designer;

    private Integer materialShop;

    private Integer proprietor;

    private Integer builder;

    private Integer pushStatus;

    private Integer businessStatus;

    private Date gmtPublish;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String remark;
}
