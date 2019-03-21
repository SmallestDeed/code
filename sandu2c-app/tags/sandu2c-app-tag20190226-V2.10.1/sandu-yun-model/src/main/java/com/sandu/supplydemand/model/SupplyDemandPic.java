package com.sandu.supplydemand.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SupplyDemandPic implements Serializable{
    private static final long serialVersionUID = -1L;
    private Long id;

    private String picName;

    private Integer picSize;

    private String picWeight;

    private String picHigh;

    private String picFormat;

    private String picPath;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String remark;

}