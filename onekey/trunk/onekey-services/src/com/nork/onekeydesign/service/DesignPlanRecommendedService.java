package com.nork.onekeydesign.service;

import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.*;
import com.nork.onekeydesign.model.vo.UnityDesignPlanRecommended;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.system.model.ResRenderPic;

import javax.servlet.http.HttpServletRequest;

public interface DesignPlanRecommendedService {

	public int add(DesignPlanRecommended designPlanRecommended);
	
	public int update(DesignPlanRecommended designPlanRecommended);
	
	public int delete(Integer id);
	
	public DesignPlanRecommended get(Integer id);
	
	public List<DesignPlanRecommended> getList(DesignPlanRecommended designPlanRecommended);

	public int getCount(DesignPlanRecommended designPlanRecommended);
	
	
	/**
	 * 《发布界面》
	 * 已经选择 的品牌列表 接口
	 * @param request
	 * @param response
	 * @return
	 */
	public ResponseEnvelope<BaseBrand> myBrandList(String msgId, String thumbId);
	
	/**
	 * 发布方法
	 * @param model
	 * @param msgId
	 * @param loginUser
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommendedResult> recommendDesignPlan(ReleaseDesignPlanModel model, String msgId,
			LoginUser loginUser);
 

	/**
	 * 用于正式发布、测试发布、改造，复制  校验方法
	 * 需要校验的条件
	 * 	  		1.样板房  空间 是否为发布中
	 * 			2.验证设计方案产品是否发布
	 * 			3.品牌不能重复添加 
	 * 			4.是否有封面，
	 * 			5.是否有720渲染，
	 * 			6.是否有照片级渲染
	 * @param designPlan
	 * @return
	 */
	public DesignPlanRecommendedCheck planFormalIsReleaseCheck(String functionType,DesignPlanRenderScene designPlanRenderScene);

	/**
	 * 
	 * 方案推荐 列表 数据
	 * @param isMainList
	 * @param creator
	 *@param brandName
	 * @param houseType
	 * @param livingName
	 * @param areaValue
	 * @param designRecommendedStyleId
	 * @param msgId
	 * @param loginUser       @return
	 */
	public Object getPlanRecommendedList(PlanRecommendedListModel model);

	/**
	 * 删除已经选择的品牌
	 * @param msgId
	 * @param designPlanBrandId
	 * @return
	 */
	public ResponseEnvelope<DesignPlanBrand> deletedBrand(String msgId, String designPlanBrandId,LoginUser loginUser);

	/**
	 * 获取设计方案风格 接口
	 * @param msgId
	 * @param planRecommendedId
	 * @param houseType
	 * @return
	 */
	public ResponseEnvelope<BaseProductStyle> getDesignStyleList(String msgId, String thumbId,String planRecommendedId,
			String houseType);

	/**
	 * 获取设计方案风格 接口 新
	 * @param msgId
	 * @param planRecommendedId
	 * @param houseType
	 * @return
	 */
	public ResponseEnvelope<BaseProductStyle> getDesignStyleListNew(String msgId, String thumbId ,String planRecommendedId, String houseType);
	
	/**
	 * 方案推荐总条数
	 * @param designPlanBrand
	 * @param brandIds 必传
	 * @return
	 */
	public Integer getPlanRecommendedCount(DesignPlanRecommended designPlanRecommended);

