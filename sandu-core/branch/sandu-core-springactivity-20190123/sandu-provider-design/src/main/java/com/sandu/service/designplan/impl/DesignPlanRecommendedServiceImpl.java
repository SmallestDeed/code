package com.sandu.service.designplan.impl;

import com.google.gson.Gson;
import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.common.Utils;
import com.sandu.api.base.common.constant.CompanyTypeEnum;
import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.designplan.common.constants.DesignPlanConstants;
import com.sandu.api.designplan.input.PlanRecommendedQuery;
import com.sandu.api.designplan.model.DesignPlanRecommended;
import com.sandu.api.designplan.model.DesignPlanSummaryInfo;
import com.sandu.api.designplan.model.ResRenderPic;
import com.sandu.api.designplan.output.DesignPlanRecommendedVo;
import com.sandu.api.designplan.service.DesignPlanLikeService;
import com.sandu.api.designplan.service.DesignPlanRecommendedService;
import com.sandu.api.designplan.service.ResRenderPicService;
import com.sandu.service.designplan.dao.DesignPlanRecommendedDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by kono on 2018/6/2 0002.
 */
@Service("designPlanRecommendedService")
public class DesignPlanRecommendedServiceImpl implements DesignPlanRecommendedService {

    //Json转换类
    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[方案推荐服务]:";
    private static Logger logger = LoggerFactory.getLogger(DesignPlanRecommendedServiceImpl.class);
    //三度公司Id
    private static final Integer SANDU_COMPANY_ID = 2501;
    @Autowired
    private DesignPlanRecommendedDao designPlanRecommendedDao;
    
    @Autowired
    private ResRenderPicService resRenderPicService;
    
    @Autowired
    private DesignPlanLikeService designPlanLikeService;

    @Override
    public ResponseEnvelope getPlanRecommendedList(PlanRecommendedQuery query, String companyCode, BaseCompany baseCompany) {
        if (null == query) {
            return new ResponseEnvelope(false, "获取方案推荐列表数据失败,PlanRecommendedQuery is null!");
        }
        LoginUser loginUser = query.getLoginUser();
        // 设置查询条件对象
        DesignPlanRecommended designPlanRecommended = setModelQuery(query, baseCompany, companyCode, loginUser);


        // 查询列表
        List<DesignPlanRecommendedVo> list = null;
        List<Integer> designPlanIds = new ArrayList<>();
        Integer total = this.getPlanRecommendedCount(designPlanRecommended);
        if (total != null && total.intValue() > 0) {
            list = this.getPlanRecommendedList(designPlanRecommended);
            List <Integer> recommendedIds = new ArrayList <Integer>();
            for (DesignPlanRecommendedVo result : list) {
                recommendedIds.add(result.getPlanRecommendedId());
            }
            List<ResRenderPic> resPicList = resRenderPicService.getResRenderPicListByRecommendedIds(recommendedIds);
            designPlanIds =  designPlanRecommendedDao.selectDesignPlanIdsFromRecord(loginUser.getId());
            for (DesignPlanRecommendedVo result : list) {
                for(ResRenderPic res : resPicList){
                    if(res!=null && res.getPlanRecommendedId().intValue() == result.getPlanRecommendedId().intValue()){
                        result.setResRenderPicPath(res.getPicPath());
                    }
                }
                if (Objects.equals(result.getChargeType(),1) && !designPlanIds.contains(result.getPlanRecommendedId())){
                    result.setCopyRightPermission(1);
                }else{
                    //免费方案,无需购买版权
                    result.setCopyRightPermission(0);
                }
                //判断用户是否已经购买过方案版权
                if (designPlanIds.contains(result.getPlanRecommendedId())){
                    result.setHavePurchased(1);
                }else{
                    result.setHavePurchased(0);
                }
                String [] planDecoratePriceList = new String[0];
                result.setPlanDecoratePriceList(planDecoratePriceList);

                //从缓存中获取方案点赞收藏数量信息、
                //从缓存中获取用户对方案是否点赞，是否收藏
                DesignPlanSummaryInfo summaryInfo =
                        designPlanLikeService.getPlanInfoOfCache(loginUser.getId(), result.getPlanRecommendedId());
                if (null != summaryInfo) {
                    if (null != summaryInfo.getLikeNum()) {
                        result.setLikeNum(summaryInfo.getLikeNum());
                    }
                    if (null != summaryInfo.getCollectNum()) {
                        result.setCollectNum(summaryInfo.getCollectNum());
                    }
                    if (null != summaryInfo.getIsLike()) {
                        result.setIsLike(summaryInfo.getIsLike());
                    }
                    if (null != summaryInfo.getIsFavorite()) {
                        result.setIsFavorite(summaryInfo.getIsFavorite());
                    }
                    if (null != summaryInfo.getViewNum()){
                        result.setViewNum(summaryInfo.getViewNum());
                    }
                }
            }
        }
        logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据完成:total:{}, list:{}.", total, GSON.toJson(list));
        return new ResponseEnvelope(true, total==null ? 0 : total, list);
    }

