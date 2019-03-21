package com.sandu.matadata;

import org.springframework.util.StringUtils;

import java.math.BigInteger;

/***
 * 缓存KEY
 * 
 * @author Administrator
 *
 */
public class CacheKeys {
   /***
    * 行政区划KEY
    */
   private final static String area="Cache:Area";
   /***
    * 行政区划-城市列表KEY
    */
   private final static String areaCity="Cache:Area:City";
   
   /***
    * 设计方案KEY
    */
   private final static String plan="Cache:Plan";
   
   /***
    * 门店KEY
    */
   private final static String shop="Cache:Shop";
   
   /***
    * 数据字典KEY
    */
   private final static String dictionary="Cache:Dictionary";
   
   /***
    * 点赞KEY
    */
   private final static String like="Cache:Like";
   
   /***
    * 收藏KEY
    */
   private final static String collection="Cache:Collection";
   
   /***
    * 评论KEY
    */
   private final static String comment="Cache:Comment";
   
   /***
    * 产品KEY
    */
   private final static String product="Cache:Product";
   
   /***
    * 产品分类KEY
    */
   private final static String productCategory="Cache:Product:Category";
   /***
    * 搜索KEY
    */
   private final static String search="Cache:Search";
   /***
    * 图片资源KEY
    */
   private final static String resPic="Cache:Res:Pic";
   /***
    * 文件资源KEY
    */
   private final static String resFile="Cache:Res:File";
 
	/***
	 * 营销活动
	 */
	private final static String activity = "Cache:Activity";
	 

	public static String getArea() {
		return area;
	}

	/***
	 * 一级产品分类KEY
	 * 
	 * @return
	 */
	public static String getFirstProductCategory() {
		return productCategory + ":First";
	}

	/***
	 * 公司产品分类KEY
	 * 
	 * @param companyId
	 * @return
	 */
	public static String productCategory(Integer companyId) {
		return productCategory + ":" + companyId;
	}

	/***
	 * 用户缓存KEY
	 */
	private final static String user = "Cache:User";

	/***
	 * 开通选装服务的城市列表KEY
	 * 
	 * @return
	 */
	public static String getOpenServiceArea() {
		return areaCity + ":OpenService";
	}

	/***
	 * 企业推荐方案资源图片KEY
	 * 
	 * @param companyId
	 * @return
	 */
	public static String getCompanyRecommendPlanPhoto(long companyId) {
		return plan + ":Recommend:Photo:" + companyId;
	}

	/***
	 * 企业产品推荐KEY
	 * 
	 * @param companyId
	 * @return
	 */
	public static String getCompanyRecommendProduct(BigInteger companyId) {
		if(companyId==null) {
			return product + ":Recommend:Company:0";
		}
		return product + ":Recommend:Company:" + companyId.intValue();
	}

	/***
	 * 门店形象图片KEY
	 * 
	 * @param shopId
	 * @return
	 */
	public static String getStoreFigurePhoto(long shopId) {
		return shop + ":Figure:Photo:" + shopId;
	}

	/***
	 * 数据字典类型KEY
	 * 
	 * @param type
	 * @return
	 */
	public static String getDictionaryType(String type) {
		return dictionary + ":" + type;
	}

	public static String getDictionaryTypeValues(String type, String values) {
		return dictionary + ":" + type + ":" + values;
	}

	/***
	 * 用户OpenID缓存KEY
	 * 
	 * @param openId
	 * @return
	 */
	public static String getUserOpenId(String openId) {
		return user + ":OpenId:" + openId;
	}

	/***
	 * 用户Token缓存KEY
	 * 
	 * @param token
	 * @return
	 */
	public static String getUserToken(String token) {
		return user + ":Token:" + token;
	}

	/***
	 * 根据收藏类别和收藏对象ID获取收藏总数的缓存KEY
	 * 
	 * @param likeType
	 * @param objId
	 * @return
	 */
	public static String getCollectionCount(int likeType, long objId) {
		return collection + ":Count:Type:" + likeType + ":ObjId:" + objId;
	}

