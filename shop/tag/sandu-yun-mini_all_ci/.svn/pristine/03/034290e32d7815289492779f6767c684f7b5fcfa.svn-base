package com.sandu.company.service.impl;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;
import com.sandu.base.model.vo.BaseCompanyVo;
import com.sandu.base.service.BaseCompanyService;
import com.sandu.cache.service.RedisService;
import com.sandu.common.constant.CompanyTypeEnum;
import com.sandu.common.constant.GlobalConstant;
import com.sandu.common.constant.SysDictionaryConstant;
import com.sandu.common.constant.SysUserConstant;
import com.sandu.common.exception.ElasticSearchException;
import com.sandu.common.utils.*;
import com.sandu.company.dao.CompanyShopDao;
import com.sandu.company.model.CompanyShop;
import com.sandu.company.model.query.*;
import com.sandu.company.model.vo.*;
import com.sandu.company.service.CompanyDesignerService;
import com.sandu.company.service.CompanyShopService;
import com.sandu.designer.po.ShopPlanInfoPo;
import com.sandu.designplan.model.query.DesignPlanQuery;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.interaction.service.CommentService;
import com.sandu.matadata.CacheKeys;
import com.sandu.matadata.Page;
import com.sandu.matadata.enums.InteractionObjectType;
import com.sandu.matadata.enums.ShopCoverResType;
import com.sandu.resource.model.vo.ResPicVo;
import com.sandu.resource.service.ResPicService;
import com.sandu.search.service.design.dubbo.ShopSearchPlanService;
import com.sandu.sys.dao.ProductCategoryDao;
import com.sandu.sys.model.SysDictionary;
import com.sandu.sys.model.query.ProductCategoryQuery;
import com.sandu.sys.model.vo.ProCategoryVO;
import com.sandu.sys.model.vo.ProductCategoryVo;
import com.sandu.sys.model.vo.SysDictionaryVo;
import com.sandu.sys.service.ProductCategoryService;
import com.sandu.sys.service.SysDictionaryService;
import com.sandu.sys.service.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/***
 * 店铺服务接口实现类
 *
 * @author Administrator
 *
 */
@Service("companyShopService")
@Transactional(readOnly = true)
public class CompanyShopServiceImpl implements CompanyShopService {
	private static final Logger logger = LoggerFactory.getLogger(CompanyShopServiceImpl.class.getName());
	@Value("${upload.root}")
	private String rootPath;
	//三度公司Id
	private static final Integer SANDU_COMPANY_ID = 2501;
	@Autowired
	private CompanyShopDao companyShopDao;
	@Autowired
	private DesignPlanRecommendedService designPlanRecommendedService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CompanyDesignerService companyDesignerService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private BaseCompanyService baseCompanyService;

	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ShopSearchPlanService shopSearchPlanService;

	@Override
	public CompanyShopDetailVo get(CompanyShopDetailQuery query) {
		// TODO Auto-generated method stub
		CompanyShopDetailVo detailVo = companyShopDao.getDetail(query.getShopId());
		if (detailVo != null) {
			detailVo.setBusinessTypeName(ShopUtils.getBusinessTypeName(detailVo.getBusinessType()));
			Long companyId = detailVo.getCompanyId();
			detailVo.setPraiseRatePercent(DigitalUtils.FloatToPercent(detailVo.getPraiseRate()));
			detailVo.setCategoryNames(this.getCategoryNames(detailVo));
			detailVo.setFirstCategoryNames(getFirstCategoryNames(detailVo));
			int commentCount = commentService.getCount(InteractionObjectType.Shop.value(), detailVo.getId());
			detailVo.setCommentCount(commentCount);
			//方案相关数据
			//getShopDetailPlanInfo(detailVo,null);
			//设计师数量
			CompanyDesignerQuery designerQuery=new CompanyDesignerQuery();
			designerQuery.setCompanyId(companyId);
			designerQuery.setPlatformValue(query.getPlatformValue());
			int designerCount = companyDesignerService.getDesignerCount(designerQuery);
			detailVo.setDesignerCount(designerCount);
			//获取资源信息
			getResPath(detailVo);
			//获取沟通对象名称
			getCommunicationObj(detailVo);

			String uuid = sysUserService.selectUUIDByPrimaryKey(detailVo.getUserId());

			detailVo.setSessionId(uuid);
		}
		return detailVo;
	}

