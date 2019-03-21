package com.sandu.api.solution.input;

import lombok.Data;
import org.apache.tools.ant.taskdefs.optional.extension.LibFileSet;

import java.io.Serializable;
import java.util.List;
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

    private String appId;

    private String appName;

    private Map<String, MiniProgramDashboardConfig> configMap;

    private String config;

}
