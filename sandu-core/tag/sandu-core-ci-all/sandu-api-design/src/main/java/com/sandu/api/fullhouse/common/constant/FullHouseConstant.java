package com.sandu.api.fullhouse.common.constant;

/**
 * created by zhangchengda
 * 2018/8/17 9:41
 * 全屋方案模块中使用的常量
 */
public class FullHouseConstant {
    /************************ 全屋方案相关 ************************/
    // 方案来源类型-内部制作
    public static final int FULL_HOUSE_SOURCE_TYPE_INTERNAL_PRODUCTION = 1;
    // 方案来源类型-装进我家
    public static final int FULL_HOUSE_SOURCE_TYPE_DECORATION_MY_HOME = 2;
    // 公开状态-已公开
    public static final int FULL_HOUSE_OPEN_STATE_YES = 1;
    // 公开状态-未公开
    public static final int FULL_HOUSE_OPEN_STATE_NO = 0;
    // 初始方案版本
    public static final int FULL_HOUSE_VERSION_DEFAULT = 1;
    // 是否删除-未删除
    public static final int FULL_HOUSE_IS_DELETED_NO = 0;
    // 是否删除-已删除
    public static final int FULL_HOUSE_IS_DELETED_YES = 1;

    /************************ 方案风格编码 ************************/
    // 客餐厅
    public static final String DESIGN_PLAN_STYLE_CODE_LIVING_DINING_ROOM = "kecanting";
    // 卫生间
    public static final String DESIGN_PLAN_STYLE_CODE_TOILET = "toilet";
    // 卧室
    public static final String DESIGN_PLAN_STYLE_CODE_BEDROOM = "bedroom";
    // 厨房
    public static final String DESIGN_PLAN_STYLE_CODE_KITCHEN = "kitchen";
    // 书房
    public static final String DESIGN_PLAN_STYLE_CODE_SCHOOLROOM = "Office";
    // 衣帽间
    public static final String DESIGN_PLAN_STYLE_CODE_CLOAKROOM = "cloakroom";
    // 客卧房
    public static final String DESIGN_PLAN_STYLE_CODE_QUEST_BEDROOM = "quest bedroom";
    // 复式空间
    public static final String DESIGN_PLAN_STYLE_CODE_DOUBLE_SPACE = "double space";
    // 全屋
    public static final String DESIGN_PLAN_STYLE_CODE_FULL_HOUSE = "fullroom";

    /************************ 空间类型 ************************/
    // 全屋
    public static final int SPACE_TYPE_FULL_HOUSE = 0;
    // 客厅
    public static final int SPACE_TYPE_LIVING_ROOM = 1;
    // 餐厅
    public static final int SPACE_TYPE_DINING_ROOM = 2;
    // 客餐厅
    public static final int SPACE_TYPE_LIVING_DINING_ROOM = 3;
    // 卧室
    public static final int SPACE_TYPE_BEDROOM = 4;
    // 厨房
    public static final int SPACE_TYPE_KITCHEN = 5;
    // 卫生间
    public static final int SPACE_TYPE_TOILET = 6;
    // 书房
    public static final int SPACE_TYPE_SCHOOLROOM = 7;
    // 衣帽间
    public static final int SPACE_TYPE_CLOAKROOM = 8;

    /************************ 优先级 ************************/
    // 最高优先级
    public static final int FULL_HOUSE_DETAIL_PRIORITY_LEVEL_HEIGHEST = 10;
    // 最低优先级
    public static final int FULL_HOUSE_DETAIL_PRIORITY_LEVEL_LOWEST = 1;
    // 优先级改变步伐
    public static final int FULL_HOUSE_DETAIL_PRIORITY_LEVEL_STEP = 1;
}
