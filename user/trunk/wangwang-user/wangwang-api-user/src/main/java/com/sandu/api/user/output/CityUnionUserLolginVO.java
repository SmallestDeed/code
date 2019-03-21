package com.sandu.api.user.output;

import com.sandu.api.user.model.bo.UserMenuTreeBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class CityUnionUserLolginVO implements Serializable {

    private Long id;

    private String name;

    private String userName;

    private Long businessAdministrationId;

    private String appKey;

    private String userKey;

    private String token;

    private Integer userType;

    private String serverUrl;

    private String resourcesUrl;

    private String siteName;

    private Double balanceAmount;

    private Double consumAmount;

    private Integer mediaType;

    private String mobile;
    private String loginPhone;

    private String cryptKey;

    private UserMenuTreeBO menuTree;
    private Set<String> permissions;
    private Map<String, Set<String>> queryFields;

    private Integer passwordUpdateFlag;

    private List<String> roleCodeList;//角色编码
}
