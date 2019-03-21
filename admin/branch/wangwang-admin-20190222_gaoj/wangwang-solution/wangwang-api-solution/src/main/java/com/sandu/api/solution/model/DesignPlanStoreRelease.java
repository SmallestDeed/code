package com.sandu.api.solution.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignPlanStoreRelease implements Serializable{
    private Long id;

    private String uuid;

    private Integer userId;

    private String shareTitle;

    private Integer shareType;

    private Integer pv;

    private Integer unionGroupId;

    private Integer unionSpecialOfferId;

    private Integer unionContactId;

    private Integer houseId;

    private Integer housePicId;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String remark;

}