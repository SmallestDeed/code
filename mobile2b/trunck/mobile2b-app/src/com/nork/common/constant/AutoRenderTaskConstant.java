package com.nork.common.constant;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/8
 */
public class AutoRenderTaskConstant {

    //渲染状态：未渲染
    public static final int NO_RENDER_TASK = 0;
    //渲染状态：渲染中
    public static final int RENDERING_TASK = 1;
    //渲染状态：渲染成功
    public static final int RENDER_TASK_SUCCESS = 2;
    //渲染状态：渲染失败
    public static final int RENDER_TASK_FAIL = 3;

    //我的方案
    public static final Integer OPERATION_SOURCE_MY_DESIGN = 0 ;
    //推荐方案
    public static final Integer OPERATION_SOURCE_REMMONED = 1 ;

    // 0:自动渲染 1：替换
    public static final Integer TASK_TYPE_AUTO_RENDER = 0;
    public static final Integer TASK_TYPE_REPLACE = 1;

    /** 任务来源（0：通用网站 1：移动端）*/
    public static final int TASK_SOURCE_WEB = 0;
    public static final int TASK_SOURCE_APP = 1;

    /**方案空间类型（1：单空间方案，2：全屋方案，一次性装进我家逻辑，3：720全屋新逻辑）**/
    public final static Integer PLAN_SINGLE_HOUSE_TYPE = 1;
    public final static Integer PLAN_FULL_HOUSE_TYPE = 2;
    public final static Integer PLAN_FULL_HOUSE_TYPE_NEW = 3;


    //渲染任务超时类型
    public static final int TIME_OUT_FAIL_TYPE = 88;

    /**  全屋方案操作(1:创建全屋方案,2:修改全屋方案,3:修改全屋方案生成新的全屋方案)  **/
    public final static Integer FULL_HOUSE_PLAN_ACTION_CREATE = 1;
    public final static Integer FULL_HOUSE_PLAN_ACTION_UPDATE = 2;
    public final static Integer FULL_HOUSE_PLAN_ACTION_UPDATE_NEW = 3;

    /**=============================空间类型=============================**/
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

}
