package com.sandu.common.constant;

import com.sandu.common.util.Utils;

import java.util.ResourceBundle;

public class SystemCommonConstant {

    /*** 获取配置文件 tmg.properties */
    public final static ResourceBundle app = ResourceBundle.getBundle("app");
    public static final String RESOURCESURL = app.getString("app.resources.url");
    public static final String SITENAME = Utils.getValue("app.server.siteName", "nork");
    public static final String SITEKEY = Utils.getValue("app.server.siteKey", "online");

    // 将"loginUser"设置为静态常量LOGIN_USER
    public static final String LOGIN_USER = "loginUser";
    // 将"请先登录！"设置为静态常量PLEASE_LOGIN
    public static final String PLEASE_LOGIN = "请先登录！";
    // 将"用户未登录！"设置为静态常量USER_NOT_LOGINNING
    public static final String USER_NOT_LOGINNING = "用户未登录！";
    // 将"参数msgId不能为空"设置为静态常量MSGID_NOT_NULL
    public static final String MSGID_NOT_NULL = "参数msgId不能为空！";
    // 将“解绑成功!”设置为静态常量RELIEVE_SUCCESS
    public static final String RELIEVE_SUCCESS = "解绑成功!";
    // 将“绑定成功!”设置为静态常量BIND_SUCCESS
    public static final String BIND_SUCCESS = "绑定成功!";

    public static final String CACHE_ENABLE = "1"; // 缓存中取值

    public static final Integer STATE = 1; // 授权状态(正常)
    // 将“数据异常!”设置为静态常量DATA_EXCEPTION
    public static final String DATA_EXCEPTION = "数据异常!";
    // 将“redisCacheEnable”设置为静态常量REDIS_CACHE_ENABLE
    // redisCacheEnable(是否启用redis缓存)：0是open；1是close
    public static final String REDIS_CACHE_ENABLE = "redisCacheEnable";
    // 将“0”：开启redis缓存 设置为静态常量OPEN_REDIS_CACHE_ENABLE
    public static final String OPEN_REDIS_CACHE_ENABLE = "0";
    // 将“1”：关闭redis缓存 设置为静态常量CLOSE_REDIS_CACHE_ENABLE
    public static final String CLOSE_REDIS_CACHE_ENABLE = "1";
    // 将“清除成功” 设置为静态常量CLEAN_SUCCESS
    public static final String CLEAN_SUCCESS = "清除成功";
    // 将 "1111" 设置为静态常量 KO
    public static final String KO = "1111";

