package com.nork.home.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.DesignTempletPutawayState;
import com.nork.design.service.DesignTempletService;
import com.nork.home.cache.BaseHouseCacher;
import com.nork.home.controller.web.WebSpaceCommonController;
import com.nork.home.dao.BaseHouseMapper;
import com.nork.home.model.BaseHouse;
import com.nork.home.model.BaseHouseResult;
import com.nork.home.model.SpaceCommonStatus;
import com.nork.home.model.search.BaseHouseSearch;
import com.nork.home.model.small.BaseHouseSmall;
import com.nork.home.service.BaseHouseService;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.model.small.BaseBrandSmall;
import com.nork.system.cache.BaseAreaCacher;
import com.nork.system.model.BaseArea;
import com.nork.system.model.ResHousePic;
import com.nork.system.service.BaseAreaService;
import com.nork.system.service.ResHousePicService;
import com.nork.system.service.SysResLevelCfgService;

/**
 * @Title: BaseHouseServiceImpl.java
 * @Package com.nork.business.service.impl
 * @Description:业务-户型ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 11:53:51
 * @version V1.0
 */
@Service("baseHouseService")
@Transactional
public class BaseHouseServiceImpl implements BaseHouseService {
	private static Logger logger = Logger
			.getLogger(WebSpaceCommonController.class);
	private final JsonDataServiceImpl<BaseHouse> JsonUtil = new JsonDataServiceImpl<BaseHouse>();
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private ResHousePicService resHousePicService;

	private BaseHouseMapper baseHouseMapper;
	
	@Autowired
    private SysResLevelCfgService sysResLevelCfgService;
	@Autowired
	public void setBaseHouseMapper(BaseHouseMapper baseHouseMapper) {
		this.baseHouseMapper = baseHouseMapper;
	}

	@Autowired
	private SpaceCommonService spaceCommonService;

	@Autowired
	private DesignTempletService designTempletService;

	/**
	 * 新增数据
	 * 
	 * @param baseHouse
	 * @return int
	 */
	@Override
	public int add(BaseHouse baseHouse) {
		baseHouseMapper.insertSelective(baseHouse);
		return baseHouse.getId();
	}