	@SuppressWarnings("all")
	@Override
	public Page<CompanyShopVo> list(CompanyShopQuery query) throws ElasticSearchException {
		Long startTime = System.currentTimeMillis();

		//根据店铺类型得出用户类型
		setUserTypeByShopType(query);

		String platfromCode = query.getPlatformCode();
		Page<CompanyShopVo> page = new Page<CompanyShopVo>();
		long count = companyShopDao.findFontCount(query);
		page.setCount(count);
		page.setPageSize(query.getPageSize());
		List<CompanyShopVo> lstVo=companyShopDao.findFontPageList(query);
		logger.info("店铺列表sql查询耗时：{}, count:{}" , (System.currentTimeMillis() - startTime), count);
		startTime = System.currentTimeMillis();
		if (null == lstVo || 0 == lstVo.size()) {
			return page;
		}

		/*//xiaoxc_20180710设置查询方案数量和方案列表条件
		Map<String, DesignPlanQuery> queryMap = getPlanInfoCondition(lstVo, platfromCode);

		//查询店铺列表所有企业的方案数量和渲染图列表
		List<ShopPlanInfo> shopPlanInfoList = designPlanRecommendedService.getShopPlanInfo(queryMap);

		//list转map
		Map<String, ShopPlanInfo> planInfoMapMap = null;
		if (null != shopPlanInfoList && 0 < shopPlanInfoList.size()) {
			planInfoMapMap = new HashMap<>(shopPlanInfoList.size());
			planInfoMapMap = shopPlanInfoList.stream().collect(Collectors.toMap(ShopPlanInfo::getCompanyIds, a -> a, (k1, k2) -> k1));
		}*/

		//转换店铺下的方案数量
        //Map<Long, Integer> planCountMap = shopPlanInfoList.stream().collect(Collectors.toMap(ShopPlanInfo::getShopId, ShopPlanInfo::getPlanCount));

        /**
		 * add by qtq
		 *装修公司和设计公司需要返回真实案列,设计师人数,博文,方案数量
		  */
		//首页或者如果当前查询的是装修公司或者设计师的店铺,需要返回以下数据
		/*lstVo.stream().forEach(vo->{
			//获取店铺下的真实案列
			ProjectCaseVo  projectCaseVo  = companyShopDao.CountProjectCase(vo.getId());
			CompanyDesigner companyDesigner =  companyShopDao.CountDesigner(vo.getCompanyId());
			CompanyShopArticle companyShopArticle =  companyShopDao.CountCompanyShopArticle(vo.getId());
			ShopPlanInfo shopPlanInfo = companyShopDao.CountCompanyShopPlanInfo(vo.getId());
			vo.setProjectCaseCount(projectCaseVo.getCount());
			vo.setCompanyDesignerCount(companyDesigner.getDesignerCount());
			vo.setCompanyShopArticleCount(companyShopArticle.getCount());
			vo.setShopPlanCount(shopPlanInfo.getPlanCount());
		});*/

		//TODO 装修公司和设计公司需要返回真实案列,设计师人数,博文,方案数量
		//add by xiaoxc 优化减少数据库交互
		Map<Long, Long> queryMap = new HashMap<Long, Long>(lstVo.size());
		for (CompanyShopVo vo : lstVo) {
			queryMap.put(vo.getId(), vo.getCompanyId());
		}
		Integer platformType = query.getPlatformType();
		Map<Long, ShopInfoVo> shopInfoMap = null;
		if (null != queryMap && queryMap.size() > 0) {
			List<ShopInfoVo> shopInfoVos = companyShopDao.getShopInfo(queryMap, platformType);
			if (null != shopInfoVos && shopInfoVos.size() > 0) {
				shopInfoMap = new HashMap<>(shopInfoVos.size());
				for (ShopInfoVo shopInfoVo : shopInfoVos) {
					shopInfoMap.put(shopInfoVo.getShopId(), shopInfoVo);
				}
			}
		}
		logger.info("查询shopInfoMap耗时：" + (System.currentTimeMillis() - startTime));
		//补充返回值
		for (CompanyShopVo vo : lstVo) {
			vo.setBusinessTypeName(ShopUtils.getBusinessTypeName(vo.getBusinessType()));
			vo.setPraiseRatePercent(DigitalUtils.FloatToPercent(vo.getPraiseRate()));
			// 分类信息
			vo.setCategoryNames(this.getCategoryNames(vo));
			vo.setFirstCategoryNames(getFirstCategoryNames(vo));
			// 方案相关数据
			//getShopPlanInfo(vo, planInfoMapMap);
			//add by xiaoxc_20181031 修改调用ES服务接口查询
			ShopPlanInfoPo shopPlanInfoPo = shopSearchPlanService.getShopPlanInfo((int)vo.getId());
			if (null != shopPlanInfoPo) {
				vo.setPlanTotal(shopPlanInfoPo.getPlanCount());
				vo.setShopPlanCount(shopPlanInfoPo.getPlanCount());
				vo.setLstResPic(shopPlanInfoPo.getPlanPicList());
			}
			if (null != shopInfoMap && shopInfoMap.containsKey(vo.getId())) {
				ShopInfoVo shopInfoVo = shopInfoMap.get(vo.getId());
				vo.setProjectCaseCount(shopInfoVo.getProjectCaseCount());
				vo.setCompanyDesignerCount(shopInfoVo.getCompanyDesignerCount());
				vo.setCompanyShopArticleCount(shopInfoVo.getCompanyShopArticleCount());
			}
			// 获取沟通对象名称
			getCommunicationObj(vo);
			//获取封面图片资源
			String coverResIds = vo.getCoverResIds();
			if(StringUtils.isNotBlank(coverResIds)){
				vo.setCoverResPicPathList(resPicService.getPicPathByIds(coverResIds));
			}
		}
		page.setList(lstVo);
		logger.info("店铺列表for循环耗时：" + (System.currentTimeMillis() - startTime));
		return page;
	}
    
