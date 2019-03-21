package com.nork.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignTempletService;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.service.MobileSpaceCommonService;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.search.BaseProductStyleSearch;
import com.nork.product.service.BaseProductStyleService;
import com.nork.system.dao.SysDictionaryMapper;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDicitonaryOptimize;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

@Service("mobileSpaceCommonService")
public class MobileSpaceCommonServiceImpl implements MobileSpaceCommonService {

	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SysDictionaryMapper sysDictionaryMapper;
	@Autowired
	private BaseProductStyleService baseProductStyleService;

	/**
	 * 空间布局列表查询接口:根据空间类型、面积查询出空间列表
	 * 
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
		// 调用APP端 原有的serviceImpl里的方法
		return spaceCommonService.newSpaceSearch(spaceFunctionId, areaValue,
				spaceShape, msgId, limit, start, loginUser);
	}

	/**
	 * 获取所有空间形状的图片
	 */
	@Override
	public Object getSpaceShape(String type) {
		String order = "ordering";//model.getOrder();
		String orderNum = "asc";//model.getOrderNum();
		
		SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
		sysDictionarySearch.setType(type);
		sysDictionarySearch.setOrder(order);
		sysDictionarySearch.setOrderNum(orderNum);
		
		List<SysDicitonaryOptimize> list = sysDictionaryMapper.getListOptimizeByType(sysDictionarySearch);
		if(Lists.isNotEmpty(list)) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getPicPath() == null || list.get(i).getPicPath() == "") {
					list.remove(i);
					continue;
				}
			}
		}
		return new ResponseEnvelope<>(list.size(), list, null);
	}
	
	/**
	 * 样板房查询接口。根据空间查询出样板房列表
	 */
	@Override
	public Object newSpaceDesign(String spaceCommonIdText, String msgId,
			String limit, String start, LoginUser loginUser) {
		// 调用APP端 原有的serviceImpl里的方法
		return designTempletService.newSpaceDesign(spaceCommonIdText, msgId,
				limit, start, loginUser);
	}



	/**
	 * 根据空间类型获取对应的面积范围
	 */
	@Override
	public List<SysDictionary> getAreaRange(String type){
		if(type == null || type == ""){
			type = "restaurant";
		}
		List<SysDictionary> areaList = sysDictionaryMapper.findAllByType(type);
		return areaList;
	}

	
	/**
	 * 根据空间名称获取风格list
	 */
	@Override
	public ResponseEnvelope<?> getDesignStyleList(String spaceName) {
		BaseProductStyleSearch baseProductStyleSearch = new BaseProductStyleSearch();
		List<BaseProductStyle> styleList = this.getBaseProductStyleList(spaceName);
		if (CustomerListUtils.isNotEmpty(styleList)) {
			baseProductStyleSearch.setPid(styleList.get(0).getId());
			if(spaceName == null || spaceName == ""){
				baseProductStyleSearch.setPid(56);
			}
		} else {
			return new ResponseEnvelope<BaseProductStyle>(false, null, null);
		}
		baseProductStyleSearch.setLevel(2);
		baseProductStyleSearch.setIsDeleted(0);
		baseProductStyleSearch.setSch_LongCode_(".root2.");
		baseProductStyleSearch.setOrder("id");
		baseProductStyleSearch.setOrderNum("asc");
		List<BaseProductStyle> list = baseProductStyleService.getPaginatedList(baseProductStyleSearch);
		return new ResponseEnvelope<BaseProductStyle>(list.size(), list, null);
	}

	/**
	 * 根据空间名称(比如“客餐厅”)获取风格list
	 * 
	 * @param spaceName
	 * @return
	 */
	private List<BaseProductStyle> getBaseProductStyleList(String spaceName) {
		BaseProductStyle baseProductStyle = new BaseProductStyle();
		if(spaceName == null || spaceName == ""){
			baseProductStyle.setIsDeleted(0);
			baseProductStyle.setLevel(1);
		}else{
			baseProductStyle.setName(spaceName);
			baseProductStyle.setIsDeleted(0);
			baseProductStyle.setLevel(1);
		}
		
		return baseProductStyleService.getList(baseProductStyle);
	}

}
