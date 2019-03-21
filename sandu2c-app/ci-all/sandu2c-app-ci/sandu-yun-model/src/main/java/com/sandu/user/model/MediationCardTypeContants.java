package com.sandu.user.model;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 6:26 2018/8/30 0030
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/8/30 0030PM 6:26
 */
public class MediationCardTypeContants {

    //证件类型,身份证:1,户口簿:2,工作证:3,驾驶证:4',

    public static final int  ID_CARD  = 1;

    public static final int  RESIDENCE_CARD = 2;

    public static final int  EMPLOYEE_CARD = 3;

    public static final int  DRIVER_CARD = 4;



    public static final int  PENDING_APPLY = 0;

    public static final int  PENDING_AUDIT = 1;

    public static final int  ALREADY_AUTHORIZE= 2;

    public static final int  AUTHORIZE_FAILURE = 3;
}
