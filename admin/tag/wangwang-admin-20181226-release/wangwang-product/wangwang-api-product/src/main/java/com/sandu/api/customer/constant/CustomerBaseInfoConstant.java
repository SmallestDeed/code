package com.sandu.api.customer.constant;

/**
 * @author sandu-lipeiyuan
 */
public class CustomerBaseInfoConstant {
    /**
     * 经营类型:1:厂商、2:经销商、3:门店、4:设计公司、5:装修公司、6:设计师、7:工长（施工单位）
     */
    public static final Integer BUSINESS_TYPE_MANUFACTURER = 1;

    /**
     * 经营类型:1:厂商、2:经销商、3:门店、4:设计公司、5:装修公司、6:设计师、7:工长（施工单位）
     */
    public static final Integer BUSINESS_TYPE_DEALER = 2;
    
    /**
     * 经营类型:1:厂商、2:经销商、3:门店、4:设计公司、5:装修公司、6:设计师、7:工长（施工单位）9、独立经销商
     */
    public static final Integer INDENPENT_BUSINESS_TYPE = 9;

    /**
     * "分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)"
     */
    public static final Integer ALOT_STATUS_ALLOCATED = 1;

    /**
     * "分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)"
     */
    public static final Integer ALOT_STATUS_FOLLOW = 2;
    /**
     * "分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)"
     */
    public static final Integer ALOT_STATUS_COMPLETE = 3;
    /**
     * "分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)"
     */
    public static final Integer ALOT_STATUS_INVAIN = 4;

    /**
     * "分配类型(0-自动;1-手动)"
     */
    public static final Integer ALOT_TYPE_AUTO = 0;
    /**
     * "分配类型(0-自动;1-手动)"
     */
    public static final Integer ALOT_TYPE_HAND = 1;
}