    /**
     * 方案推荐总条数
     *
     * @return
     */
    @Override
    public Integer getPlanRecommendedCount(DesignPlanRecommended designPlanRecommended) {
        if (designPlanRecommended == null) {
            return 0;
        }
		/*是方案管理员 必须要传空间类型*/
        if ("yes".equals(designPlanRecommended.getCheckAdministrator())) {
            if (designPlanRecommended.getSpaceFunctionIds() == null || designPlanRecommended.getSpaceFunctionIds().size() <= 0) {
                return 0;
            }
		/*非方案管理员 必须要传品牌id*/
        }
        return designPlanRecommendedDao.getPlanRecommendedCount(designPlanRecommended);
    }

    /**
     * 方案推荐数据
     *
     * @return
     */
    @Override
    public List<DesignPlanRecommendedVo> getPlanRecommendedList(DesignPlanRecommended designPlanRecommended) {
        if (designPlanRecommended == null) {
            return null;
        }
        /*是方案管理员 必须要传空间类型*/
        if ("yes".equals(designPlanRecommended.getCheckAdministrator())) {
            if (designPlanRecommended.getSpaceFunctionIds() == null || designPlanRecommended.getSpaceFunctionIds().size() <= 0) {
                return null;
            }
        }
        return designPlanRecommendedDao.getPlanRecommendedList(designPlanRecommended);
    }

    @Override
    public Integer recommendedPlanCount(PlanRecommendedQuery query, String companyCode, BaseCompany baseCompany) {
        if (null == query) {
            return 0;
        }
        LoginUser loginUser = query.getLoginUser();
        // 设置查询条件对象
        DesignPlanRecommended designPlanRecommended = setModelQuery(query, baseCompany, companyCode, loginUser);

        return this.getPlanRecommendedCount(designPlanRecommended);
    }