	/**
	 * 更新数据
	 * 
	 * @param baseHouse
	 * @return int
	 */
	@Override
	public int update(BaseHouse baseHouse) {
		return baseHouseMapper.updateByPrimaryKeySelective(baseHouse);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return baseHouseMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 * 
	 * @param id
	 * @return BaseHouse
	 */
	@Override
	public BaseHouse get(Integer id) {
		return baseHouseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param baseHouse
	 * @return List<BaseHouse>
	 */
	@Override
	public List<BaseHouse> getList(BaseHouse baseHouse) {
		return baseHouseMapper.selectList(baseHouse);
	}

	@Override
	public List<BaseHouse> getHouseCommonCodeList(BaseHouse baseHouse) {
		return baseHouseMapper.selectHouseCommonCodeList(baseHouse);
	}

	/**
	 * 获取数据数量
	 * 
	 * @param baseHouse
	 * @return int
	 */
	@Override
	public int getCount(BaseHouseSearch baseHouseSearch) {
		return baseHouseMapper.selectCount(baseHouseSearch);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param baseHouse
	 * @return List<BaseHouse>
	 */
	@Override
	public List<BaseHouse> getPaginatedListFilter(
			BaseHouseSearch baseHouseSearch) {
		List<BaseHouse> baseHouses = baseHouseMapper
				.selectPaginatedList(baseHouseSearch);
		/* 过滤逻辑->户型,空间,样板房三个层级,下级没数据,不显示上级 */
		// for(int i=0;i<baseHouses.size();i++){
		// /*如果flag=true->remove*/
		// boolean flag=false;
		// List<Integer> houseIds=new ArrayList<Integer>();
		// houseIds.add(baseHouses.get(i).getId());
		// List<Integer>
		// spaceIds=spaceCommonService.getSpaceIdsByHouseIds(houseIds);
		// if(spaceIds==null||spaceIds.size()==0){
		// flag=true;
		// }else{
		// /*空间存在->验证样板房*/
		// Integer userType=baseHouseSearch.getUserType();
		// List<Integer> putawayStates=null;
		// /* 无上架得样板房也能显示户型
		// if(userType!=null && userType.intValue()==1){
		// Integer[] integers=new Integer[]{1,2};
		// putawayStates=Arrays.asList(integers);
		// }else{
		// Integer[] integers=new Integer[]{1};
		// putawayStates=Arrays.asList(integers);
		// }
		// List<Integer>
		// templetIds=designTempletService.getTempletIdsBySpaceIds(spaceIds,null);
		// if(templetIds==null||templetIds.size()==0)
		// flag=true;*/
		// }
		// if(flag==true){
		// baseHouses.remove(i);
		// i--;
		// }
		// }
		/* 过滤逻辑->户型,空间,样板房四个层级,下级没数据,不显示上级->end */
		return baseHouses;
	}

	/**
	 * 分页获取数据
	 * 
	 * @param baseHouse
	 * @return List<BaseHouse>
	 */
	@Override
	public List<BaseHouse> getPaginatedList(BaseHouseSearch baseHouseSearch) {
		List<BaseHouse> baseHouses = baseHouseMapper
				.selectPaginatedList(baseHouseSearch);
		return baseHouses;
	}

	@Override
	public List<BaseHouseResult> getHouseList(
			BaseHouseResult baseHouseResultSearch) {
	    
	    int count =BaseHouseCacher.getCount( baseHouseResultSearch);
	    baseHouseResultSearch.setLevelLimitCount(count);
		List<BaseHouseResult> baseHouseResults = null;
		baseHouseResults = baseHouseMapper.houseSearchList(baseHouseResultSearch);

		if (baseHouseResults == null || baseHouseResults.size() == 0)
			return baseHouseResults;

		/* 过滤逻辑->小区,户型,空间,样板房四个层级,下级没数据,不显示上级 */
		/*
		 * for(int i=0;i<baseHouseResults.size();i++){ boolean flag=false;
		 * List<Integer> houseIds=null; if(StringUtils.equals("0",
		 * cacheEnable)){ houseIds =
		 * getHouseIdsByLivingId(baseHouseResults.get(i).getLivingId()); }else{
		 * houseIds =
		 * getHouseIdsByLivingId(baseHouseResults.get(i).getLivingId()); }
		 * 
		 * if(houseIds==null||houseIds.size()==0){ flag=true; }else{
		 * BaseHouseSearch baseHouseSearch=new BaseHouseSearch();
		 * baseHouseSearch.setStart(-1); baseHouseSearch.setLimit(-1);
		 * baseHouseSearch.setLivingId(baseHouseResults.get(i).getLivingId());
		 * baseHouseSearch.setUserType(userType); List<BaseHouse>
		 * baseHouses=getPaginatedListFilter(baseHouseSearch);
		 * baseHouseResults.get(i).setHouseCount(""+baseHouses.size());
		 * if(baseHouses==null||baseHouses.size()==0){
		 * baseHouseResults.remove(i); i--; }
		 * 
		 * } }
		 */
		/* 过滤逻辑->小区,户型,空间,样板房四个层级,下级没数据,不显示上级->end */
		return baseHouseResults;
	}

	/**
	 * 根据livingId得到户型IdList
	 * 
	 * @author huangsongbo
	 * @param livingId
	 * @return
	 */
	public List<Integer> getHouseIdsByLivingId(Integer livingId) {
		return baseHouseMapper.getHouseIdsByLivingId(livingId);
	}

	@Override
	public int getHouseCount(BaseHouseResult baseHouseResult) {
		return baseHouseMapper.getHouseCount(baseHouseResult);
	}

	@Override
	public List<String> findAllName() {
		return baseHouseMapper.findAllName();
	}

	@Override
	public List<Integer> getAllDistinctLivingId() {
		return baseHouseMapper.getAllDistinctLivingId();
	}

	@Override
	public List<Integer> getIdsBySearch(BaseHouseSearch baseHouseSearch) {
		return baseHouseMapper.getIdsBySearch(baseHouseSearch);
	}

	@Override
	public int getCountBySearch(BaseHouseSearch baseHouseSearch) {
		return baseHouseMapper.getCountBySearch(baseHouseSearch);
	}

	@Override
	public Integer getCountByCreator(BaseHouseSearch baseHouseSearch) {
		return baseHouseMapper.getCountByCreator(baseHouseSearch);
	}

	@Override
	public String selectUnitsName(Map<String, Object> map) throws Exception {
		String unitName = baseHouseMapper.selectUnitsName(map);
		return unitName;
	}
	
	/**
	 * 根据省市区搜索户型list 
	 * @param provinceCode
	 * @param cityCode
	 * @param livingName
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object newHouseSearchWeb(String provinceCode, String cityCode, String livingName, String msgId, String limit,
			String start, LoginUser loginUser) {

		BaseHouseResult baseHouseResult = new BaseHouseResult();
		baseHouseResult.setProvinceCode(provinceCode);
		baseHouseResult.setCityCode(cityCode);
		baseHouseResult.setLivingName(livingName);
		if (StringUtils.isNotBlank(start)) {
			baseHouseResult.setStart(Integer.parseInt(start));
		}
		if (StringUtils.isNotBlank(limit)) {
			baseHouseResult.setLimit(Integer.parseInt(limit));
		}
		List<BaseHouseResult> list = new ArrayList<BaseHouseResult>();
		int total = 0;
		String areaCode_p = provinceCode;
		String areaCode_c = cityCode;
		StringBuffer areaLongCode = new StringBuffer();
		if (StringUtils.isNotBlank(areaCode_p)) {
			areaLongCode.append("." + areaCode_p);
		}
		if (StringUtils.isNotBlank(areaCode_c)) {
			areaLongCode.append("." + areaCode_c);
		}
		if (StringUtils.isNotBlank(areaLongCode.toString())) {
			areaLongCode.append(".");
		}
		baseHouseResult.setAreaLongCode(areaLongCode.toString());
		try {
			baseHouseResult.setStart(-1);
			baseHouseResult.setLimit(-1);
			this.internalPermissions(loginUser, baseHouseResult);// 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
			if (Utils.enableRedisCache()) {
				list = BaseHouseCacher.getPageList(baseHouseResult, loginUser.getId());
			} else {
				list = this.getHouseList(baseHouseResult);
			}

			total = list.size();

			list = (List<BaseHouseResult>) Utils.paging2(list, Integer.parseInt(start), Integer.parseInt(limit));
			if (CustomerListUtils.isNotEmpty(list)) {
				for (BaseHouseResult houseResult : list) {
					StringBuffer areaName = new StringBuffer();
					String areaCode = houseResult.getAreaLongCode();
					if (!StringUtils.isEmpty(areaCode)) {
						if (areaCode.contains(".")) {
							String area[] = areaCode.split("\\.");
							BaseArea baseArea = new BaseArea();
							for (int i = 0; i < area.length; i++) {
								baseArea.setAreaCode(area[i]);
								List<BaseArea> areaList = null;
								if (Utils.enableRedisCache()) {
									areaList = BaseAreaCacher.getList(baseArea);
								} else {
									areaList = baseAreaService.getList(baseArea);
								}
								if (CustomerListUtils.isNotEmpty(areaList)) {
									areaName.append(areaList.get(0).getAreaName());
								}
								// 取区名
								while (i == area.length - 1) {
									if (CustomerListUtils.isNotEmpty(areaList)) {
										houseResult.setDistrictName(areaList.get(0).getAreaName());
									}
									break;
								}
							}
						} else {
							BaseArea baseArea = new BaseArea();
							baseArea.setAreaCode(areaCode);
							List<BaseArea> areaList = null;
							if (Utils.enableRedisCache()) {
								areaList = BaseAreaCacher.getList(baseArea);
							} else {
								areaList = baseAreaService.getList(baseArea);
							}
							if (CustomerListUtils.isNotEmpty(areaList)) {
								areaName.append(areaList.get(0).getAreaName());
							}
						}
						houseResult.setAreaName(areaName.toString());
					} else {
						houseResult.setAreaName("不明");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseHouseResult>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<BaseHouseResult>(total, list, msgId);
	}
	/**
	 * 判断该用户该环境 拥有 看到  哪些状态的空间和样板房，并且赋值
	 * @param loginUser
	 * @param baseHouseSearch
	 */
	public void internalPermissions(LoginUser loginUser,BaseHouseResult baseHouseResult) {
		if(loginUser == null || loginUser.getId() < 1 || baseHouseResult == null){
			return;
		}
		Integer spaceCommonStatusList[] = null;//存放空间状态的list  用于in 查询
		Integer designTempletPutawayStateList[] = null; //存放样板房状态的list  用于in 查询
		
		  
		
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();//1为正式2为内部,默认为正式
		
		if(loginUser.getUserType()!=null && 1 == loginUser.getUserType().intValue() && "2".equals(versionType)) {//内部环境并且内部用户才能看到测试中上架中发布中的
			spaceCommonStatusList = new Integer[]{SpaceCommonStatus.IS_RELEASE,SpaceCommonStatus.IS_UP,SpaceCommonStatus.IS_TEST};
			designTempletPutawayStateList= new Integer[]{DesignTempletPutawayState.IS_RELEASE,DesignTempletPutawayState.IS_UP,DesignTempletPutawayState.IS_TEST};
		}else {
			spaceCommonStatusList = new Integer[]{SpaceCommonStatus.IS_RELEASE};
			designTempletPutawayStateList= new Integer[]{DesignTempletPutawayState.IS_RELEASE};

		}
		baseHouseResult.setSpaceCommonStatusList(spaceCommonStatusList);
		baseHouseResult.setDesignTempletPutawayStateList(designTempletPutawayStateList);
	}

	/**
	 * 点击小区名字搜索户型
	 * 
	 * @param style
	 * @param livingId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	@Override
	public Object newHouseList(String style, String livingId, String msgId,
			String limit, String start, LoginUser loginUser) {

		BaseHouseSearch baseHouseSearch = new BaseHouseSearch();
		List<BaseHouse> list = new ArrayList<BaseHouse>();
		int total = 0;
		try {
			baseHouseSearch.setLivingId(Integer.parseInt(livingId));
			if (StringUtils.isNotBlank(start)) {
				baseHouseSearch.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				baseHouseSearch.setLimit(Integer.parseInt(limit));
			}
			this.internalPermissions(loginUser,baseHouseSearch);//判断该用户该环境 拥有 看到  哪些状态的空间和样板房，并且赋值
			total = this.getHaveSpaceCount(baseHouseSearch); 
			if(total>0){
				list = this.getHaveSpaceList(baseHouseSearch);
				for(BaseHouse baseHouse : list){
					// 取户型的缩略图和大图
					if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
						// 截图图片处理 v3.9.4(draw-v1.0.2)
						ResHousePic resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
						baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
						baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
					} else if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() > 0) {
						ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
						baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getThumbnailPath() : "");
						baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getLargeThumbnailPath() : "");
					} else {
						baseHouse.setThumbnailPath("/default/huxing_bg_pic_no.png");
						baseHouse.setLargeThumbnailPath("/default/huxing_bg_pic_no.png");
					}
				}
			}
			if ("small".equals(style) && list != null && list.size() > 0) {
				String baseHouseJsonList = JsonUtil.getListToJsonData(list);
				List<BaseHouseSmall> smallList = new JsonDataServiceImpl<BaseHouseSmall>()
						.getJsonToBeanList(baseHouseJsonList,
								BaseBrandSmall.class);
				return new ResponseEnvelope<BaseHouseSmall>(total, smallList,
						msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseHouse>(false, "数据异常!", msgId);
		}

		return new ResponseEnvelope<BaseHouse>(total, list, msgId);

	}
	/**
	 * 判断该用户该环境 拥有 看到  哪些状态的空间和样板房，并且赋值
	 * @param loginUser
	 * @param baseHouseSearch
	 */
	public void internalPermissions(LoginUser loginUser,BaseHouseSearch baseHouseSearch) {
		if(loginUser == null || loginUser.getId() < 1 || baseHouseSearch == null){
			return;
		}
		Integer spaceCommonStatusList[] = null;//存放空间状态的list  用于in 查询
		Integer designTempletPutawayStateList[] = null; //存放样板房状态的list  用于in 查询
		
		  
		
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();//1为正式2为内部,默认为正式
		
		if(loginUser.getUserType()!=null && 1 == loginUser.getUserType().intValue() && "2".equals(versionType)) {//内部环境并且内部用户才能看到测试中上架中发布中的
			spaceCommonStatusList = new Integer[]{SpaceCommonStatus.IS_RELEASE,SpaceCommonStatus.IS_UP,SpaceCommonStatus.IS_TEST};
			designTempletPutawayStateList= new Integer[]{DesignTempletPutawayState.IS_RELEASE,DesignTempletPutawayState.IS_UP,DesignTempletPutawayState.IS_TEST};
		}else {
			spaceCommonStatusList = new Integer[]{SpaceCommonStatus.IS_RELEASE};
			designTempletPutawayStateList= new Integer[]{DesignTempletPutawayState.IS_RELEASE};

		}
		baseHouseSearch.setSpaceCommonStatusList(spaceCommonStatusList);
		baseHouseSearch.setDesignTempletPutawayStateList(designTempletPutawayStateList);
	}
	
	/**
	 * 根据livingId得到户型IdList
	 * @author jiangwei
	 * @param id
	 * @return
	 */
	@Override
	public List<String> getPaginatedListMoreInfo(Integer id) {
		return baseHouseMapper.getPaginatedListMoreInfo(id);
	}

	/**
	 * 获取拥有空间的户型
	 * @param baseHouseSearch
	 * @return
	 */
	@Override
	public int getHaveSpaceCount(BaseHouseSearch baseHouseSearch) {
		return baseHouseMapper.getHaveSpaceCount(baseHouseSearch);
	}
	/**
	 * 获取拥有空间的户型
	 * @param baseHouseSearch
	 * @return
	 */
	@Override
	public List<BaseHouse> getHaveSpaceList(BaseHouseSearch baseHouseSearch) {
		return baseHouseMapper.getHaveSpaceList(baseHouseSearch);
	}

}
