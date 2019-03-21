
package com.sandu.api.user.output;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sandu.api.user.model.bo.UserMenuBO;
import com.sandu.api.user.model.bo.UserMenuTreeBO;

import lombok.Data;


@Data
public class MerchantMgrUserLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nickName;
    private String token;
    private String picPath;//头像
    private String mobile;
    
    private UserMenuTreeBO menuTree;
    private Set<String> permissions;
    private Map<String, Set<String>> queryFields;
    
    private Integer passwordUpdateFlag;
    private List<String> roleCodeList;//角色编码

    private String loginName;

    private String userName;

    /**
     * 企业/经销商id
     */
    private Long businessAdministrationId;

    /**
     * 企业Id
     */
    private Long companyId;

    //套餐快到期(临期标志)
    private boolean tipsFlag;

    //套餐过期标记
    private boolean maturityFlag;

    //套餐快到期剩余天数
    private Integer RemainingDays;

    //套餐类型 =>{} 1.使用 0.正式
    private Integer serviceType;

    //套餐用户表示 => false:老用户 true:套餐用户
    private Boolean oldServiceFlag = false;

    private String serviceName;

    //账号剩余时间(add by wangHaiLin)
    private Integer leftTime;
    //账号到期时间
    private String accountFailureTimeStr;
    //套餐到期时间
    private String ServiceFailureTimeStr;
    //是否当天初次登录
    private boolean firstLoginToday;


    //公司是否需要审核
    private Integer isExamine;

    private Integer userType;
}