    /**
     * 转换查询条件对象
     * @param query
     * @return
     */
    private DesignPlanRecommended setModelQuery(PlanRecommendedQuery query, BaseCompany baseCompany, String companyCode, LoginUser loginUser) {
        DesignPlanRecommended designPlanRecommended = new DesignPlanRecommended();
        String houseType = query.getSpaceType();
        String livingName = query.getLivingName();
        String areaValue = query.getSpaceArea();
        String designRecommendedStyleId = query.getDesignPlanStyleId();
        String displayType = query.getDisplayType();
        String creator = query.getCreator();//搜索条件：创建者
        String brandName = query.getBrandName();//搜索条件：品牌
        Integer limit = query.getLimit();
        Integer start = query.getStart();
        Integer isSortByReleaseTime = query.getIsSortByReleaseTime();
        Integer isSortByRenderCount = query.getIsSortByRenderCount();
        Integer companyId = query.getCompanyId();
        Integer designerId = query.getDesignerId();
        List<Integer> companyIds = new ArrayList<>();
        if (baseCompany != null ) {
            companyId = baseCompany.getId();
            if (CompanyTypeEnum.MANUFACTURER.getValue().equals(baseCompany.getBusinessType())) {
                companyIds.add(companyId);
            } else if (CompanyTypeEnum.FRANCHISER.getValue().equals(baseCompany.getBusinessType())) {
                companyIds.add(baseCompany.getPid());
                designPlanRecommended.setCompanyId(baseCompany.getPid());
            } else {
                companyIds.add(companyId);
                if (!SANDU_COMPANY_ID.equals(companyId)) {
                    //非厂商经销商需查三度公司+本公司方案(先写死后续处理)
                    companyIds.add(SANDU_COMPANY_ID);
                    //包含三度企业ID时需本公司方案在前面，排序使用
                    designPlanRecommended.setCompanyId(companyId);
                }
            }
        }
        designPlanRecommended.setCompanyIds(companyIds);

        /* 查询 */
        designPlanRecommended.setDisplayType(displayType);
        if (DesignPlanConstants.FUNCTION_TYPE_DECORATE.equals(displayType)) {/* 1代表祝列表。其他代表一键装修处的小列表。小列表只能查询支持一件装修的数据 */
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(DesignPlanConstants.IS_RELEASEING);
        }
        if (DesignPlanConstants.FUNCTION_TYPE_DRAGDECORATE.equals(displayType)) {
            //运营网站用户仅能看到已发布推荐方案
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(DesignPlanConstants.IS_RELEASEING);
        }
        if (DesignPlanConstants.FUNCTION_TYPE_TEST.equals(displayType)) {
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(DesignPlanConstants.IS_TEST_RELEASE);
        }
        if (DesignPlanConstants.FUNCTION_TYPE_CHECK.equals(displayType)) {
//            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
//            designPlanRecommended.setIsRelease(RecommendedDecorateState.WAITING_CHECK_RELEASE);
//            /*判断用户是什么类型管理员*/
//            List<Integer> spaceFunctionIds = null;
//            spaceFunctionIds = this.designPlanRecommendedCheckType(loginUser.getId());
//            if (spaceFunctionIds == null || spaceFunctionIds.size() <= 0) {
//                return new ResponseEnvelope(false, "无权限！");
//            }
//            designPlanRecommended.setSpaceFunctionIds(spaceFunctionIds);
//            designPlanRecommended.setCheckAdministrator("yes");/*是方案审核管理员*/
        }
        if (DesignPlanConstants.FUNCTION_TYPE_PUBLIC.equals(displayType) || StringUtils.isEmpty(displayType)) {
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_SHARE);
            designPlanRecommended.setIsRelease(DesignPlanConstants.IS_OPEN);
        }
        if (DesignPlanConstants.FUNCTION_TYPE_MOBILE.equals(displayType)) {
            designPlanRecommended.setIsRelease(DesignPlanConstants.IS_OPEN);
        }
        // 支持查询一键方案和样板间方案
        if (DesignPlanConstants.FUNCTION_TYPE_SUPPORTBOTH.equals(displayType)) {
            List<Integer> types = new ArrayList<>();
            types.add(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            types.add(DesignPlanConstants.RECOMMENDED_TYPE_SHARE);
            designPlanRecommended.setRecommendedTypes(types);
            designPlanRecommended.setIsRelease(DesignPlanConstants.IS_RELEASEING);
        }
        //根据时间排序
        if(null!=isSortByReleaseTime&&null==isSortByRenderCount) {
            designPlanRecommended.setIsSortByReleaseTime(isSortByReleaseTime);
        }
        //根据渲染次数排序
        if(null==isSortByReleaseTime&&null!=isSortByRenderCount) {
            designPlanRecommended.setIsSortByRenderCount(isSortByRenderCount);
        }
        //根据平台过滤
        if(query.getPlatformId()!=null&&query.getPlatformId()>0) {
            designPlanRecommended.setPlatformId(query.getPlatformId());
        }

        if (StringUtils.isNotEmpty(brandName)) { /*品牌名*/
            designPlanRecommended.setBrandName(brandName.trim());
        }
        if (StringUtils.isNotEmpty(creator)) { /*创建者*/
            designPlanRecommended.setCreator(creator.trim());
        }
        if (StringUtils.isNotEmpty(houseType) && (!"null".equals(houseType))) { /*空间功能类型 */
            designPlanRecommended.setSpaceFunctionId(Integer.parseInt(houseType));
        }
        if (StringUtils.isNotEmpty(areaValue)) {
            designPlanRecommended.setAreaValue(areaValue);
        }
        if (StringUtils.isNotEmpty(livingName)) { /* 小区名称 */
            designPlanRecommended.setLivingName(livingName);
        }
        if (StringUtils.isNotEmpty(designRecommendedStyleId)) { /* 推荐方案风格 */
            designPlanRecommended.setDesignRecommendedStyleId(Integer.parseInt(designRecommendedStyleId));
        }

        //空间形状
        if (null != query.getSpaceShape()) { /* 小区名称 */
            designPlanRecommended.setSpaceShape(query.getSpaceShape());
        }

        if (limit != null) {
            designPlanRecommended.setLimit(limit);
        }
        if (start != null) {
            designPlanRecommended.setStart(start);
        }
        designPlanRecommended.setUserId(loginUser.getId());
        designPlanRecommended.setShopId(query.getShopId());
        // 设置设计师Id
        //designPlanRecommended.setDesignPlanId(designerId);

        /*公司为三度云享家展示平台所有已发布的一键方案+公开方案*/
//        if(companyCode.equals(baseCompany.getCompanyCode())){
//            designPlanRecommended.setBrandIds(null);
//            designPlanRecommended.setCompanyId(null);
//            designPlanRecommended.setPlatformId(null);
//            designPlanRecommended.setRecommendedType(null);
//            List<Integer> types = new ArrayList<>();
//            types.add(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
//            types.add(DesignPlanConstants.RECOMMENDED_TYPE_SHARE);
//            designPlanRecommended.setRecommendedTypes(types);
//            designPlanRecommended.setIsRelease(DesignPlanConstants.IS_RELEASEING);
//        }

        return designPlanRecommended;
    }

