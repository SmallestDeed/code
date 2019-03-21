package com.nork.home.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.onekeydesign.dao.DesignTempletMapper;
import com.nork.onekeydesign.model.DesignTemplet;
import com.nork.onekeydesign.model.search.DesignTempletSearch;
import com.nork.home.cache.SpaceCommonCacher;
import com.nork.home.dao.SpaceCommonMapper;
import com.nork.home.model.HouseSpaceResult;
import com.nork.home.model.SpaceCommon;
import com.nork.home.model.SpaceCommonStatus;
import com.nork.home.model.search.SpaceCommonSearch;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.service.BaseCompanyService;
import com.nork.system.dao.ResPicMapper;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysResLevelCfgService;

/**
 * @Title: SpaceCommonServiceImpl.java
 * @Package com.nork.home.service.impl
 * @Description:户型房型-通用空间表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 15:48:39
 * @version V1.0
 */
@Service("spaceCommonService")
@Transactional
public class SpaceCommonServiceImpl implements SpaceCommonService {
	private static Logger logger = Logger
			.getLogger(SpaceCommonServiceImpl.class);

	private SpaceCommonMapper spaceCommonMapper;
	@Autowired
	private SysResLevelCfgService sysResLevelCfgService;
	@Resource
	private ResPicService resPicService;
	@Resource
	private ResModelService resModelService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private DesignTempletMapper designTempletMapper;
	@Autowired
	private ResPicMapper resPicMapper;
	@Autowired
	private BaseCompanyService baseCompanyService;

	@Autowired
	public void setSpaceCommonMapper(SpaceCommonMapper spaceCommonMapper) {
		this.spaceCommonMapper = spaceCommonMapper;
	}

	/**
	 * 新增数据
	 * 
	 * @param spaceCommon
	 * @return int
	 */
	@Override
	public int add(SpaceCommon spaceCommon) {
		spaceCommonMapper.insertSelective(spaceCommon);
		return spaceCommon.getId();
	}

	/**
	 * 更新数据
	 * 
	 * @param spaceCommon
	 * @return int
	 */
	@Override
	public int update(SpaceCommon spaceCommon) {
		return spaceCommonMapper.updateByPrimaryKeySelective(spaceCommon);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return spaceCommonMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 * 
	 * @param id
	 * @return SpaceCommon
	 */
	@Override
	public SpaceCommon get(Integer id) {
		return spaceCommonMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param spaceCommon
	 * @return List<SpaceCommon>
	 */
	@Override
	public List<SpaceCommon> getList(SpaceCommon spaceCommon) {
		return spaceCommonMapper.selectList(spaceCommon);
	}

	/**
	 * 获取数据数量
	 * 
	 * @param spaceCommon
	 * @return int
	 */
	@Override
	public int getCount(SpaceCommonSearch spaceCommonSearch) {
		return spaceCommonMapper.selectCount(spaceCommonSearch);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param spaceCommon
	 * @return List<SpaceCommon>
	 */
	@Override
	public List<SpaceCommon> getPaginatedList(
			SpaceCommonSearch spaceCommonSearch) {
		return spaceCommonMapper.selectPaginatedList(spaceCommonSearch);
	}

	/**
	 * 获取选择的空间列表
	 * 
	 * @param spaceCommon
	 * @return List<SpaceCommon>
	 */
	@Override
	public List<SpaceCommon> getPageSelectList(
			SpaceCommonSearch spaceCommonSearch) {
		return spaceCommonMapper.selectPageSelectList(spaceCommonSearch);
	}

	/**
	 * 获取选择的空间列表总数
	 * 
	 * @param spaceCommon
	 * @return List<SpaceCommon>
	 */
	@Override
	public int getPageSelectCount(SpaceCommonSearch spaceCommonSearch) {
		return spaceCommonMapper.selectPageSelectCount(spaceCommonSearch);
	}

	/**
	 * 根据户型过滤空间
	 * 
	 * @param spaceCommon
	 * @return List<SpaceCommon>
	 */
	@Override
	public List<HouseSpaceResult> getHouseSpaceList(Integer houseId) {

		return spaceCommonMapper.houseSpaceList(houseId);
	}

	@Override
	// public int getHouseSpaceListCount(int parseInt) {
	// return spaceCommonMapper.getHouseSpaceListCount(parseInt);
	// }
	public int getHouseSpaceListCount(SpaceCommon spaceCommon,int userId) {
	    
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, Constants.RES_BIG_TYPE_NUM_SPACE);//如果是空间，他返回的是百分比，需要乘总数
  /*      float valNum = Float.valueOf(val);
        int total =  (int)(spaceCommonMapper.geVaildTotalNum()*valNum);*/
        spaceCommon.setLevelLimitCount(Integer.valueOf(val));
        int realCount = spaceCommonMapper.getHouseSpaceListCount(spaceCommon);
        return  realCount;
	}

	@Override
	// public int getHouseSpaceListCount2(int parseInt) {
	// return spaceCommonMapper.getHouseSpaceListCount2(parseInt);
	// }
	public int getHouseSpaceListCount2(SpaceCommon spaceCommon,int userId) {
	    
	     String val = sysResLevelCfgService.getLimitResNumByUserId(userId, Constants.RES_BIG_TYPE_NUM_SPACE);//如果是空间，他返回的是百分比，需要乘总数
        /*
         * float valNum = Float.valueOf(val); int total =
         * (int)(spaceCommonMapper.geVaildTotalNum()*valNum);
         */
        spaceCommon.setLevelLimitCount(Integer.valueOf(val));
        int realCount = spaceCommonMapper.getHouseSpaceListCount2(spaceCommon);
        return realCount;
	}

	@Override
	/*
	 * public List<HouseSpaceResult> getPaginatedHouseSpaceList(int houseId, int
	 * start, int limit) {
	 */
	public List<HouseSpaceResult> getPaginatedHouseSpaceList(
			SpaceCommon spaceCommon,int userId) {
	   
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, Constants.RES_BIG_TYPE_NUM_SPACE);//如果是空间，他返回的是百分比，需要乘总数
        spaceCommon.setLevelLimitCount(Integer.valueOf(val));
		return spaceCommonMapper.getPaginatedHouseSpaceList(spaceCommon);
	}

	@Override
	/*
	 * public List<HouseSpaceResult> getPaginatedHouseSpaceList2(int houseId,
	 * int start, int limit,int userType) {
	 */
	public List<HouseSpaceResult> getPaginatedHouseSpaceList2(
			SpaceCommon spaceCommon,int userId) {
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, Constants.RES_BIG_TYPE_NUM_SPACE);//如果是空间，他返回的是百分比，需要乘总数
        /*
         * float valNum = Float.valueOf(val); int total =
         * (int)(spaceCommonMapper.geVaildTotalNum()*valNum);
         */
        spaceCommon.setLevelLimitCount(Integer.valueOf(val));
		return spaceCommonMapper.getPaginatedHouseSpaceList2(spaceCommon);
	}

