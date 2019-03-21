package com.sandu.api.solution.model;

import com.sandu.api.company.output.CompanyIntroduceVO;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/18
 * @since : sandu_yun_1.0
 */
@Data
public class MiniProgramDashboard implements Serializable{

    private Long id;

    private Integer companyId;

    private String appId;

    private String appName;

    private Map<String, MiniProgramDashboardConfig> configMap;

    private CompanyIntroduceVO companyIntroduceVO;

    private String config;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;
}
