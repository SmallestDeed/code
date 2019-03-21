package com.sandu.api.solution.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * 对应表为： res_design
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 2018/5/24 17:19
 */
@Data
public class RenderRoamConfig implements Serializable {

    private Integer id;
    private Integer oldId;
    private String fileCode;
    private String fileName;
    private String fileOriginalName;
    private String fileType;
    private String fileSize;
    private String fileWeight;
    private String fileHigh;
    private Integer fileLength;
    private String fileSuffix;
    private String fileFormat;
    private String fileLevel;
    private String filePath;
    private String fileDesc;
    private Integer fileOrdering;
    private String sysCode;
    private String creator;
    private Date gmtCreate;
    private String modifier;
    private Date gmtModified;
    private Integer isDeleted;
    private String fileKey;
    private Integer businessId;
    private String smallPicInfo;
    private Integer viewPoint;
    private Integer scene;
    private Integer sequence;
    private Integer renderingType;
    private String panoPath;
    private Integer minHeight;
    private Integer isModelShare;
    private String att1;
    private String att2;
    private String att3;
    private String att4;
    private String att5;
    private String att6;
    private Integer numa1;
    private Integer numa2;
    private Integer numa3;
    private Integer numa4;
    private Integer numa5;
    private Integer numa6;
    private String remark;
    private String source;
}

