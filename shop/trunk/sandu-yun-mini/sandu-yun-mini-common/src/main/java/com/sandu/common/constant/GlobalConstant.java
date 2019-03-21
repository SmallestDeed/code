package com.sandu.common.constant;

/***
 * 全局常量
 * @author Administrator
 *
 */
public class GlobalConstant {
   /***
    * 默认缓存过期时间(10分钟)
    */
   public static final int CACHE_SECONDS=10*60;
   /***
    * 默认较长缓存过期时间(30分钟)
    */
   public static final int LONG_CACHE_SECONDS=30*60;
   /***
    * 默认很长缓存过期时间(1小时)
    */
   public static final int LONG_LONG_CACHE_SECONDS=60*60;
   
   /***
    * TOKEN缓存过期时间(2小时)
    */
   public static final int TOKEN_CACHE_SECONDS=2*60*60;
   
   /***
    * 最大新人优惠券张数
    */
   public static final int MAX_AVAILABLE_NEW_MEMBER_COUPON_COUNT=2;
}