	/**
	 * modified by songjianming@sanduspace.cn on 2018/12/6 只有正常的店铺才有方案，未认领的店铺没有方案数据
	 * @param query
	 * @return
	 * @throws ElasticSearchException
	 */
	@Override
	public Page<CompanyShopVo> listShopOffline(CompanyShopQuery query) throws ElasticSearchException {
		//根据店铺类型得出用户类型
		// setUserTypeByShopType(query);

		Page<CompanyShopVo> page = new Page<>();

		long count = companyShopDao.countShopOffline(query);
		page.setCount(count);
		page.setPageSize(query.getPageSize());

		List<CompanyShopVo> lstVo = companyShopDao.listShopOffline(query);
		if (null == lstVo || 0 == lstVo.size()) {
			return page;
		}

		// TODO 装修公司和设计公司需要返回真实案列,设计师人数,博文,方案数量
		// add by xiaoxc—20181031 优化减少数据库交互
		// 正常的店铺
		Map<Long, Long> queryMap = lstVo.stream().filter(vo -> vo.getShopType() == 0)
				.collect(Collectors.toMap(CompanyShopVo::getId, CompanyShopVo::getCompanyPid, (p, n) -> n));

		Map<Long, ShopInfoVo> shopInfoMap = null;
		Integer platformType = query.getPlatformType();

		if (queryMap.size() > 0) {
			List<ShopInfoVo> shopInfoVos = companyShopDao.getShopInfo(queryMap, platformType);
			if (null != shopInfoVos && shopInfoVos.size() > 0) {
				shopInfoMap = shopInfoVos.stream().collect(Collectors.toMap(ShopInfoVo::getShopId, shop -> shop, (p, n) -> n));
			}
		}

		// 补充返回值
		for (CompanyShopVo vo : lstVo) {
			vo.setBusinessTypeName(ShopUtils.getBusinessTypeName(vo.getBusinessType()));
			vo.setPraiseRatePercent(DigitalUtils.FloatToPercent(vo.getPraiseRate()));
			// 分类信息
			vo.setCategoryNames(this.getCategoryNames(vo));
			vo.setFirstCategoryNames(getFirstCategoryNames(vo));
			// 方案相关数据
			// getShopPlanInfo(vo, planInfoMapMap);
			// add by xiaoxc_20181031 修改调用ES服务接口查询
//			if (vo.getShopType() == 0) {
//				ShopPlanInfoPo shopPlanInfoPo = shopSearchPlanService.getShopPlanInfo((int) vo.getId());
//				if (null != shopPlanInfoPo) {
//					vo.setPlanTotal(shopPlanInfoPo.getPlanCount());
//					vo.setShopPlanCount(shopPlanInfoPo.getPlanCount());
//					vo.setLstResPic(shopPlanInfoPo.getPlanPicList());
//				}
//			}

			if (null != shopInfoMap && shopInfoMap.containsKey(vo.getId()) && vo.getShopType() == 0) {
				ShopInfoVo shopInfoVo = shopInfoMap.get(vo.getId());
				vo.setProjectCaseCount(shopInfoVo.getProjectCaseCount());
				vo.setCompanyDesignerCount(shopInfoVo.getCompanyDesignerCount());
				vo.setCompanyShopArticleCount(shopInfoVo.getCompanyShopArticleCount());
			}
			// 获取沟通对象名称
			getCommunicationObj(vo);
			//获取封面图片资源
			String coverResIds = vo.getCoverResIds();
			if (StringUtils.isNotBlank(coverResIds)) {
				vo.setCoverResPicPathList(resPicService.getPicPathByIds(coverResIds));
			}
		}

		page.setList(lstVo);

		return page;
	}

	@Override
	public CompanyShopDetailVo getOfflineDetail(CompanyShopDetailQuery query) {
		CompanyShopDetailVo detailVo = companyShopDao.getOfflineDetail(query.getShopId());
		if (detailVo != null) {
			detailVo.setBusinessTypeName(ShopUtils.getBusinessTypeName(2));
			// 评论
			detailVo.setCommentCount(0);

			// 设计师数量
			detailVo.setDesignerCount(0);

			// 获取资源信息
			getResPath(detailVo);

			// 获取沟通对象名称
			getCommunicationObj(detailVo);

			String uuid = sysUserService.selectUUIDByPrimaryKey(detailVo.getUserId());

			detailVo.setSessionId(uuid);
		}

		return detailVo;
	}

	//根据店铺类型得出用户类型
	private void setUserTypeByShopType(CompanyShopQuery query) {
		if(query!=null && query.getBusinessType()!=null) {
			List<Integer> userTypeList=new ArrayList<>();
			switch(query.getBusinessType().intValue()) {
				case SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION_VALUE://品牌馆1
					userTypeList.add(2);
					query.setUserTypeList(userTypeList);
					//query.setUserType(2);
					break;
				case SysDictionaryConstant.SHOP_TYPE_FURNITURE_VALUE: //家具建材2;
					userTypeList.add(3);//经销商
					userTypeList.add(14);//独立经销商
					query.setUserTypeList(userTypeList);
					//query.setUserType(3);
					break;
				case SysDictionaryConstant.SHOP_TYPE_PROFESSION_DESIGNER_VALUE: //设计师3;
					userTypeList.add(5);
					query.setUserTypeList(userTypeList);
					//query.setUserType(5);
					break;
				case SysDictionaryConstant.SHOP_TYPE_DESIGNER_COMPANY_VALUE: //设计师公司4;
					userTypeList.add(5);//用户类型为设计师
					query.setUserTypeList(userTypeList);
					//query.setUserType(4);
					break;
				case SysDictionaryConstant.SHOP_TYPE_DECORATION_COMPANY_VALUE://装修公司 5;
					userTypeList.add(5);//设计师
					userTypeList.add(13);//工长
					query.setUserTypeList(userTypeList);
					//query.setUserType(6);
					break;
				case SysDictionaryConstant.SHOP_TYPE_CONSTRUCTION_UNIT_VALUE://工长（施工单位)6;
					userTypeList.add(13);//工长
					userTypeList.add(5);//设计师
					query.setUserTypeList(userTypeList);
					//query.setUserType(13);
					break;
				default:
	                break;
			}

		}

	}

	private String getFirstCategoryName(int id,List<ProductCategoryVo> lst) {
		String name="";
		if(lst!=null && lst.size()>0) {
			for(ProductCategoryVo vo : lst) {
				if(vo.getId()==id) {
					name=vo.getName();
					break;
				}
			}
		}
		return name;
	}

