package com.sandu.api.user.model.bo;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysUser;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class UserInfoBO extends SysUser {
    private static final long serialVersionUID = 1337259213476894221L;
    /**
     * 文件加密key，app端用来解密
     */
    String cryptKey;
    private String msgId = null;
    private String userKey;
    private String webSocketMessage;
    private String payCallBackServer;
    private Integer IsLoginIn;
    /*用户登录的设备号*/
    private String terminalImei;
    private Integer heartbeatTime;
    private String serverUrl;
    private String resourcesUrl;
    
    /**
     * add by huangsongbo 2018.11.17
     * 专为户型绘制的资源做的一个单独的资源访问域名
     */
    private String resourcesUrlStandby;
    
    private List<ResourcesUrl> resourcesUrls;
    private List<Map<String, String>> servitizationList;//服务化地址集合
    private String siteName;
    private String Sitekey;
    private Map<String, String> resourceMap;
    private List<SysRole> roleList;
    private List<String> roleCodeList;//角色编码
    private String picPath;
    private String userPic;
    private UserMenuTreeBO menuTree;
    private List<UserMenuBO> menuList;
    private Set<String> permissions;
    private Map<String, Set<String>> queryFields;

    private String currentLoginCompanyName;
    private List<CompanyInfoBO> companyList;
    private String userCenterUrl;
    private String companyLogo;

    //套餐快到期(临期标志)
    private boolean tipsFlag;

    //套餐过期标记
//    private boolean maturityFlag;

    //套餐快到期剩余天数
    private Integer RemainingDays;

    //套餐类型 =>{} 1.使用 0.正式
//    private Integer serviceType;


    //账号剩余时间
    private Integer leftTime;

    //套餐用户表示 => false:老用户 true:套餐用户
    private Boolean oldServiceFlag = false;

    private BaseCompany currentCompany;

    private Integer isVirtual;

    //移动B端赠送渲染数量{}单位:月
    private Integer mobile2bGiveRender;

    //登录平台Id
    private Long platformId;

    //公司是否需要审核
    private Integer isExamine;

    private String promptMessage;
    
    /**
     * 登录用户对应的公司类型
     */
    private Integer companyType;

    private Integer promptUpdatePassword = 0;
}
