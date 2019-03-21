package com.sandu.designplan.model;

public class RecommendedDecorateState {

    /**
     * 未发布
     */
    public static final int NO_RELEASE = 0;
    /**
     * 发布中
     */
    public static final int IS_RELEASEING = 1;
    /**
     * 测试发布中
     */
    public static final int IS_TEST_RELEASE = 2;
    /**
     * 待审核
     */
    public static final int WAITING_CHECK_RELEASE = 3;
    /**
     * 审核失败
     */
    public static final int FAILURE_CHECK_RELEASE = 4;
}