	private String getFirstCategoryNames(CompanyShopVo shop) {
		StringBuilder builder = new StringBuilder();
		if(shop==null || StringUtils.isBlank(shop.getFirstCategoryIds()))
			return builder.toString();
		String firstCategoryIds=shop.getFirstCategoryIds();
		String[] categoryIds=firstCategoryIds.split(",");
		List<ProductCategoryVo> lstVo=productCategoryService.getFirstList();
		if(null==lstVo || lstVo.size()==0){
			lstVo = productCategoryDao.findFirstList(new ProductCategoryQuery());
		}

		for(String id:categoryIds) {
			if(shop.getId()==1) {
				logger.info("categoryId:"+id);
			}
			int cid=DigitalUtils.StringToInt(id);
			if(null!=lstVo && lstVo.size()>0){
				builder.append(getFirstCategoryName(cid,lstVo) + "、");
			}
		}
		return builder.length() > 0 ? builder.substring(0, builder.length() - 1).toString() : "";
	}

	/**
	 * 获取分类名称
	 *
	 * @param vo
	 * @return
	 */
	private String getCategoryNames(CompanyShopVo vo) {
		if (vo == null || vo.getBusinessType() <= 0) {
			return "";
		}
		Integer companyId = new Integer((int) vo.getCompanyId());
		String categoryIds = vo.getCategoryIds();
		int businessType = vo.getBusinessType();
		StringBuilder builder = new StringBuilder();

		// 品牌馆、家居建材:取企业可见范围产品分类
		if (SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION_VALUE == businessType
				|| SysDictionaryConstant.SHOP_TYPE_FURNITURE_VALUE == businessType) {
			List<ProductCategoryVo> categoryList = productCategoryService.getCategoryListByIds(companyId,
					categoryIds);
			if (categoryList != null && 0 < categoryList.size()) {
				for (ProductCategoryVo nodeVo : categoryList) {
					builder.append(nodeVo.getName() + "、");
				}
			}
		}
		// 设计师、设计师公司、装修公司:擅长风格
		if (SysDictionaryConstant.SHOP_TYPE_PROFESSION_DESIGNER_VALUE == businessType
				|| SysDictionaryConstant.SHOP_TYPE_DESIGNER_COMPANY_VALUE == businessType
				|| SysDictionaryConstant.SHOP_TYPE_DECORATION_COMPANY_VALUE == businessType) {
			List<SysDictionaryVo> dictionaryVoList = sysDictionaryService
					.getDictionoryByTypeOrValues(SysDictionaryConstant.DESIGNER_STYLES, categoryIds);
			if (dictionaryVoList != null && 0 < dictionaryVoList.size()) {
				for (SysDictionaryVo dictionaryVo : dictionaryVoList) {
					builder.append(dictionaryVo.getName() + "、");
				}
			}
		}
		// 工长（施工单位）：施工类型
		if (SysDictionaryConstant.SHOP_TYPE_CONSTRUCTION_UNIT_VALUE == businessType) {
			List<SysDictionaryVo> dictionaryVoList = sysDictionaryService
					.getDictionoryByTypeOrValues(SysDictionaryConstant.DIRECTION_CONSTRUCTION_TYPE, categoryIds);
			if (dictionaryVoList != null && 0 < dictionaryVoList.size()) {
				for (SysDictionaryVo dictionaryVo : dictionaryVoList) {
					builder.append(dictionaryVo.getName() + "、");
				}
			}
		}
		return builder.length() == 0 ? "" : builder.substring(0,builder.length()-1).toString();
	}

	@SuppressWarnings("all")
	@Override
	public List<CompanyShopVo> getRecommendList() throws ElasticSearchException {
		List<CompanyShopVo> lstVo = null;
		String key = CacheKeys.getShopRecommend();
		String jsonShop = redisService.get(key);
		if (StringUtils.isBlank(jsonShop)) {
			lstVo = companyShopDao.getRecommendList();
			if (lstVo != null && lstVo.size() > 0) {
				for (CompanyShopVo vo : lstVo) {
					vo.setBusinessTypeName(ShopUtils.getBusinessTypeName(vo.getBusinessType()));
					vo.setPraiseRatePercent(DigitalUtils.FloatToPercent(vo.getPraiseRate()));
					vo.setCategoryNames(this.getCategoryNames(vo));
					//方案相关数据
					//getShopListPlanInfo(vo,null);
					ShopPlanInfoPo shopPlanInfoPo = shopSearchPlanService.getShopPlanInfo((int)vo.getId());
					if (null != shopPlanInfoPo) {
						vo.setPlanTotal(shopPlanInfoPo.getPlanCount());
						vo.setLstResPic(shopPlanInfoPo.getPlanPicList());
					}
					vo.setFirstCategoryNames(getFirstCategoryNames(vo));
				}
				jsonShop=JsonUtils.toJson(lstVo);
				redisService.del(key);
				redisService.addString(key, GlobalConstant.LONG_LONG_CACHE_SECONDS,jsonShop);
			}
		} else {
			lstVo=JsonUtils.fromJson(jsonShop, new TypeToken<List<CompanyShopVo>>() {}.getType());
		}
		return lstVo;
	}

	@SuppressWarnings("all")
	@Override
	public Page<CompanyShopVo> dynamicShopList(CompanyShopQuery query) {
		Page<CompanyShopVo> page = new Page<CompanyShopVo>();
		// 需求要求最新动态必须有logo图才显示，此状态判断是否有图
		query.setDisplayType("dynamicShop");
		List<CompanyShopVo> lstVo = companyShopDao.findFontPageList(query);
		if (lstVo != null && lstVo.size() > 0) {
			for (CompanyShopVo vo : lstVo) {
//				vo.setBusinessTypeName(ShopUtils.getBusinessTypeName(vo.getBusinessType()));
//				vo.setPraiseRatePercent(DigitalUtils.FloatToPercent(vo.getPraiseRate()));
//				vo.setCategoryNames(this.getCategoryNames(vo));
				//方案相关数据
//				getShopListPlanInfo(vo, query.getPlatformCode());
//				vo.setFirstCategoryNames(getFirstCategoryNames(vo));
				}
			page.setList(lstVo);
		}
		return page;
	}