	@Override
	public List<SpaceCommon> getSpaceSearchList(SpaceCommon spaceCommon,int userId) {
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, Constants.RES_BIG_TYPE_NUM_SPACE);//如果是空间，他返回的是百分比，需要乘总数
        /*
         * float valNum = Float.valueOf(val); int total =
         * (int)(spaceCommonMapper.geVaildTotalNum()*valNum);
         */
        spaceCommon.setLevelLimitCount(Integer.valueOf(val));
		return spaceCommonMapper.spaceSearchList(spaceCommon);
	}

	@Override
	public int getSpaceSearchCount(SpaceCommon spaceCommon,int userId) {
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, Constants.RES_BIG_TYPE_NUM_SPACE);//如果是空间，他返回的是百分比，需要乘总数
        /*
         * float valNum = Float.valueOf(val); int total =
         * (int)(spaceCommonMapper.geVaildTotalNum()*valNum);
         */
        spaceCommon.setLevelLimitCount(Integer.valueOf(val));
        int realCount = spaceCommonMapper.spaceSearchCount(spaceCommon);
		return realCount;
	}

	/**
	 * 空间新增、修改需回填的信息表
	 * 
	 * @param baseProduct
	 */
	public void backfill(SpaceCommon spaceCommon) {
		Integer spaceId = spaceCommon.getId();
		// 回填缩略图businessId
		if (spaceCommon.getPicId() != null && spaceCommon.getPicId() > 0) {
			String picKey = "home.spaceCommon.pic";
			resPicService.backFillResPic(spaceId, spaceCommon.getPicId(),
					picKey, spaceCommon.getSpaceCode());
		}
		// 回填空间俯视图
		if (StringUtils.isNotBlank(spaceCommon.getView3dPic())) {
			String picKey = "home.spaceCommon.view3dPic";
			resPicService.backFillResPic(spaceId,
					Integer.valueOf(spaceCommon.getView3dPic()), picKey,
					spaceCommon.getSpaceCode());
		}
		// 回填空间俯视平面图
		if (StringUtils.isNotBlank(spaceCommon.getViewPlanIds())) {
			String picKey = "home.spaceCommon.viewPlan";
			resPicService.backFillResPic(spaceId,
					Integer.valueOf(spaceCommon.getViewPlanIds()), picKey,
					spaceCommon.getSpaceCode());
		}
		// 回填空间cad图
		if (spaceCommon.getCadPicId() != null && spaceCommon.getCadPicId() > 0) {
			String picKey = "home.spaceCommon.cadPic";
			resPicService.backFillResPic(spaceId, spaceCommon.getCadPicId(),
					picKey, spaceCommon.getSpaceCode());
		}
		// 回填模型资源
		// if(!StringUtils.isEmpty(spaceCommon.getModel3dId())){
		// String modelKey = "home.spaceCommon.3dmodel";
		// resModelService.backFillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getModel3dId()),modelKey);
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getModelU3dId())){
		// String modelKey = "home.spaceCommon.u3dmodel.web";
		// resModelService.backFillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getModelU3dId()),modelKey);
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getMacBookPcU3dModelId())){
		// String modelKey = "home.spaceCommon.u3dmodel.macBookPc";
		// resModelService.backFillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getMacBookPcU3dModelId()),modelKey);
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getWindowsPcU3dModelId())){
		// String modelKey = "home.spaceCommon.u3dmodel.windowsPc";
		// resModelService.backFillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getWindowsPcU3dModelId()),modelKey);
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getAndroidU3dModelId())){
		// String modelKey = "home.spaceCommon.u3dmodel.android";
		// resModelService.backFillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getAndroidU3dModelId()),modelKey);
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getIosU3dModelId())){
		// String modelKey = "home.spaceCommon.u3dmodel.ios";
		// resModelService.backFillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getIosU3dModelId()),modelKey);
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getIpadU3dModelId())){
		// String modelKey = "home.spaceCommon.u3dmodel.ipad";
		// resModelService.backFillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getIpadU3dModelId()),modelKey);
		// }
	}

	/**
	 * 空间修改先清除资源回填信息
	 * 
	 * @param baseProduct
	 */
	public void clearBackfill(SpaceCommon spaceCommon) {
		Integer spaceId = spaceCommon.getId();
		// 回填缩略图businessId
		if (spaceCommon.getPicId() != null && spaceCommon.getPicId() > 0) {
			resPicService.clearBackfillResPic(spaceId, spaceCommon.getPicId());
		}
		// 回填空间俯视图
		if (StringUtils.isNotBlank(spaceCommon.getView3dPic())) {
			resPicService.clearBackfillResPic(spaceId,
					Integer.valueOf(spaceCommon.getView3dPic()));
		}
		// 回填模型资源
		// if(!StringUtils.isEmpty(spaceCommon.getModel3dId())){
		// resModelService.clearBackfillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getModel3dId()));
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getModelU3dId())){
		// resModelService.clearBackfillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getModelU3dId()));
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getMacBookPcU3dModelId())){
		// resModelService.clearBackfillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getMacBookPcU3dModelId()));
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getWindowsPcU3dModelId())){
		// resModelService.clearBackfillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getWindowsPcU3dModelId()));
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getAndroidU3dModelId())){
		// resModelService.clearBackfillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getAndroidU3dModelId()));
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getIosU3dModelId())){
		// resModelService.clearBackfillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getIosU3dModelId()));
		// }
		// if(!StringUtils.isEmpty(spaceCommon.getIpadU3dModelId())){
		// resModelService.clearBackfillResModel(spaceId,
		// Integer.valueOf(spaceCommon.getIpadU3dModelId()));
		// }
	}

	@Override
	public List<String> findAllName() {
		return spaceCommonMapper.findAllName();
	}

	@Override
	public String getU3dModelId(String mediaType, SpaceCommon spaceCommon) {
		if (spaceCommon == null || mediaType == null) {
			return "";
		}
		if ("3".equals(mediaType)) {
			return spaceCommon.getWindowsPcU3dModelId() == null ? ""
					: spaceCommon.getWindowsPcU3dModelId().toString();
		} else if ("4".equals(mediaType)) {
			return spaceCommon.getMacBookPcU3dModelId() == null ? ""
					: spaceCommon.getMacBookPcU3dModelId().toString();
		} else if ("5".equals(mediaType)) {
			return spaceCommon.getIosU3dModelId() == null ? "" : spaceCommon
					.getIosU3dModelId().toString();
		} else if ("6".equals(mediaType)) {
			return spaceCommon.getAndroidU3dModelId() == null ? ""
					: spaceCommon.getAndroidU3dModelId().toString();
		} else if ("7".equals(mediaType)) {
			return spaceCommon.getIpadU3dModelId() == null ? "" : spaceCommon
					.getIpadU3dModelId().toString();
		} else {
			return spaceCommon.getModelU3dId() == null ? "" : spaceCommon
					.getModelU3dId().toString();
		}
	}

	@Override
	public int findAllCode(SpaceCommonSearch spaceCommonSearch) {
		return spaceCommonMapper.findAllCode(spaceCommonSearch);
	}

	@Override
	public String spaceCodingVerification(SpaceCommon spaceCommon,
			HttpServletRequest request) {

		Integer spaceFunctionId = spaceCommon.getSpaceFunctionId();
		String spaceAreas = spaceCommon.getSpaceAreas();
		if (spaceFunctionId == null || StringUtils.isBlank(spaceAreas)) {
			return "空间类型和空间面积不能为空";
		}

		SysDictionary houseTypeSysDictionary = sysDictionaryService
				.getSysDictionaryByValue("houseType", spaceFunctionId);
		if (houseTypeSysDictionary == null) {
			return "找不到value:" + spaceFunctionId + ";type:houseType的数据字典";
		} else if (StringUtils.isBlank(houseTypeSysDictionary.getAtt2())) {
			return "value:" + spaceFunctionId
					+ ";type:houseType的数据字典的att2属性未设置,请设置.";
		}

		SysDictionary spaceAreasSysDictionary = sysDictionaryService
				.getSysDictionaryByValue(houseTypeSysDictionary.getValuekey(),
						Integer.parseInt(spaceAreas));
		if (spaceAreasSysDictionary == null) {
			return "找不到value:" + spaceAreas + ";type:"
					+ houseTypeSysDictionary.getValuekey() + "的数据字典";
		} else if (StringUtils.isBlank(spaceAreasSysDictionary.getAtt2())) {
			return "value:" + spaceAreas + ";type:"
					+ houseTypeSysDictionary.getValuekey()
					+ "的数据字典的att2属性未设置,请设置.";
		}

		String codeStart = houseTypeSysDictionary.getAtt2()
				+ spaceAreasSysDictionary.getAtt2() + "_";

		/* 生成序号 */
		SpaceCommonSearch spaceCommonSearch = new SpaceCommonSearch();
		spaceCommonSearch.setSchSpaceCode_(codeStart);
		spaceCommonSearch.setSpaceFunctionId(spaceFunctionId);
		spaceCommonSearch.setSpaceAreas(spaceAreas);
		spaceCommonSearch.setOrder("id");
		spaceCommonSearch.setOrderNum("desc");
		List<SpaceCommon> spaceCommons = getPageSelectList(spaceCommonSearch);
		// 序号
		String number = "";
		if (spaceCommons != null && spaceCommons.size() > 0) {
			/* 序号+1 */
			String spaceCommonCode = spaceCommons.get(0).getSpaceCode();
			if (StringUtils.isNotBlank(spaceCommonCode)
					&& spaceCommonCode.indexOf("_") != -1) {
				String number2 = spaceCommonCode.split("_")[spaceCommonCode
						.split("_").length - 1];
				if (number2.length() == 4 && number2.matches("[0-9]+")) {
					/* 判断序号是否符合格式 */
					int num = Integer.parseInt(number2);
					number = String.format("%04d", num);
				} else {
					number = "0001";
				}
			} else {
				number = "0001";
			}
		} else {
			number = "0001";
		}
		return codeStart + number;
	}

	/**
	 * 通过编码删除
	 * 
	 * @param spaceCode
	 * @return
	 */
	@Override
	public int deleteByCode(String spaceCode) {
		// 删除资源文件

		// 删除数据
		return spaceCommonMapper.deleteByCode(spaceCode);
	}

	@Override
	public SpaceCommon sysSave(SpaceCommon spaceCommon, LoginUser loginUser) {
		spaceCommon.setCreator(loginUser.getLoginName());
		spaceCommon.setModifier(loginUser.getLoginName());
		spaceCommon.setGmtCreate(new Date());
		spaceCommon.setGmtModified(new Date());
		return spaceCommon;
	}

	/**
	 * 通过houseIds查找关联的spaceIds
	 * 
	 * @param houseIds
	 * @return
	 */
	public List<Integer> getSpaceIdsByHouseIds(List<Integer> houseIds) {
		return spaceCommonMapper.getSpaceIdsByHouseIds(houseIds);
	}

	/**
	 * 通过SpaceCommonIds查找 空间名
	 * 
	 * @param houseIds
	 * @return
	 */
	@Override
	public List<SpaceCommon> getSpaceCommonIds(List<Integer> SpaceCommonIds) {
		return spaceCommonMapper.getSpaceCommonIds(SpaceCommonIds);
	}

	/**
	 * 设置空间的效果图和平面图(从该空间关联的,参数isRecommend=1的样板房取得图片url)
	 * 空间需要设置的参数:view3dPicPath,view3dSmallPicPath,viewPlanPath,viewPlanSmallPath
	 * 
	 * @author huangsongbo
	 */
	public void setPicParams(SpaceCommon spaceCommon) {
		DesignTempletSearch designTempletSearch = new DesignTempletSearch();
		designTempletSearch.setStart(0);
		designTempletSearch.setLimit(1);
		designTempletSearch.setLevelLimitCount(-1);
		designTempletSearch.setSpaceCommonId(spaceCommon.getId());
		designTempletSearch.setIsRecommend(new Integer(1));
		designTempletSearch.setIsDeleted(0);
		/*logger.error("------临时log:spaceCommon.getId():" + spaceCommon.getId());*/
		List<DesignTemplet> list = designTempletMapper
				.selectPaginatedList(designTempletSearch);
		if (list == null || list.size() == 0)
			return;
		DesignTemplet designTemplet = list.get(0);
		/*logger.error("------临时log:designTemplet.getId():" + designTemplet.getId());*/
		/* 首页图 */
		/*String view3dPicIdStr = designTemplet.getEffectPic();
		if (StringUtils.isNotBlank(view3dPicIdStr)) {
			if (view3dPicIdStr.indexOf(",") != -1)
				view3dPicIdStr = view3dPicIdStr.substring(0,
						view3dPicIdStr.indexOf(","));
			Integer view3dPicId = Integer.valueOf(view3dPicIdStr);
			ResPic resPic = resPicMapper.selectByPrimaryKey(view3dPicId);
			if (resPic != null) {
				spaceCommon.setView3dPicPath(resPic.getPicPath());
				// Utils.getSmallPicId(resHousePic, "ipad")
				Integer view3dSmallPicId = Utils.getSmallPicId(resPic, "ipad");
				if (view3dSmallPicId != null) {
					ResPic resPicSmall = resPicMapper
							.selectByPrimaryKey(view3dSmallPicId);
					if (resPicSmall != null)
						spaceCommon.setView3dSmallPicPath(resPicSmall
								.getPicPath());
				}
			}
		}*/
		/* 首页图->end */
		/* 平面图 */
		String effectPlanIdsStr = designTemplet.getEffectPlanIds();
		/*logger.error("------临时log:designTemplet.getEffectPlanIds():" + designTemplet.getEffectPlanIds());*/
		if (StringUtils.isNotBlank(effectPlanIdsStr)) {
			if (effectPlanIdsStr.indexOf(",") != -1)
				effectPlanIdsStr = effectPlanIdsStr.substring(0,
						effectPlanIdsStr.indexOf(","));
			Integer effectPlanId = Integer.valueOf(effectPlanIdsStr);
			ResPic resPic = resPicMapper.selectByPrimaryKey(effectPlanId);
			if (resPic != null) {
				/*logger.error("------临时log:resPic.getId():" + resPic.getId());*/
				spaceCommon.setViewPlanPath(resPic.getPicPath());
				// Utils.getSmallPicId(resHousePic, "ipad")
				Integer effectPlanSmallId = Utils.getSmallPicId(resPic, "ipad");
				if (effectPlanSmallId != null) {
					ResPic resPicSmall = resPicMapper
							.selectByPrimaryKey(effectPlanSmallId);
					if (resPicSmall != null)
						spaceCommon.setViewPlanSmallPath(resPicSmall
								.getPicPath());
				}
			}
		}
		/* 平面图->end */
	}

	/**
	 * 处理类似于"!xingx"的值
	 * 
	 * @author huangsongbo
	 * @param categoryBlacklistMap
	 * @return
	 */
	public Map<String, Set<String>> dealWithMap(
			Map<String, Set<String>> categoryBlacklistMap) {
		List<String> spaceCommonTypeList = sysDictionaryService
				.findValueKeyByType("houseType");
		for (Map.Entry<String, Set<String>> entry : categoryBlacklistMap
				.entrySet()) {
			Set<String> typeSet = entry.getValue();// [dddd, xxxx,
													// (xingx,xingy)]
			for (String typeStr : typeSet) {
				if (typeStr.startsWith("(") && typeStr.endsWith("")) {
					typeSet.remove(typeStr);
					List<String> smallTypeListCopy = new ArrayList<String>();
					smallTypeListCopy.addAll(spaceCommonTypeList);
					List<String> strList = Utils.getListFromStr(typeStr
							.replace("(", "").replace(")", ""));
					for (String str : strList) {
						smallTypeListCopy.remove(str);
					}
					typeSet.addAll(smallTypeListCopy);
					entry.setValue(typeSet);
				}
			}
		}
		return categoryBlacklistMap;
	}

	/**
	 * 通过空间状态找出空间idList
	 * 
	 * @author huangsongbo
	 * @param status
	 * @return
	 */
	public List<Integer> findIdListByStatus(int status) {
		return spaceCommonMapper.findIdListByStatus(status);
	}

	/**
	 * 修改空间状态
	 * 
	 * @author huangsongbo
	 * @param hasBeenPutaway
	 *            修改前状态
	 * @param releaseing
	 *            修改后状态
	 */
	public void updateStatus(int oldStatus, int newStatus) {
		spaceCommonMapper.updateStatus(oldStatus, newStatus);
	}
	
	
	/**
	 * 空间布局列表查询接口:根据空间类型、面积查询出空间列表
	 * @param spaceFunctionId
	 * @param areaValue
	 * @param spaceShape
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	@Override
	public Object newSpaceSearch(String spaceFunctionId, String areaValue,
			String spaceShape, String msgId, String limit, String start,
			LoginUser loginUser) {

		/* 获取黑名单配置信息 */
		/* 获得黑名单配置开关 */
		// Integer spaceCommonBlacklistEnable=Integer.valueOf(Utils.getValue("spaceCommonBlacklistEnable",
		// 		"0"));
		Integer spaceCommonBlacklistEnable = 0;
		SysDictionary sysDictionary1 = sysDictionaryService
				.findOneByTypeAndValueKey("configuration",
						"spaceCommonBlacklistEnable");
		if (sysDictionary1 != null) {
			try {
				spaceCommonBlacklistEnable = Integer.valueOf(sysDictionary1
						.getAtt1());
			} catch (Exception e) {
				throw new RuntimeException(
						"配置格式错误,type=configuration,valueKey=spaceCommonBlacklistEnable,att1="
								+ sysDictionary1.getAtt1());
			}
		}
		Set<String> blacklist = new HashSet<String>();
		if (spaceCommonBlacklistEnable == 1) {
			/* 获取黑名单配置 */
			// String spaceCommonBlacklist=Utils.getValue("spaceCommonBlacklist", "");
			String spaceCommonBlacklist = "";
			SysDictionary sysDictionary2 = sysDictionaryService
					.findOneByTypeAndValueKey("configuration",
							"spaceCommonBlacklist");
			if (sysDictionary2 != null && sysDictionary2.getAtt1() != null)
				spaceCommonBlacklist = sysDictionary2.getAtt1();
			Map<String, Set<String>> categoryBlacklistMap = Utils
					.getCategoryBlacklistMap(spaceCommonBlacklist);
			/* 处理类似于"!xingx"的值 */
			categoryBlacklistMap = this.dealWithMap(categoryBlacklistMap);
			/* 得到用户所属类型:序列号->公司->小类 */
			Set<String> typeSet = baseCompanyService
					.getTypeSetByUserId(loginUser.getId());
			/* 获取产品类型黑名单 */
			boolean flag = false;
			for (String typeStr : typeSet) {
				if (categoryBlacklistMap.containsKey(typeStr))
					if (!flag) {
						flag = true;
						blacklist.addAll(categoryBlacklistMap.get(typeStr));
					} else {
						blacklist.retainAll(categoryBlacklistMap.get(typeStr));
					}
			}
		}
		/* 获取黑名单配置信息->end */
		/* 处理黑名单,根据valueKey查询value值,并加入到查询条件 */
		Set<Integer> spaceFunctionIdblackList = new HashSet<Integer>();
		for (String blackValueKey : blacklist) {
			SysDictionary sysDictionary = sysDictionaryService
					.findOneByTypeAndValueKey("houseType", blackValueKey);
			if (sysDictionary != null)
				spaceFunctionIdblackList.add(sysDictionary.getValue());
		}
		/* 处理黑名单,根据valueKey查询value值,并加入到查询条件->end */
		SpaceCommon spaceCommon = new SpaceCommon();
		spaceCommon.setIsDeleted(0);
		if (spaceCommonBlacklistEnable == 1) {
			spaceCommon.setBlackList(spaceFunctionIdblackList);
		}
		int total = 0;
		List<SpaceCommon> list = new ArrayList<SpaceCommon>();
		try {
			spaceCommon.setSpaceFunctionId(Integer.valueOf(spaceFunctionId));// 空间功能类型
			spaceCommon.setSpaceAreas(areaValue);// 空间面积
			if (StringUtils.isNotBlank(spaceShape)) {
				spaceCommon.setSpaceShape(spaceShape);
			}
			// 只查询标准空间(下面两个条件为标准空间)
			spaceCommon.setPid(0);// 缩略图
			spaceCommon.setIsStandardSpace(1);// 是否是标准空间
			if (StringUtils.isNotBlank(start)) {
				spaceCommon.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				spaceCommon.setLimit(Integer.parseInt(limit));
			}
			/* 根据登录用户类型(内部用户,普通用户)决定查询条件putawayState */
			Integer spaceIntegers[] = {};
			Integer designTempletIntegers[] = {};
			String versionType = Utils.getPropertyName("app",
					"sys.version.type", "1").trim();/* 1为外网 2 为内网 默认为外网 */
			if (loginUser.getUserType() == 1 && "2".equals(versionType)) {
				spaceIntegers = new Integer[] { 1, 3, 5 }; /* 1上架 5测试 3为增加 的发布 */
				designTempletIntegers = new Integer[] { 1, 2, 3 }; // 1上架 2测试 3为增加 的发布
			} else {
				spaceCommon.setStatus(SpaceCommonStatus.IS_RELEASE.intValue());
			}
			// spaceCommon.setPutawayStates(Arrays.asList(integers));
			spaceCommon.setStatusList(Arrays.asList(spaceIntegers));
			spaceCommon.setPutawayStates(Arrays.asList(designTempletIntegers));
			/* 根据登录用户类型(内部用户,普通用户)决定查询条件putawayState->end */

			if (Utils.enableRedisCache()) {
				total = SpaceCommonCacher.getTotalWithParameter(spaceCommon,loginUser.getId());
			} else {
				total = this.getSpaceSearchCount(spaceCommon,loginUser.getId());
			}

			if (total > 0) {
				if (Utils.enableRedisCache()) {
					list = SpaceCommonCacher.getPageWithParameter(spaceCommon,loginUser.getId());
				} else {
					list = this.getSpaceSearchList(spaceCommon,loginUser.getId());
				}

			}
			for (SpaceCommon spaceCommonSingle : list) {
				/*
				 * 查询view3dPicPath,view3dSmallPicPath,viewPlanPath,viewPlanSmallPath
				 */
				this.setPicParams(spaceCommonSingle);
			}
			//
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SpaceCommon>(false, "数据异常", msgId);
		}
		return new ResponseEnvelope<SpaceCommon>(total, list, msgId);

	}

	/**
	 * 通过户型过滤空间布局图
	 * @param houseId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	@Override
	public Object newHouseSpaceList(String houseId, String msgId, String limit,
			String start, LoginUser loginUser) {
    	/*获取黑名单配置信息*/
    	/*获得黑名单配置开关*/
    	//Integer spaceCommonBlacklistEnable=Integer.valueOf(Utils.getValue("spaceCommonBlacklistEnable", "0"));
		Integer spaceCommonBlacklistEnable=0;
        SysDictionary sysDictionary1=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklistEnable");
        if(sysDictionary1!=null){
        	try{
        		spaceCommonBlacklistEnable=Integer.valueOf(sysDictionary1.getAtt1());
        	}catch(Exception e){
        		throw new RuntimeException("配置格式错误,type=configuration,valueKey=spaceCommonBlacklistEnable,att1="+sysDictionary1.getAtt1());
        	}
        }
    	Set<String> blacklist=new HashSet<String>();
    	if(spaceCommonBlacklistEnable==1){
    		/*获取黑名单配置*/
    		//String spaceCommonBlacklist=Utils.getValue("spaceCommonBlacklist", "");
    		String spaceCommonBlacklist="";
    		SysDictionary sysDictionary2=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklist");
    		if(sysDictionary2!=null&&sysDictionary2.getAtt1()!=null)
    			spaceCommonBlacklist=sysDictionary2.getAtt2();
    		Map<String,Set<String>> categoryBlacklistMap=Utils.getCategoryBlacklistMap(spaceCommonBlacklist);
    		/*处理类似于"!xingx"的值*/
    		categoryBlacklistMap=this.dealWithMap(categoryBlacklistMap);
    		/*得到用户所属类型:序列号->公司->小类*/
    		Set<String> typeSet=baseCompanyService.getTypeSetByUserId(loginUser.getId());
    		/*获取产品类型黑名单*/
    		boolean flag=false;
    		for(String typeStr:typeSet){
    			if(categoryBlacklistMap.containsKey(typeStr)){
    				if(!flag){
    					flag=true;
    					blacklist.addAll(categoryBlacklistMap.get(typeStr));
    				}else{
    					blacklist.retainAll(categoryBlacklistMap.get(typeStr));
    				}
    			}
    		}
    	}
    	/*获取黑名单配置信息->end*/
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件*/
    	Set<Integer> spaceFunctionIdblackList=new HashSet<Integer>();
    	for(String blackValueKey:blacklist){
    		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValueKey("houseType", blackValueKey);
    		if(sysDictionary!=null)
    			spaceFunctionIdblackList.add(sysDictionary.getValue());
    	}
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件->end*/
		int userType = loginUser==null?-1:(loginUser.getUserType()==null?-1:loginUser.getUserType());
    	SpaceCommon spaceCommonTemporary=new SpaceCommon();
    	spaceCommonTemporary.setHouseId(Integer.parseInt(houseId));
    	spaceCommonTemporary.setUserType(userType);
    	spaceCommonTemporary.setBlackList(spaceFunctionIdblackList);
    	String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
    	spaceCommonTemporary.setVersionType(versionType);
		int total = 0;
		List<HouseSpaceResult> list = new ArrayList<HouseSpaceResult>();
		try {
			if(Utils.enableRedisCache()){
				if(userType ==1){
					total = SpaceCommonCacher.getHouseSpaceListCount2(spaceCommonTemporary,loginUser.getId());
				}else{
					total = SpaceCommonCacher.getHouseSpaceListCount(spaceCommonTemporary,loginUser.getId());
				}
			}else{
				if(userType ==1){
					total=this.getHouseSpaceListCount2(spaceCommonTemporary,loginUser.getId());
				}else{
					total=this.getHouseSpaceListCount(spaceCommonTemporary,loginUser.getId());
				}
			}
			
			/*设置分页查询条件*/
			spaceCommonTemporary.setStart(StringUtils.isBlank(start) ? -1 : Integer.parseInt(start));
			spaceCommonTemporary.setLimit(StringUtils.isBlank(limit) ? -1 : Integer.parseInt(limit));
			/*设置分页查询条件->end*/
			logger.info("total:" + total);
			if (total > 0) {
				if(Utils.enableRedisCache()){
					if(userType ==1){
						list =  SpaceCommonCacher.getPaginatedHouseSpaceList2(spaceCommonTemporary,loginUser.getId());
					}else{
						list =  SpaceCommonCacher.getPaginatedHouseSpaceList(spaceCommonTemporary,loginUser.getId());
					}
					
				}else{
					if(userType==1){
						list = this.getPaginatedHouseSpaceList2(spaceCommonTemporary,loginUser.getId());
					}else{
						list =	 this.getPaginatedHouseSpaceList(spaceCommonTemporary,loginUser.getId());
					}
				}

			}
			if (CustomerListUtils.isNotEmpty(list)) {
				for (HouseSpaceResult houseSpace : list) {
					SpaceCommon spaceCommon=new SpaceCommon();
					spaceCommon.setId(houseSpace.getSpaceId());
					this.setPicParams(spaceCommon);
					/*首页图*/
					houseSpace.setViewPicPath(spaceCommon.getView3dPicPath());
					houseSpace.setViewPicSmallPath(spaceCommon.getView3dSmallPicPath());
					/*平面图*/
					houseSpace.setViewPlanPath(spaceCommon.getViewPlanPath());
					houseSpace.setViewPlanSmallPath(spaceCommon.getViewPlanSmallPath());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<HouseSpaceResult>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<HouseSpaceResult>(total, list, msgId);
	}

	/**
	 * 空间智能搜索
	 * @param spaceCommon 
	 */
	@Override
	public int spaceCapacityCount(SpaceCommon spaceCommon) {
		return spaceCommonMapper.spaceCapacityCount(spaceCommon);
	}

	@Override
	public List<SpaceCommon> spaceCapacityList(SpaceCommon spaceCommon) {
		return spaceCommonMapper.spaceCapacityList(spaceCommon);
	}

}
