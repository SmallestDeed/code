package com.nork.design.service;

import java.util.List;
import java.util.Map;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlanBrand;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRecommendedCheck;
import com.nork.design.model.DesignPlanRecommendedProductResult;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.PlanRecommendedListModel;
import com.nork.design.model.ReleaseDesignPlanModel;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.system.model.ResRenderPic;

public interface DesignPlanRecommendedServiceV2 {

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
	
	public Integer getAllFavoritePlanRecommendedCount(DesignPlanRecommended designPlanRecommended);
	/**
	 *  获取方案推荐收藏夹列表
	 * @param designPlanRecommended
	 * @return
	 */
	public List<DesignPlanRecommendedResult> getFavoritePlanRecommendedList(
			DesignPlanRecommended designPlanRecommended);
	
	public List<DesignPlanRecommendedResult> getAllFavoritePlanRecommendedList(
			DesignPlanRecommended designPlanRecommended);


	/**
	 *  获取空间类型面积列表
	 * @author xiaoxc
	 * @param spaceFunctionId
	 * @return
	 */
	public ResponseEnvelope getSpaceAreaList(Integer spaceFunctionId,String msgId);
	
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

}