	private List<ShopPopularityListVo> getShopPopularityList(CompanyShopQuery query) {
		List<Integer> casePicIdList = new ArrayList();
		List<ShopPopularityListVo> lstVo = companyShopDao.findShopPopularityList(query);
		for (ShopPopularityListVo vo : lstVo) {
			String casePicIds = vo.getCasePicIds();
			if (!StringUtils.isEmpty(casePicIds)) {
				List<String> picIdList = new ArrayList<>(Arrays.asList(casePicIds.split(",")));
				if (null != picIdList && 0 < picIdList.size()) {
					casePicIdList.add(Integer.valueOf(picIdList.get(0)));
					vo.setCasePicId(Long.valueOf(picIdList.get(0)));
				}
			}
		}
		HashMap<Long, String> picIdMap = resPicService.getMapOfIdAndPicPath(casePicIdList);
		for (ShopPopularityListVo vo : lstVo) {
			vo.setCaseCoverPath(picIdMap.get(vo.getCasePicId()));
		}

		return lstVo;
	}
	private List<ShopPopularityInfoVO> getShopPopularityInfoData(CompanyShopQuery query) {
		List<ShopPopularityInfoVO> result = new ArrayList <>();
		List<ShopPopularityListVo> businessTyp3List = new ArrayList <>();
		List<ShopPopularityListVo> businessTyp4List = new ArrayList <>();
		List<ShopPopularityListVo> businessTyp5List = new ArrayList <>();


		ShopPopularityInfoVO shopPopularityInfo3 = new ShopPopularityInfoVO();
		query.setBusinessType(3);
		businessTyp3List = getShopPopularityList(query);
		shopPopularityInfo3.setBusinessTypeValue(Integer.valueOf(3));
		shopPopularityInfo3.setShopPopularityList(businessTyp3List);

		ShopPopularityInfoVO shopPopularityInfo4 = new ShopPopularityInfoVO();
		query.setBusinessType(4);
		businessTyp4List = getShopPopularityList(query);
		shopPopularityInfo4.setBusinessTypeValue(Integer.valueOf(4));
		shopPopularityInfo4.setShopPopularityList(businessTyp4List);

		ShopPopularityInfoVO shopPopularityInfo5 = new ShopPopularityInfoVO();
		query.setBusinessType(5);
		businessTyp5List = getShopPopularityList(query);
		shopPopularityInfo5.setBusinessTypeValue(Integer.valueOf(5));
		shopPopularityInfo5.setShopPopularityList(businessTyp5List);

		result.add(shopPopularityInfo3);
		result.add(shopPopularityInfo4);
		result.add(shopPopularityInfo5);
		return result;
	}

	@Override
	public Page<ShopPopularityListVo> findShopPopularityList(CompanyShopQuery query) {
		Page<ShopPopularityListVo> page = new Page<>();
		List<ShopPopularityListVo> lstVo = companyShopDao.findShopPopularityList(query);
		if (null != lstVo && 0 < lstVo.size()) {
			for (ShopPopularityListVo vo : lstVo) {
				String casePicIds = vo.getCasePicIds();
				if (!StringUtils.isEmpty(casePicIds)) {
					List<String> picIdList = new ArrayList<>(Arrays.asList(casePicIds.split(",")));
					if (null != picIdList && 0 < picIdList.size()) {
						ResPicVo picVo = resPicService.getPicById(Long.valueOf(picIdList.get(0)));
						vo.setCaseCoverPath(picVo != null ? picVo.getPicPath() : "");
					}
				}
			}
		}
		page.setList(lstVo);
		return page;
	}

	public boolean updateVisitCount(long shopId) {
		boolean isUpdated = false;
		CompanyShop shop = new CompanyShop();
		shop.setId(shopId);
		shop.setGmtModified(new Date());
		int result=companyShopDao.updateVisitCount(shop);
		if(result>0)
			isUpdated=true;

		return isUpdated;
	}

	/**
	 * 方案相关数据
	 * @param detailVo
	 */
	/*@SuppressWarnings("all")
	private void getShopDetailPlanInfo(CompanyShopDetailVo detailVo, String platfromCode) {
		Long companyId = detailVo.getCompanyPid();

		// 一键方案数量
		DesignPlanQuery query = new DesignPlanQuery();
		if (StringUtils.isNotEmpty(platfromCode)) {
			query.setPlatformId(designPlanRecommendedService.getPlatformId(platfromCode));
		}
		BaseCompanyVo baseCompany = baseCompanyService.get(companyId);
		List<Integer> companyIds = new ArrayList<>();
		if (baseCompany != null ) {
			if (CompanyTypeEnum.MANUFACTURER.getValue().equals(baseCompany.getBusinessType())) {
				companyIds.add(Integer.valueOf(baseCompany.getId()+""));
			} else if (CompanyTypeEnum.FRANCHISER.getValue().equals(baseCompany.getBusinessType())) {
				companyIds.add(Integer.valueOf(baseCompany.getId()+""));
			} else {
				companyIds.add(Integer.valueOf(baseCompany.getId()+""));
				companyIds.add(2501);//非厂商经销商需查三度公司+本公司方案(先写死后续处理)
			}
		}
		query.setCompanyIds(companyIds);
		query.setCompanyId(new BigInteger(companyId.toString()));
		query.setRecommendedType(2);
		int onekeyPlanCount = designPlanRecommendedService.findRecommendedPlanCount(query);
		detailVo.setOnekeyPlanCount(onekeyPlanCount);
		// 样板间数量
		query.setRecommendedType(1);
		int openPlanCount = designPlanRecommendedService.findRecommendedPlanCount(query);
		detailVo.setOpenPlanCount(openPlanCount);
		// 详情方案列表显示3个
		query.setRecommendedType(null);
		query.setPageSize(3);
		query.getStart();
		List<DesignPlanVo> planVoList = designPlanRecommendedService.findRecommendedPlanList(query);
		if (planVoList != null && 0 < planVoList.size()) {
			detailVo.setLstResPic(planVoList);
		}
	}*/

