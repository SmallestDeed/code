package com.sandu.api.user.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sandu.api.user.model.bo.UserMenuBO;
import com.sandu.api.user.model.bo.UserMenuTreeBO;


@Data
public class WebsiteUserLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户ID")
    private Long id;
    private String name;
    
    private String userName;
    
    private String appKey;

    private String userKey;

    private String token;

    private Integer userType;

    private String siteName;

    private Double balanceAmount;

    private Double consumAmount;

    private Integer mediaType;

    
    private UserMenuTreeBO menuTree;
    private Set<String> permissions;
    private Map<String, Set<String>> queryFields;

    private Integer passwordUpdateFlag;

    private List<String> roleCodeList;//角色编码
}
