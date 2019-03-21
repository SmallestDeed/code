package com.nork.design.common;

public final class DesignPlanRelease {
    /**
     * 未发布
     */
    public static final Integer stateless_null = null;   
    /**
     * 未发布
     */
    public static final int stateless = 0;   
    /**
     * 发布中
     */
    public static final int is_release = 1;  
    /**
     * 测试发布中
     */
    public static final int is_test = 2; 
    /**
     *  待审核
     */
    public static final int waiting_check = 3;  
    /**
     *  审核失败
     */
    public static final int failure_check = 4;  
}