	/**
	 * 方案相关数据
	 * @param vo
	 *//*
	@SuppressWarnings("all")
	private void getShopListPlanInfo(CompanyShopVo vo, String platfromCode) {
		Long startTime = System.currentTimeMillis();
		Long companyId = vo.getCompanyPid();
		DesignPlanQuery query = new DesignPlanQuery();
		if (StringUtils.isNotEmpty(platfromCode)) {
			query.setPlatformId(designPlanRecommendedService.getPlatformId(platfromCode));
		}
		BaseCompanyVo baseCompany = baseCompanyService.get(companyId);
		List<Integer> companyIds = new ArrayList<>();
		if (baseCompany != null ) {
			if (CompanyTypeEnum.MANUFACTURER.getValue().equals(baseCompany.getBusinessType())) {
				companyIds.add(Integer.valueOf(baseCompany.getId()+""));
			} else if (CompanyTypeEnum.FRANCHISER.getValue().equals(baseCompany.getBusinessType())) {
				companyIds.add(Integer.valueOf(baseCompany.getId()+""));
			} else {
				companyIds.add(Integer.valueOf(baseCompany.getId()+""));
				if (!SANDU_COMPANY_ID.equals(baseCompany.getId())) {
					//非厂商经销商需查三度公司+本公司方案(先写死后续处理)
					companyIds.add(SANDU_COMPANY_ID);
				}
			}
		}
		query.setCompanyIds(companyIds);
		query.setCompanyId(new BigInteger(companyId.toString()));
		// 方案总数量
		String planCountKey = CacheKeys.getCompanyShopRecommendCount(platfromCode, companyId.intValue());
		String jsonPlanCount = redisService.get(planCountKey);
		Integer planTotal = 0;
		if (StringUtils.isEmpty(jsonPlanCount)) {
			planTotal = designPlanRecommendedService.findRecommendedPlanCount(query);
			redisService.del(planCountKey);
			redisService.addString(planCountKey, GlobalConstant.LONG_LONG_CACHE_SECONDS, jsonPlanCount);
		} else {
			planTotal = Integer.parseInt(jsonPlanCount);
		}
		vo.setPlanTotal(planTotal);
		logger.debug("getShopListPlanInfo{} : 查询方案数量时间 = " + (System.currentTimeMillis() - startTime));

		// 店铺列表方案显示3个
		query.setPageSize(3);
		query.getStart();
		String planListKey = CacheKeys.getCompanyShopRecommendList(platfromCode, companyId.intValue());
		String jsonPlanList = redisService.get(planListKey);
		List<DesignPlanVo> planVoList = null;
		if (StringUtils.isEmpty(jsonPlanList)) {
			planVoList = designPlanRecommendedService.findRecommendedPlanList(query);
			redisService.del(planListKey);
			redisService.addSet(planListKey, GlobalConstant.LONG_LONG_CACHE_SECONDS, jsonPlanList);
		} else {
			JsonUtils.fromJson(jsonPlanList, new TypeToken<List<DesignPlanVo>>() {}.getType());
		}
		if (planVoList != null && 0 < planVoList.size()) {
			vo.setLstResPic(planVoList);
		}
		logger.info("getShopListPlanInfo{} : 店铺列表方案查询耗时 = " + (System.currentTimeMillis() - startTime));
	}*/

	/**
	 * 获取资源相对路径
	 * @param detailsVO
	 */
	private void getResPath(CompanyShopDetailVo detailsVO) {
		if (detailsVO == null) {
			return;
		}
		String coverIds = detailsVO.getCoverResIds();
		String filePath = detailsVO.getFilePath();
		//封面资源地址
		if (StringUtils.isNotEmpty(coverIds)) {
			//图片列表和全景图
			if (ShopCoverResType.PICLIST.value() == detailsVO.getCoverResType()
					|| ShopCoverResType.PANORAMA.value() == detailsVO.getCoverResType()) {
				List<String> list = resPicService.getPicPathByIds(coverIds);
				if (list != null && 0 < list.size()) {
					detailsVO.setCoverResList(list);
				}
			}
			//视频
			if (ShopCoverResType.VIDEO.value() == detailsVO.getCoverResType()) {

			}
		}
		//店铺介绍富文本内容
		if (StringUtils.isNotEmpty(filePath)) {
			// 读取配置
			String fileContext = Utils.getFileContext(rootPath + filePath);
			detailsVO.setShopIntroduced(fileContext);
		}
	}

