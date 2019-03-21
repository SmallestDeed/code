package com.sandu.api.user.output;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.user.model.bo.CompanyInfoBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sandu.api.user.model.bo.UserMenuBO;


@Data
public class PcUserLoginVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String msgId = null;
    private Long id;
    private Integer userType;
    /**
     * 字符主键
     **/
    private String loginId;
    /**
     * 登录名称
     **/
    private String loginName;//
    private String loginPhone;// 登录手机号
    private String loginEmail;// 登录手机号
    private String name;// 登录中文名
    private String appKey;
    private String userKey;
    private String token;
    private String deviceId;
    private String mediaType;
    private Integer groupId;//组织ID
    private Integer sex;//性别
    private String picPath;//头像
    private String brandIds;//品牌(多个用逗号隔开)
    private String serverUrl;
    private String resourcesUrl;
    private Long businessAdministrationId;
    
    private Set<String> roles;
    private List<UserMenuBO> menuList;
    private Set<String> permissions;
    private Map<String, Set<String>> queryFields;

    private List<String> roleCodeList;//角色编码

    private String userName;

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

    private BaseCompany currentCompany;

    private String currentCompanyName;

    private  String serviceName;

    //公司是否需要审核
    private Integer isExamine;
    
    private String sessionId;

    //pc端、移动端是否需要提示用户修改密码 0:不需要，1:需要
    private Integer promptUpdatePassword;
}
