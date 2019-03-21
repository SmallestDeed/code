package com.sandu.pay.order.metadata;

public class ProductType {
	/***
	 * 序列号
	 */
   public final static String AUTHCODE_PURCHASE = "authcode_purchase";

   public final static String AUTHCODE_PURCHASE_NAME = "序列号购买";

   public final static String AUTHCODE_RENEW = "authcode_renew";

   public final static String AUTHCODE_RENEW_NAME = "序列号续费";

   public final static String AUTHCODE_DREDGE = "authcode_dredge";

   public final static String AUTHCODE_DREDGE_NAME = "序列号开通";
   /***
    * 渲染服务
    */
   public final static String RENDER = "render";
   
   /***
    * 普通渲染服务
    */
   public final static String COMMON_RENDER = "common_render";
   
   public final static String COMMON_RENDER_NAME = "照片级渲染：1024 x 576";
   /***
    * 高清渲染服务
    */
   public final static String HD_RENDER = "HD_render";
   
   public final static String HD_RENDER_NAME = "照片级渲染：1600 x 900";
   /***
    * 超高清渲染服务
    */
   public final static String UHD_RENDER = "UHD_render";
   
   public final static String UHD_RENDER_NAME = "照片级渲染：2048 x 1152";
   /***
    * 720度全景渲染服务
    */
   public final static String PANORAMA_RENDER = "panorama_render";
   
   public final static String PANORAMA_RENDER_NAME = "720度全景渲染";

   public final static String PICTURE_RENDER_NAME = "照片级渲染";

   /**
    * 多点全景渲染
    */
   public final static String ROAM_PANORAMA_RENDER = "roam720";

   public final static String ROAM_PANORAMA_RENDER_NAME = "多点全景渲染";
   /**
    * 漫游视频渲染
    */
   public final static String ROAM_VIDEO_RENDER = "video";
   
   public final static String ROAM_VIDEO_RENDER_NAME = "漫游视频渲染";
}