	@Override
	public List<ProjectCaseVo> getShopProjectCaseList(ProjectCaseQuery query) {
		List<ProjectCaseVo> list = companyShopDao.findShopProjectCaseList(query);
		if (list != null && list.size() > 0) {
			list.forEach(projectCaseVo -> {
				String filePath = projectCaseVo.getFilePath();
				//案例富文本内容
				if ("detail".equals(query.getPageType())) {
					if (StringUtils.isNotEmpty(filePath)) {
						// 读取配置
						String fileContext = Utils.getFileContext(rootPath + filePath);
						projectCaseVo.setContent(fileContext);
					}
					//获取详情时修改案例浏览数量
					Integer browseCount =0;
					if(null!=projectCaseVo.getBrowseCount()){
						browseCount=projectCaseVo.getBrowseCount();
					}
					browseCount++;
					this.updateBrowseCount(projectCaseVo.getCaseId().longValue(),browseCount);
					projectCaseVo.setBrowseCount(browseCount);
				}else{
					if (StringUtils.isNotEmpty(filePath)) {
						// 读取配置
						String fileContext = Utils.getFileContext(rootPath + filePath);
						projectCaseVo.setContent(fileContext);
					}
					//取富文本第一张图做封面图
					if (StringUtils.isNotEmpty(projectCaseVo.getPicIds())){
						List<Integer> idList = this.getIntegerListFromStringList(projectCaseVo.getPicIds());
						if (idList != null && 0 < idList.size()) {
							if (0!=Long.valueOf(idList.get(0))){
								ResPicVo resPicVo = resPicService.getPicById(Long.valueOf(idList.get(0)));
								logger.info("查询店铺案例列表  查询展示图片信息结果 ResPicVo:{}",resPicVo.toString());
								logger.info("查询店铺案例列表  查询展示图片信息结果 ResPicVo:{}",resPicVo.getPicPath());
								if (null!=resPicVo.getPicPath()){
									projectCaseVo.setPicPath(resPicVo.getPicPath());
								}
							}
						}
					}
					if (null==projectCaseVo.getBrowseCount()){
						projectCaseVo.setBrowseCount(0);
					}
				}
			});
		}
		return list;
	}

	@Override
	public int updateBrowseCount(Long caseId, Integer browseCount) {
		return companyShopDao.updateBrowseCount(caseId,browseCount);
	}

	@Override
	public List<CompanyShopStyleListVO> getShopStyleList(ShopStyleQuery query) {
		//家居建材、品牌馆----产品分类-----对应企业可见产品范围
		List<CompanyShopStyleListVO> list=null;
		if ((query.getStyleType()==SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION_VALUE)||
				(query.getStyleType()==SysDictionaryConstant.SHOP_TYPE_FURNITURE_VALUE)){

				//获得可见产品范围信息
				List<ProCategoryVO> proCategoryList = productCategoryService.getListByLevel(2,query.getStart(),query.getPageSize());
				//构造输出
				if (null!=proCategoryList&&proCategoryList.size()>0){
					list=new ArrayList<>();
					for (ProCategoryVO p:proCategoryList) {
						CompanyShopStyleListVO shopStyle = new CompanyShopStyleListVO();
						shopStyle.setShopType(query.getStyleType());
						shopStyle.setStyleId(p.getId().intValue());
						shopStyle.setStyleName(p.getName());
						list.add(shopStyle);
					}
				}
			return list;
		}
		//设计师、设计公司、装修公司-----设计风格----数据字典中type=goodStyle
		else if ((query.getStyleType()==SysDictionaryConstant.SHOP_TYPE_PROFESSION_DESIGNER_VALUE)||
				(query.getStyleType()==SysDictionaryConstant.SHOP_TYPE_DESIGNER_COMPANY_VALUE)||
				(query.getStyleType()==SysDictionaryConstant.SHOP_TYPE_DECORATION_COMPANY_VALUE)){
			return this.getShopStyleFormDictionary(list, SysDictionaryConstant.DESIGNER_STYLES, query);
		}
		//工长-----施工类型-----数据字典中type=constructionType
		else if(query.getStyleType()==SysDictionaryConstant.SHOP_TYPE_CONSTRUCTION_UNIT_VALUE){
			return this.getShopStyleFormDictionary(list, SysDictionaryConstant.DIRECTION_CONSTRUCTION_TYPE, query);
		}
		return null;
	}



	private List<CompanyShopStyleListVO> getShopStyleFormDictionary(List<CompanyShopStyleListVO> list,String type,ShopStyleQuery query){
		//从数据字典根据查询“风格信息”
		List<SysDictionary> sysDictionarys = sysDictionaryService.getListByType(type,query.getStart(),query.getPageSize());
		if (sysDictionarys.size()>0){
			list=new ArrayList<>();
			for (SysDictionary dic:sysDictionarys) {
				CompanyShopStyleListVO shopStyle=new CompanyShopStyleListVO();
				shopStyle.setShopType(query.getStyleType());
				shopStyle.setStyleId(dic.getValue());
				shopStyle.setStyleName(dic.getName());
				list.add(shopStyle);
			}
			return list;
		}else{
			return list;
		}
	}


