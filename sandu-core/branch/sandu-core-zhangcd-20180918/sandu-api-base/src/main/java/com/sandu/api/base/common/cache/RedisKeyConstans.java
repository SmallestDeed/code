package com.sandu.api.base.common.cache;


/**
 * @desc Redis键常量定义
 * @date 20171016
 * @auto pengxuangang
 */
public class RedisKeyConstans {

	//Redis任务队列常量
    public static final String REDIS_TASK_LIST = "taskList";
    //替换任务
    public static final String REDIS_TASK_REPLACE_LIST = "taskReplaceList"; 
    //置顶
    public static final String REDIS_RENDER_TASK_STICK = "renderTaskStick";

    //用户会话对象
//    public static final String SESSION_USER_OBJECT_SET = UserSO.USER_OBJECT_SESSION_CONTANT;

    //设计方案
    public static final String DESIGN_PLAN_MAP = "DesignPlan";

    //设计方案样板房
    public static final String DESIGN_PLAN_TEMPLATE_MAP = "DesignPlanTemplet";
    
    //短信验证码
    public static final String SESSION_SMS_CODE = "SmsCode";

    //用户临时会话
    public static final String USER_TEMP_SESSION_MAP = "UserTempSession";

    //设计规则
    public static final String DESIGN_RULE_KEY = "DesignRule";

    //基本产品
    public static final String BASE_PRODUCT_MAP = "BaseProduct";

    //系统字典
    public static final String SYSTEM_DICTIONARY_KEY = "SystemDictionary";
    
    /**
     * 企业信息：
     * 		key:企业domain;
     * 		value:企业详细信息.
     */
    public static final String 	COMPANY_DOMAIN_MAP = "CompanyDomain";

    //异业联盟品牌
    public static final String UNION_BRAND_MAP = "UnionBrand";

    //异业联盟公司
    public static final String UNION_COMPANY_MAP = "UnionCompany";

    //存用户对方案的信息（是否点赞收藏等）; key：userId + ":" + designId; value：点赞状态：收藏状态（0：未XX，1：已XX）
    public static final String USER_DESIGN_PLAN_INFO_MAP = "CustomerUserDesignPlanInfoMap";

    //临时记录用户对方案的操作（同步数据库用）key：userId + ":" + designId; value：点赞状态：收藏状态（0：未XX，1：已XX）
    public static final String USER_DESIGN_PLAN_INFO_SYNCHRONIZE_MAP = "CustomerUserDesignPlanInfoOperationRecordSynchronizeMap";

    //存方案的点赞总数
    public static final String DESIGN_PLAN_LIKE_NUM_MAP = "CustomerDesignPlanLikeNumMap";
    //存方案点赞总数（同步数据库用）
    public static final String DESIGN_PLAN_LIKE_NUM_SYNCHRONIZE_MAP = "CustomerDesignPlanLikeNumSynchronizeMap";

    //存方案的收藏总数
    public static final String DESIGN_PLAN_FAVORITE_NUM_MAP = "CustomerDesignPlanFavoriteNumMap";
    //存方案的收藏总数
    public static final String DESIGN_PLAN_FAVORITE_NUM_SYNCHRONIZE_MAP = "CustomerDesignPlanFavoriteNumSynchronizeMap";


    /**
     * 企业信息：
     * 		key:企业appId;
     * 		value:企业详细信息.
     */
    public static final String 	COMPANY_APP_ID = "CompanyAPPId";
}