	/**
	 * 方案推荐数据
	 * @param brandIds 必传
	 * @param designPlanBrand
	 * @return
	 */
	public List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanRecommended designPlanRecommended);

	/**
	 * 审核列表
	 * @param msgId
	 * @param userId
	 * @param spaceFunctionId
	 * @param planName
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommendedResult> designPlanRecommendedCheckList(PlanRecommendedListModel model);

	/**
	 * 审核接口
	 * @param msgId
	 * @param planId
	 * @param planRecommendedId
	 * @param userId
	 * @param isReleaseState
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommended> designPlanRecommendedCheck(String msgId, String planRecommendedId,
			LoginUser loginUser, String isReleaseState,String failCause);
	/**
	 * 方案推荐详情
	 * @param msgId
	 * @param planId
	 * @param planRecommendedId
	 * @return
	 */
	/*public ResponseEnvelope<DesignPlanRecommended> designPlanRecommendedDetails_old(String msgId,String planRecommendedId);*/

	/**
	 * 取消发布接口
	 * @param msgId
	 * @param loginUser
	 * @param planRecommendedId
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommended> cancelRelease(String msgId,LoginUser loginUser, String planRecommendedId);

	/**
	 * 发布用的品牌列表 接口
	 * @param msgId
	 * @return
	 */
	public ResponseEnvelope<BaseBrand> myBrandList(String msgId,BaseBrandSearch baseBrandSearch);

	/**
	 * 方案推荐详情
	 * @param msgId
	 * @param planRecommendedId
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommended> designPlanRecommendedDetails(String msgId, String planRecommendedId,LoginUser loginUser);

	/**
	 * 提交审核
	 * @param msgId
	 * @param loginUser
	 * @param planRecommendedId
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommended> submitCheck(String msgId, LoginUser loginUser,
			String planRecommendedId,String spaceAreas);

	/**
	 * 方案推荐详情 产品列表
	 * @param msgId
	 * @param planRecommendedId
	 * @param loginUser
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommendedProductResult> getDesignPlanRecommendedProductList(String msgId,
			String planRecommendedId, LoginUser loginUser);

	/**
	 * 设置封面
	 * @param msgId
	 * @param loginUser
	 * @param planRecommendedId
	 * @param picId
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommended> updateCoverPic(String msgId, LoginUser loginUser, String planRecommendedId, String picId);

	/**
	 * 修改方案推荐
	 * @param msgId
	 * @param loginUser
	 * @param planRecommendedId
	 * @param remark
	 * @param planName
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommended> updatePlanRecommended(String msgId, LoginUser loginUser,
			String planRecommendedId, String remark, String planName);

	/**
	 * 详情查看
	 * @param msgId
	 * @param smallPicId
	 * @return
	 */
	public ResponseEnvelope<ResRenderPic> detailsSee(String msgId, String picId,String detailsSeeType,LoginUser loginUser);

	/**
	 *  获取方案推荐收藏夹数据量
	 * @param designPlanRecommended
	 * @return
	 */
	public Integer getFavoritePlanRecommendedCount(DesignPlanRecommended designPlanRecommended);
	/**
	 *  获取方案推荐收藏夹列表
	 * @param designPlanRecommended
	 * @return
	 */
	public List<DesignPlanRecommendedResult> getFavoritePlanRecommendedList(
			DesignPlanRecommended designPlanRecommended);


	/**
	 *  获取空间类型面积列表
	 * @author xiaoxc
	 * @param spaceFunctionId
	 * @return
	 */
	public ResponseEnvelope getSpaceAreaList(Integer spaceFunctionId,String msgId);
	
	public List<DesignPlanRecommendedResult> getPlanRecommendedListMobile(DesignPlanRecommended designPlanRecommended) ;
	
	public ResponseEnvelope<DesignPlanRecommendedResult> getPlanRecommendedListMobile(PlanRecommendedListModel model) ;
	
	public Integer getPlanRecommendedCountMobile(DesignPlanRecommended designPlanRecommended);

	
	/**
	 * 分享发布  好 一键装修测试发布 前校验
	 * @param msgId
	 * @param thumbId
	 * @param recommendedType
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommendedResult> planFormalIsReleaseCheck(String msgId, String thumbId,String recommendedType);
	
	/**
     * 在样板房推荐页面显示推荐方案审核状态
     * @param msgId
     * @return
     */
    public ResponseEnvelope getRecommendedPlanCheckState(String msgId);

	/**
	 * 判断是否拥有设计方案拷贝 和 发布的权限
	 * @param loginUser
	 * @return
	 */
	public boolean isPermissions(LoginUser loginUser);

	/**
	 * 判断是否拥有审核 权限
	 * @param loginUser
	 * @return
	 */
	public boolean isDesignPlanCheck(LoginUser loginUser,Integer userId);

	/**
	 * 发布逻辑
	 * @param designPlan
	 * @param brandIds
	 * @return
	 */
	public boolean planIsRelease(DesignPlan designPlan, Integer isRelease,List<String> brandIds,List<String> checkIdList,HttpServletRequest request);
	/**
	 * 取消发布逻辑
	 * @param designPlan
	 * @return
	 */
	public boolean planNoRelease(DesignPlan designPlan);

	/**
	 * 方案推荐 《发布》   需要校验 1.是否有封面，是否有720渲染，是否有照片级渲染
	 * @param designPlan
	 * @param brandIdList
	 * @return
	 */
	public Map<String, String> planIsReleaseCheck(DesignPlan designPlan, List<String> brandIdList);

	/**
	 * 方案推荐 《测试发布》   需要校验 1.是否有封面，是否有720渲染，是否有照片级渲染
	 * @param designPlan
	 * @return
	 */
	public Map<String, String> planIsTestCheck(DesignPlan designPlan, List<String> brandIdList);

	/**
	 * 检测推荐方案中是否存在定制浴室柜
	 *
	 * @author huangsongbo
	 * @param planRecommendedId
	 * @return
	 */
	public boolean getIsRemmendedPlanHasDyba(Integer planRecommendedId);

	/**
	 * 查询id为${planRecommendedId}的推荐方案,存在多少个该类型的产品(大类:${bigTypeValuekey},小类:${smallTypeValuekey})
	 * @param planRecommendedId
	 * @param bigTypeValuekey
	 * @param smallTypeValuekey
	 * @return
	 */
	public int getCountByPlanIdAndProductType(Integer planRecommendedId, String smallTypeValuekey);

	public int getCountByPlanIdAndProductTypeValueList(
			Integer planRecommendedId, Integer bigTypeValue, List<Integer> smallTypeValueList);

	/**
	 * 查询样板房在推荐方案组中最匹配的方案ID
	 * @param designTemplateId	样板房ID
	 * @param recommendedPlanId	打组推荐方案的主ID
	 * @return
	 */
	Integer getBestMatchInPlanGroup(Integer designTemplateId, Integer recommendedPlanId);
	
	/**
	 * 获取小面积推荐方案id
	 * 
	 * @author huangsongbo
	 * @param planRecommendedId
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	Integer getStandbyRecommendedId(Integer planRecommendedId) throws IntelligenceDecorationException;

	/**
	 * 获取方案资源
	 * @author chenqiang
	 * @param recommendedId 方案id
	 * @return
	 * @date 2018/11/28 0028 15:07
	 */
	UnityDesignPlanRecommended getDesignPlanRecommendedInfo(Integer recommendedId);
	UnityDesignPlanRecommended getDesignPlanRecommendedInfoByMath(UnityDesignPlanRecommended unityDesignPlanRecommended);
}