	/**
	 * 获取沟通对象
	 * @param detailVo
	 */
	private void getCommunicationObj(CompanyShopDetailVo detailVo) {
		String userName = detailVo.getUserName();
		String nickName = detailVo.getNickName();
		if (StringUtils.isNotEmpty(userName)) {
			return ;
		}
		//如果为空则用登录号
		if (StringUtils.isNotEmpty(nickName) && Utils.isMobile(nickName)) {
			detailVo.setUserName(nickName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
		} else {
			detailVo.setUserName(nickName);
		}
	}

	/**
	 * 获取沟通对象
	 * @param shopVo
	 */
	private void getCommunicationObj(CompanyShopVo shopVo) {
		String userName = shopVo.getUserName();
		String nickName = shopVo.getNickName();
		if (StringUtils.isNotEmpty(userName)) {
			return ;
		}
		//如果为空则用登录号
		if (StringUtils.isNotEmpty(nickName) && Utils.isMobile(nickName)) {
			shopVo.setUserName(nickName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
		} else {
			shopVo.setUserName(nickName);
		}
	}

	/**
	 * 获取搜索方案信息条件
	 * @param lstVo
	 * @param platfromCode
	 * @return
	 */
	private Map<String, DesignPlanQuery> getPlanInfoCondition(List<CompanyShopVo> lstVo, String platfromCode ) {
		//获取平台ID
		Integer platformId = null;
		if (StringUtils.isNotEmpty(platfromCode)) {
			platformId = designPlanRecommendedService.getPlatformId(platfromCode);
		}
		Map<String, DesignPlanQuery> queryMap = new HashMap<>(lstVo == null ? 0 : lstVo.size());
		for (CompanyShopVo shopVo : lstVo) {
			String mapKey = this.getCompanyIds(shopVo).toString();
			if (!StringUtils.isEmpty(mapKey)) {
				//map已存在企业key则continue
				if (queryMap.containsKey(mapKey)) {
					continue;
				}
			}
			DesignPlanQuery planQuery = new DesignPlanQuery();
			planQuery.setShopId(new Integer((int)shopVo.getId()));
			planQuery.setPlatformId(platformId);
			//获取企业信息
			planQuery.setCompanyIds(getCompanyIds(shopVo));
			planQuery.setCompanyId(new BigInteger(shopVo.getCompanyPid() + ""));
			planQuery.setPageSize(3);
			planQuery.getStart();
			// 这里以companyIds为key
			queryMap.put(planQuery.getCompanyIds().toString(), planQuery);
		}

		return queryMap;
	}


	/**
	 * 1、非厂商经销商可查三度公司方案
	 * 2、厂商经销商试用账号可查看三度公司方案
	 * @param shopVo
	 * @return
	 */
	private List<Integer> getCompanyIds(CompanyShopVo shopVo) {
		Long companyId = shopVo.getCompanyPid();
		//获取企业信息
		String key = CacheKeys.getCompanyId(companyId.intValue());
		String companyJson = redisService.get(key);
		BaseCompanyVo baseCompany  = null;
		if (StringUtils.isEmpty(companyJson)) {
			baseCompany = baseCompanyService.get(companyId);
			redisService.del(key);
			redisService.addSet(key, GlobalConstant.LONG_CACHE_SECONDS, JsonUtils.toJson(baseCompany));
		} else {
			baseCompany = JsonUtils.fromJson(companyJson, new TypeToken<BaseCompanyVo>() {}.getType());
		}
		List<Integer> companyIds = new ArrayList<>();
		if (baseCompany != null ) {
			if (CompanyTypeEnum.MANUFACTURER.getValue().equals(baseCompany.getBusinessType())) {
				companyIds.add(companyId.intValue());
				// 试用用户和是否显示三度方案
				if (SysUserConstant.NO_SHOW_SANDU_PLAN_STATUS == shopVo.getUseType()
						&& SysUserConstant.SHOW_SANDU_PLAN_STATUS == shopVo.getShowSanduPlan() ) {
					companyIds.add(SANDU_COMPANY_ID);
				}
			} else {
				companyIds.add(companyId.intValue());
				if (!SANDU_COMPANY_ID.equals(new Integer(companyId.intValue()))) {
					//非厂商经销商需查三度公司+本公司方案(先写死后续处理)
					companyIds.add(SANDU_COMPANY_ID);
				}
			}
		}
		return companyIds;
	}

//	/**
//	 * 获取方案数据
//	 * @param shopVo
//	 * @param shopPlanInfoMap
//	 */
	/*private void getShopPlanInfo(CompanyShopVo shopVo, Map<String, ShopPlanInfo> shopPlanInfoMap) {

		if (null == shopPlanInfoMap) {
			return;
		}
		//处理map对象获取方案数据
		String mapKey = this.getCompanyIds(shopVo).toString();
		if (shopPlanInfoMap.containsKey(mapKey)) {
			ShopPlanInfo shopPlanInfo = shopPlanInfoMap.get(mapKey);
			if (shopPlanInfo != null) {
				shopVo.setPlanTotal(shopPlanInfo.getPlanCount());
				List<DesignPlanVo> planVoList = new ArrayList<>(3);
				String picPaths = shopPlanInfo.getPicPaths();
				if (!StringUtils.isEmpty(picPaths)) {
					DesignPlanVo planVo;
					for (String planIdPath : picPaths.split(",")) {
						planVo = new DesignPlanVo();
						String planId = planIdPath.substring(0,planIdPath.indexOf("/"));
						String planPath = planIdPath.substring(planIdPath.indexOf("/"),planIdPath.length());
						planVo.setPlanId(StringUtils.isEmpty(planId) ? 0 : Integer.valueOf(planId));
						planVo.setPicPath(planPath);
						planVoList.add(planVo);
					}
					shopVo.setLstResPic(planVoList);
				}
			}
		}
	}*/

	public static List<Integer> getIntegerListFromStringList(String str) {
		List<Integer> list = new ArrayList();
		if (!org.apache.commons.lang3.StringUtils.isBlank(str) && !"null".equals(str)) {
			String[] strs = str.split(",");
			String[] var3 = strs;
			int var4 = strs.length;

			for(int var5 = 0; var5 < var4; ++var5) {
				String idStr = var3[var5];
				if (!org.apache.commons.lang3.StringUtils.isBlank(idStr)) {
					list.add(Integer.parseInt(idStr));
				}
			}

			return list;
		} else {
			return list;
		}
	}
}