    /**
     * @CreateDate 2017-6-15 13:47:36
     * @CreateAuthor yangzhun
     */
    // 将"登录超时，请重新登录!"设置为静态常量LOGIN_OVERTIME
    public static final String LOGIN_OVERTIME = "登录超时，请重新登录!";
    // 将"目录名称catalogName参数为空！"设置为静态常量CATALOGNAMR_NOT_NULL
    public static final String CATALOGNAMR_NOT_NULL = "目录名称catalogName参数为空！";
    // 将"该目录名称无法创建,请重新命名！"设置为静态常量CATALOGNAMR_CANNOT_CREATE
    public static final String CATALOGNAMR_CANNOT_CREATE = "该目录名称无法创建,请重新命名！";
    // 将"登录失效！"设置为静态常量LOGIN_FAILURE
    public static final String LOGIN_FAILURE = "登录失效！";
    // 将"传参异常!"设置为静态常量PARAM_EXCEPTION
    public static final String PARAM_EXCEPTION = "传参异常!";
    // 将"none"设置为静态常量NONE
    public static final String NONE = "none";
    // 将"参数Id不能为空"设置为静态常量ID_NOT_NULL
    public static final String ID_NOT_NULL = "参数Id不能为空";
    // 将"参数userId不能为空"设置为静态常量USERID_NOT_NULL
    public static final String USERID_NOT_NULL = "参数userId不能为空";
    // 将"参数collectCatalogId不能为空"设置为静态常量COLLECTCATALOGID_NOT_NULL
    public static final String COLLECTCATALOGID_NOT_NULL = "参数collectCatalogId不能为空";
    // 将"参数templetId不能为空！"设置为静态常量TEMPLETID_NOT_NULL
    public static final String TEMPLETID_NOT_NULL = "参数templetId不能为空！";
    // 将"参数designTempletId不能为空！"设置为静态常量DESIGNTEMPLETID_NOT_NULL
    public static final String DESIGNTEMPLETID_NOT_NULL = "参数designTempletId不能为空！";
    // 将"参数productId不能为空"设置为静态常量PRODUCTID_NOT_NULL
    public static final String PRODUCTID_NOT_NULL = "参数productId不能为空";
    // 将"参数type不能为空"设置为静态常量TYPE_NOT_NULL
    public static final String TYPE_NOT_NULL = "参数type不能为空";
    // 将"参数groupId不能为空"设置为静态常量GROUP_NOT_NULL
    public static final String GROUPID_NOT_NULL = "参数groupId不能为空";
    // 将"该产品不存在"设置为静态常量PRODUCT_NOT_EXIST
    public static final String PRODUCT_NOT_EXIST = "该产品不存在";
    // 将"该收藏目录不存在"设置为静态常量COLLECTION_DIRECTORY_NOT_EXIST
    public static final String COLLECTION_DIRECTORY_NOT_EXIST = "该收藏目录不存在";
    // 将"该组合收藏明细已不存在"设置为静态常量COLLECTION_DETAIL_NOT_EXIST
    public static final String COLLECTION_DETAIL_NOT_EXIST = "该组合收藏明细已不存在";
    // 将"您已收藏该产品！"设置为静态常量PRODUCT_COLLECTED
    public static final String PRODUCT_COLLECTED = "您已收藏该产品！";
    // 将"OK"设置为静态常量OK
    public static final String OK = "OK";
    // 将"删除失败,缺少参数msgId!"设置为静态常量LACK_MSGID
    public static final String LACK_MSGID = "删除失败,缺少参数msgId!";
    // 将"删除失败,缺少参数Id!"设置为静态常量LACK_ID
    public static final String LACK_ID = "删除失败,缺少参数id!";
    // 将"删除失败,缺少参数userId!"设置为静态常量LACK_USERID
    public static final String LACK_USERID = "删除失败,缺少参数userId!";
    // 将"删除失败,该目录不存在"设置为静态常量DIRECTORY_NOT_EXIST
    public static final String DIRECTORY_NOT_EXIST = "删除失败,该目录不存在";
    // 将"默认目录 无法删除"设置为静态常量DIRECTORY_DEFAULT
    public static final String DIRECTORY_DEFAULT = "默认目录 无法删除";
    // 将"默认"设置为静态常量DEFAULT
    public static final String DEFAULT = "默认";
    // 将"删除成功"设置为静态常量DELETE_SUCCESS
    public static final String DELETE_SUCCESS = "删除成功";
    // 将"收藏成功"设置为静态常量COLLECTION_SUCCESS
    public static final String COLLECTION_SUCCESS = "收藏成功";
    // 将"找不到模板或样板房！"设置为静态常量TEMPLATE_CANNOT_FIND
    public static final String TEMPLATE_CANNOT_FIND = "找不到模板或样板房！";
    // 将"创建设计方案"设置为静态常量CREARE_DESIGN_PLAN
    public static final String CREARE_DESIGN_PLAN = "create_design_plan";
    // 将"手动保存设计方案"设置为静态常量MANUAL_SAVE_DESIGN_PLAN
    public static final String MANUAL_SAVE_DESIGN_PLAN = "manual_save_design_plan";
    // 将"退出保存设计方案"设置为静态常量QUIT_SAVE_DESIGN_PLAN
    public static final String QUIT_SAVE_DESIGN_PLAN = "quit_design_plan";
    // 将"修改设计方案"设置为静态常量MODIFIED_DESIGN_PLAN
    public static final String MODIFIED_DESIGN_PLAN = "update_design_plan";

    // 删除记录,记录不存在! 设置为静态常量 RECORD_DOES_NOT_EXIST
    public static final String RECORD_DOES_NOT_EXIST = "记录不存在！";
    // 参数为空时, 设置为静态常量 PARAMETER_IS_NULL
    public static final String PARAMETER_IS_NULL = "参数不能为空！";
    // 创建/修改成功时, 设置为静态常量 CREATE_SUCCESS
    public static final String CREATE_SUCCESS = "创建/修改成功";
    // 数据删除失败时, 设置为静态常量 DATA_DELETE_FAILED
    public static final String DATA_DELETE_FAILED = "数据删除失败";
    // 该记录不存在或已被删除时, 设置为静态常量 DATA_DELETE_FAILED
    public static final String EXIST_OR_HAS_BEEN_DELETED = "该记录不存在或已被删除";
    // 参数productId不能为空时, 设置为静态常量 DATA_DELETE_FAILED
    public static final String PRODUCTID_PARAMETERS = "参数productId不能为空";
    // 参数productId不能为空时, 设置为静态常量 DATA_DELETE_FAILED
    public static final String HAVE_TO_COLLECT_THE_GOODS = "已经收藏该商品";

    public static final String MEDIA_TYPE_OF_MOBILE = "MOBILE";
    public static final String MEDIA_TYPE_OF_APP = "APP";
    public static final String MEDIA_TYPE_OF_OTHER = "OTHER";

    public static int USER_TIME_OUT_HOUR = 4;

    /**
     * PC端
     */
    public static final String PC_EQUIPMENT = "pc";

    /**
     * 移动端
     */
    public static final String MOBILE_EQUIPMENT = "mobile";

    public static final String MEDIA_TYPE_WEB = "2";
    public static final String MEDIA_TYPE_PC = "3";

    //用户角色code
    public static final String RENDER_WARNING = "RENDER_WARNING";

}
