package com.sandu.pay.order.metadata;

/**
 * 区分不同商品的业务类型
 *
 * @Author yzw
 * @Date 2018/1/16 16:21
 */
public class PayModelConstantType {

    // 业务类型start
    /**
     * 渲染的业务类型(需要购买)
     */
    public final static String RENDER_TYPE = "renderType";

    /**
     * c端包年包月套餐 =>渲染的业务类型(需要购买)
     */
    public final static String RENDER_TYPE_2C = "renderType2c";

    /**
     * 渲染的业务类型（绑定序列号赠送）
     */
    public final static String RENDER_TYPE_GIVE = "renderTypeGive";

    /**
     * 渲染的业务类型（经销商，需要购买）
     */
    public final static String RENDER_TYPE_FRANCHISER = "renderTypeFranchiser";

    /**
     * 渲染的业务类型（经销商，绑定序列号赠送）
     */
    public final static String RENDER_TYPE_GIVE_FRANCHISER = "renderTypeGiveFranchiser";

    public final static String PACKAGE_YEAR = "包年套餐";

    public final static String PACKAGE_MONTH = "包月套餐";

    public final static String PACKAGE_GIVE = "绑定赠送3个月";

    // 业务类型start

    // 赠送小于等于7天开始提示
    public final static int GIVE_DATA_NUMBER = 7;

    // 包月小于等于5天提示
    public final static int CONFIG_MONTH = 5;

    // 包年小于等于30天提示
    public final static int CONFIG_YEAR = 30;

    // 包年包月渲染业务名称
    public final static String RENDER_BIZ_NAME = "包年包月渲染业务";

    // 第一次点击渲染时候小于5天的提示（免费，包月，包年剩余时长是否：0<=剩余时长<=5）
    public final static int FIRST_CHECK_DAY = 5;

    //没权限
    public final static String NOT_PERMISSION_STATE = "0";

    //有赠送权限
    public final static String GIVE_STATE = "1";

    //有包月权限
    public final static String PACKAGE_MONTH_STATE = "2";

    //有包年权限
    public final static String PACKAGE_YEAR_STATE = "3";

    //用户免费的权限
    public final static String FREE_STATE = "4";

    //表示有多个了（比如：赠送，包月，包年）
    public final static String FIVE_STATE = "5";
    // 用户渲染权限状态end

    /**
     * 度币共享渲染业务
     */
    public final static String INTEGRAL_RENDER_BIZ_NAME = "度币共享渲染业务";

    /**
     * 使用度币共享生成订单的时候，在产品后面拼接该字符串
     */
    public final static String INTEGRAL_RENDER_PRODUCT_NAME = "(度币共享)";

    // 范围类型 start
    /**
     * 个人购买
     */
    public final static Integer RANGER_TYPE_PERSONAGE = 0;

    /**
     * 经销商(购买)
     */
    public final static Integer RANGE_TYPE_FRANCHISER = 3;
    // 范围类型 end

    //时间类型start
    /**
     * 年
     */
    public final static String TIME_TYPE_YEAR = "1";

    /**
     * 月
     */
    public final static String TIME_TYPE_MOUTH = "0";
    //时间类型start

    /**
     * 表示所有用户
     */
    public final static String BIZ_TYPE = "all_user";

    /**
     * 表示渲染
     */
    public final static String BUSINESS_TYPE_RENDER = "0";

    /**
     * 表示户型
     */
    public final static String BUSINESS_TYPE_HOUSE = "1";
}
