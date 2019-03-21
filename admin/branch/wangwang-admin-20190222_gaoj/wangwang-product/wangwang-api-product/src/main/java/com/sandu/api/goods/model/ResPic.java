package com.sandu.api.goods.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ResPic implements Serializable
{
    private Long id;

    private String picCode;

    private String picName;

    private String picFileName;

    private String picType;

    private Integer picSize;

    private String picWeight;

    private String picHigh;

    private String picSuffix;

    private String picLevel;

    private String picFormat;

    private String picPath;

    private String picDesc;

    private String picOrdering;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String fileKey;

    private String fileKeys;

    private String businessIds;

    private String smallPicInfo;

    private Integer viewPoint;

    private Integer scene;

    private Date dateAtt1;

    private Date dateAtt2;

    private Integer businessId;

    private Integer numAtt2;

    private BigDecimal numAtt3;

    private BigDecimal numAtt4;

    private String remark;

    private Integer sequence;

    private String renderingType;

    private String panoPath;

    private Integer sysTaskPicId;
}