	/***
	 * 根据收藏类别、用户ID和收藏对象ID获取收藏总数的缓存KEY
	 * 
	 * @param likeType
	 * @param objId
	 * @return
	 */
	public static String getCollectionCount(int likeType, long userId, long objId) {
		return collection + ":Count:Type:" + likeType + ":ObjId:" + objId + ":UserId:" + userId;
	}

	/***
	 * 根据评论类别和评论对象ID获取评论总数的缓存KEY
	 * 
	 * @param likeType
	 * @param objId
	 * @return
	 */
	public static String getCommentCount(int likeType, long objId) {
		return comment + ":Count:Type:" + likeType + ":ObjId:" + objId;
	}

	/***
	 * 根据评论类别、用户ID和评论对象ID获取评论总数的缓存KEY
	 * 
	 * @param likeType
	 * @param objId
	 * @return
	 */
	public static String getCommentCount(int likeType, long userId, long objId) {
		return comment + ":Count:Type:" + likeType + ":ObjId:" + objId + ":UserId:" + userId;
	}

	/***
	 * 根据点赞类别和点赞对象ID获取点赞总数的缓存KEY
	 * 
	 * @param likeType
	 * @param objId
	 * @return
	 */
	public static String getLikeCount(int likeType, long objId) {
		return like + ":Count:Type:" + likeType + ":ObjId:" + objId;
	}

	/***
	 * 产品分类KEY
	 * 
	 * @return
	 */
	public static String getProductCategory() {
		return productCategory;
	}

	/***
	 * 根据点赞类别、用户ID和点赞对象ID获取点赞总数的缓存KEY
	 * 
	 * @param likeType
	 * @param objId
	 * @return
	 */
	public static String getLikeCount(int likeType, long userId, long objId) {
		return like + ":Count:Type:" + likeType + ":ObjId:" + objId + ":UserId:" + userId;
	}

	/***
	 * 获取店铺推荐KEY
	 * 
	 * @return
	 */
	public static String getShopRecommend() {
		return shop + ":Recommend:List";
	}

	/***
	 * 获取热门搜索关键词KEY
	 * 
	 * @return
	 */
	public static String getHotSearch() {
		return search + ":Hot";
	}

	/***
	 * 根据用户ID获取用户历史搜索KEY
	 * 
	 * @param userId
	 * @return
	 */
	public static String getUserHistorySearch(long userId) {
		return search + ":User:" + userId;
	}

	/***
	 * 获取用户领取优惠券总量KEY
	 * 
	 * @param couponId
	 * @return
	 */
	public static String getUserReceiveCouponCount(long couponId) {
		return activity + ":Coupon:UserReceiveCount:Id:" + couponId;
	}

   /***
    * 通过资源ids获取图片地址
    * @param ids
    * @return
    */
   public static String getPicPathByIds(String ids) {
      return resPic+":ids:"+ids;
   }

	/***
	 * 店铺列表方案数量KEY
	 *
	 * @param companyId
	 * @return
	 */
	public static String getCompanyShopRecommendCount(String platformCode, Integer companyId) {
		if (StringUtils.isEmpty(platformCode) || companyId == null) {
			return shop + ":RecommendPlan:Count:0";
		}
		return shop + ":RecommendPlan:Count:" + platformCode + ":" + companyId.longValue();
	}

	/***
	 * 店铺列表方案列表KEY
	 *
	 * @param companyId
	 * @return
	 */
	public static String getCompanyShopRecommendList(String platformCode, Integer companyId) {
		if (StringUtils.isEmpty(platformCode) || companyId == null) {
			return shop + ":RecommendPlan:List:0";
		}
		return shop + ":RecommendPlan:List:" + platformCode + ":" + companyId.longValue();
	}


	/***
	 * 企业缓存KEY
	 */
	private final static String baseCompany = "Cache:BaseCompany";

	/***
	 * 公司KEY
	 *
	 * @param companyId
	 * @return
	 */
	public static String getCompanyId(Integer companyId) {
		return baseCompany + ":" + companyId;
	}

}
