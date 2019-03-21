package com.nork.design.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.*;
import com.nork.design.model.input.FranchiserPlanListDTO;
import com.nork.design.model.input.PlanRecommendedListForFranchiserDTO;
import com.nork.design.model.output.FranchiserPlanListVO;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.system.model.ResRenderPic;

public interface DesignPlanRecommendedServiceV2 {

	public DesignPlanRecommended add(DesignPlanRecommended designPlanRecommended);
	
	public int update(DesignPlanRecommended designPlanRecommended);
	
	public int delete(Integer id);
	
	public DesignPlanRecommended get(Integer id);

	/**
	 * 根据组合id 查询组合方案列表
	 * @author chenqiang
	 * @param groupId 组合id
	 * @param isDeleted 是否删除
	 * @return
	 * @date 2018/8/13 0013 18:32
	 */
	List<DesignPlanRecommended> getGroupList(Integer groupId,Integer isDeleted);

	
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
	 * @param model
	 * @return
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
	 * 把推荐方案id回填到装修报价表
	 * @param planRecommendId
	 * @return
	 */
	int updatePlanDecoratePrice(Integer planRecommendId, Integer renderSceneId);

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
	 * @param styleId 
	 * @param brandIds 
	 * @param recommendedType 1:普通方案;2:智能方案
	 * @return
	 */
	public ResponseEnvelope<DesignPlanRecommended> submitCheck(String msgId, LoginUser loginUser,
			String planRecommendedId,String spaceAreas, String brandIds, String styleId
			/*, Integer recommendedType*/
			);

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
    ResponseEnvelope getRecommendedPlanCheckState(String msgId);

	/**
	 * 根据空间类型获取推荐方案ids
	 * @author xiaoxc
	 * @param spaceFunctionId
	 * @return
	 */
	List<Integer> findRecommendedPlanIdListBySpaceFunctionId(Integer spaceFunctionId);


	/**
	 * 获取空间布局类型
	 * @author xiaoxc
	 * @param list
	 * @param type
	 * @return
	 */
	String getLayoutType(List<String> list, String type);

	/**
	 * 查询公开方案size 定制方法
	 * 
	 * @author huangsongbo
	 * @date 2018.3.28
	 * @param dto
	 * @return
	 */
	public int getPlanRecommendedListSizeByDTO(PlanRecommendedListForFranchiserDTO dto);

	/**
	 * 查询公开方案list 定制方法
	 * 
	 * @author huangsongbo
	 * @date 2018.3.28
	 * @param dto
	 * @return
	 */
	public List<DesignPlanRecommendedResult> getPlanRecommendedListByDTO(PlanRecommendedListForFranchiserDTO dto);

	public int getFranchiserPlanListSizeByDTO(FranchiserPlanListDTO dto);

	public List<FranchiserPlanListVO> getFranchiserPlanListByDTO(FranchiserPlanListDTO dto);

	void setBrandIds(List<Integer> brandIdList, Integer designPlanRecommendedId, Long companyId, LoginUser loginUser);

	/**
	 * 验证用户有没有发布智能方案/普通方案的权限
	 * 
	 * @author huangsongbo
	 * @date 2018.4.9
	 * 
	 * @param roles
	 * @param shelfStatusList
	 * @return
	 */
	public boolean hasReleasePermission(Set<String> roles, List<String> shelfStatusList);

	/**
	 * 验证用户有没有发布智能方案/普通方案的权限
	 * 
	 * @author huangsongbo
	 * @date 2018.4.13
	 * 
	 * @param roles
	 * @param recommendedType
	 * @return
	 */
	public boolean hasReleasePermissionParamRecommendedType(Set<String> roles, Integer recommendedType);

	Integer getPlatformIdByPlatformCode(String code);

	public boolean hasReleasePermissionParamRecommendedType(Integer recommendedType);

	public ResponseEnvelope<DesignPlanRecommendedResult> decorateTestRecommend(ReleaseDesignPlanModel model, DesignPlanRenderScene designPlanRenderScene,
																			   List<String> brandIdList,String msgId,LoginUser loginUser);
	public ResponseEnvelope<DesignPlanRecommendedResult> shareRecommend(ReleaseDesignPlanModel model, DesignPlanRenderScene designPlanRenderScene,
																		List<String> brandIdList,String msgId,LoginUser loginUser);

	/**
	 * 根据推荐组合方案id 查询组合方案列表
	 * @author chenqiang
	 * @param id 主方案id
	 * @return
	 * @date 2018/8/13 0013 14:01
	 */
	List<DesignRenderGroup> getDesignPlanGroupList(Integer id);

	/**
	 * 根据idList/主方案id 修改组合方案成员方案状态
	 * @author chenqiang
	 * @param isRelease 方案状态
	 * @param idList  id集合
	 * @return
	 * @date 2018/8/13 0013 19:43
	 */
	int updateGroupDesignRelease(Integer isRelease,List<Integer> idList);

	/**
	 * 根据idList/主方案id 维护组合方案关系
	 * @author chenqiang
	 * @param groupId 主方案id
	 * @param idList  id集合
	 * @return 
	 * @date 2018/8/13 0013 19:43
	 */
	int updateGroupDesign(Integer groupId,List<Integer> idList);

	Integer getDesignPlanTargetId(Integer id);
}