	@Override
	public List<DesignPlanRecommended> getListByFullHouseId(Integer fullHousePlanId) throws BizExceptionEE {
		String throwLogPrefix = "查找全屋方案包明细失败";
		
		// 参数验证 ->start
		if(fullHousePlanId == null) {
			String logMes = "params error: fullHousePlanId = null";
			logger.error(CLASS_LOG_PREFIX + logMes);
    		throw new BizExceptionEE(throwLogPrefix + "(" + logMes + ")");
		}
		// 参数验证 ->end
		
		return designPlanRecommendedDao.selectByFullHouseId(fullHousePlanId);
	}

	@Override
	public Map<Integer, List<DesignPlanRecommended>> getDesignPlanRecommendedMap(
			List<DesignPlanRecommended> designPlanRecommendList) throws BizExceptionEE {
		String throwLogPrefix = "整理推荐方案列表逻辑异常";
		
		// 参数验证 ->start
		if(Utils.isEmpty(designPlanRecommendList)) {
			String logMes = "params error: Utils.isEmpty(designPlanRecommendList) = true";
			logger.error(CLASS_LOG_PREFIX + logMes);
			throw new BizExceptionEE(throwLogPrefix + "(" + logMes + ")");
		}
		// 参数验证 ->end
		
		Map<Integer, List<DesignPlanRecommended>> returnMap = new HashMap<Integer, List<DesignPlanRecommended>>();
		
		// 按空间类型整理map ->start
		for(DesignPlanRecommended designPlanRecommended : designPlanRecommendList) {
			if(designPlanRecommended.getPriorityLevel() == null) {
				designPlanRecommended.setPriorityLevel(9999);
			}
			
			Integer houseTypeValue = designPlanRecommended.getSpaceFunctionId();
			if(returnMap.containsKey(houseTypeValue)) {
				List<DesignPlanRecommended> list = returnMap.get(houseTypeValue);
				if(list == null) {
					list = new ArrayList<DesignPlanRecommended>(Arrays.asList(designPlanRecommended));
				}else {
					list.add(designPlanRecommended);
				}
				returnMap.put(houseTypeValue, list);
			}else {
				List<DesignPlanRecommended> list = new ArrayList<DesignPlanRecommended>(Arrays.asList(designPlanRecommended));
				returnMap.put(houseTypeValue, list);
			}
		}
		// 按空间类型整理map ->end
		
		// 按优先级对map的每个value(推荐方案list)排序 ->start
		for(Integer key : returnMap.keySet()) {
			List<DesignPlanRecommended> list = returnMap.get(key);
			if(Utils.isNotEmpty(list)) {
				Collections.sort(list, new Comparator<DesignPlanRecommended>() {

					@Override
					public int compare(DesignPlanRecommended o1, DesignPlanRecommended o2) {
						return o1.getPriorityLevel() - o2.getPriorityLevel();
					}
					
				});
			}
		}
		// 按优先级对map的每个value(推荐方案list)排序 ->end
		
		return returnMap;
	}

