package com.sandu.api.user.output;

import com.sandu.api.user.model.bo.UserMenuTreeBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Data
public class MobileUserLoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private String userPic;

    private UserMenuTreeBO menuTree;
    private Set<String> permissions;
    private Map<String, Set<String>> queryFields;
    
    private Integer passwordUpdateFlag;

    private List<String> roleCodeList;//角色编码

    private String companyName;

    //套餐快到期(临期标志)
    private boolean tipsFlag;

    //套餐过期标记
    private boolean maturityFlag;

    //套餐快到期剩余天数
    private Integer RemainingDays;

    //套餐类型 =>{} 1.使用 0.正式
    private Integer serviceType;

    //套餐剩余时间
    private Integer leftTime;

    //套餐用户表示 => false:老用户 true:套餐用户
    private Boolean oldServiceFlag = false;

    private  String serviceName;

    private Integer isVirtual;

    private String currentCompanyName;

    private Long companyId;

    //移动B端赠送渲染数量{}单位:月
    private Integer mobile2bGiveRender;

    //赠送度币
    private Double giveBalanceAmount;
    
    private String sessionId;

    //公司是否需要审核
    private Integer isExamine;

    private String promptMessage;
    
    /**
     * 登录用户对应的公司类型
     */
    private Integer companyType;

    //pc端、移动端是否需要提示用户修改密码 0:不需要，1:需要
    private Integer promptUpdatePassword;
}