	@Override
	public Integer getMatchId(Integer areaValue, Integer groupPrimaryId) throws BizExceptionEE {
		String throwLogPrefix = "获取最匹配的推荐方案失败";
		
		// 参数验证 ->start
		if(areaValue == null) {
			String logMes = "params error: areaValue = null";
			logger.error(CLASS_LOG_PREFIX + logMes);
			throw new BizExceptionEE(throwLogPrefix + "(" + logMes + ")");
		}
		if(groupPrimaryId == null) {
			String logMes = "params error: groupPrimaryId = null";
			logger.error(CLASS_LOG_PREFIX + logMes);
			throw new BizExceptionEE(throwLogPrefix + "(" + logMes + ")");
		}
		// 参数验证 ->end
		
		List<DesignPlanRecommended> designPlanRecommendedList = this.getListByGroupPrimaryId(groupPrimaryId);
		if(Utils.isEmpty(designPlanRecommendedList)) {
			return groupPrimaryId;
		}
		
		// 整理出一个key = 面积value, value = DesignPlanRecommended 的map ->start
		
		// key = 空间面积value, value = DesignPlanRecommended 的map
		Map<Integer, DesignPlanRecommended> designPlanRecommendedMap = new HashMap<Integer, DesignPlanRecommended>();
		// 空间面积list
		List<Integer> areaValueList = new ArrayList<Integer>();
		
		for(DesignPlanRecommended designPlanRecommended : designPlanRecommendedList) {
			if(StringUtils.isNotEmpty(designPlanRecommended.getApplySpaceAreas())) {
				List<Integer> areaValueListTemp = Utils.getIntegerListFromString(designPlanRecommended.getApplySpaceAreas());
				for(Integer areaValueItem : areaValueListTemp) {
					designPlanRecommendedMap.put(areaValueItem, designPlanRecommended);
					areaValueList.add(areaValueItem);
				}
			}
		}
		
		Collections.sort(areaValueList);
		// 整理出一个key = 面积value, value = DesignPlanRecommended 的map ->end
		
		// get 最匹配的推荐方案(面积相等/最接近且小于) ->start
		if(areaValueList.indexOf(areaValue) != -1) {
			// 有相同面积的推荐方案
			return designPlanRecommendedMap.get(areaValue).getId();
		}else {
			// 没有相同面积的推荐方案,取面积小于且最接近的面积对应的推荐方案
			areaValueList.add(areaValue);
			Collections.sort(areaValueList);
			int index = areaValueList.indexOf(areaValue);
			if(index == 0) {
				// 没有更大的面积了,取面积中最小的
				return designPlanRecommendedMap.get(areaValueList.get(1)).getId();
			}else {
				return designPlanRecommendedMap.get(areaValueList.get(index - 1)).getId();
			}
		}
		// get 最匹配的推荐方案(面积相等/最接近且小于) ->end
	}

	@Override
	public List<DesignPlanRecommended> getListByGroupPrimaryId(Integer groupPrimaryId) throws BizExceptionEE {
		String throwLogPrefix = "查找方案包中的推荐方案明细失败";
		
		// 参数验证 ->start
		if(groupPrimaryId == null) {
			String logMes = "params error: groupPrimaryId = null";
			logger.error(CLASS_LOG_PREFIX + logMes);
			throw new BizExceptionEE(throwLogPrefix + "(" + logMes + ")");
		}
		// 参数验证 ->end
		
		return designPlanRecommendedDao.getListByGroupPrimaryId(groupPrimaryId);
	}
	

    /**
     * created by zhangchengda
     * 2018/8/17 14:57
     * 通过主键查询推荐方案
     * @param id 主键
     * @return 返回推荐方案
     */
    @Override
    public DesignPlanRecommended selectByPrimaryKey(Integer id) {
        return designPlanRecommendedDao.selectByPrimaryKey(id);
    }
    
}
