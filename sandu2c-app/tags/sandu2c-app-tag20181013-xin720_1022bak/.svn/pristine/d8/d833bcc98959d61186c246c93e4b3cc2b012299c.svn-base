package com.sandu.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.design.model.DesignTemplet;
import com.sandu.home.model.dto.GuideInfoDTO;
import com.sandu.system.dao.BaseHouseGuidePicInfoMapper;
import com.sandu.system.model.BaseHouseGuidePicInfo;
import com.sandu.system.model.BaseHousePicFullHousePlanRel;
import com.sandu.system.model.constants.BaseHousePicFullHousePlanRelConstant;
import com.sandu.system.model.search.BaseHouseGuidePicInfoSearch;
import com.sandu.system.service.BaseHouseGuidePicInfoService;
import com.sandu.system.service.BaseHousePicFullHousePlanRelService;

import lombok.extern.log4j.Log4j2;

@Service("baseHouseGuidePicInfoService")
@Log4j2
public class BaseHouseGuidePicInfoServiceImpl implements BaseHouseGuidePicInfoService {
	
	private final static String LOGPREFIX = "[户型2d导航图模块]:";
	
	@Autowired
	private BaseHouseGuidePicInfoMapper baseHouseGuidePicInfoMapper;

	@Autowired
	private BaseHousePicFullHousePlanRelService baseHousePicFullHousePlanRelService;
	
	@Override
	public List<GuideInfoDTO> getGuideInfoDTOListByPicId(
			Integer picId,
			Integer fullHousePlanId, 
			Map<Integer, Integer> renderStatusMap,
			Map<Integer, DesignTemplet> designTempletMap
			) {
		// 参数验证 ->start
		if(picId == null) {
			log.error(LOGPREFIX + "params error: picId = null");
			return null;
		}
		// 参数验证 ->end
		
		// select 坐标信息 from db->start
		BaseHouseGuidePicInfoSearch baseHouseGuidePicInfoSearch = new BaseHouseGuidePicInfoSearch();
		baseHouseGuidePicInfoSearch.setIsDeleted(0);
		baseHouseGuidePicInfoSearch.setPicId(picId);
		List<BaseHouseGuidePicInfo> baseHouseGuidePicInfoList = this.getListBySearch(baseHouseGuidePicInfoSearch);
		// select 坐标信息 from db ->end
		
		// 获取渲染任务状态 ->start
		
		// 是否要删除renderStatusMap不存在的样板房
		// 如果不传renderStatusMap,是不需要删除的(什么时候回传? 装修是判断1-1,1-n的时候)
		boolean flag = false;
		if(renderStatusMap == null) {
			renderStatusMap = baseHousePicFullHousePlanRelService.getRenderStatusMap(fullHousePlanId);
		}else {
			flag = true;
		}
		
		// 获取渲染任务状态 ->start
		
		List<GuideInfoDTO> guideInfoDTOList = new ArrayList<GuideInfoDTO>();
		
		// transform ->start
		if(baseHouseGuidePicInfoList != null && baseHouseGuidePicInfoList.size() > 0) {
			for(BaseHouseGuidePicInfo item : baseHouseGuidePicInfoList) {
				GuideInfoDTO guideInfoDTO = new GuideInfoDTO();
				guideInfoDTO.setRenderStatus(BaseHousePicFullHousePlanRelConstant.STATE_NOT_BEGIN);
				guideInfoDTO.setDesignTempletId(item.getDesignTempletId() == null ? null : item.getDesignTempletId().intValue());
				if(flag) {
					// 在renderStatusMap中找不到样板房id话,会删除该坐标点信息
					if(renderStatusMap.containsKey(guideInfoDTO.getDesignTempletId())) {
						guideInfoDTO.setRenderStatus(renderStatusMap.get(guideInfoDTO.getDesignTempletId()));
					}else {
						// 不记录该坐标信息
						continue;
					}
				}else {
					if(renderStatusMap.containsKey(guideInfoDTO.getDesignTempletId())) {
						guideInfoDTO.setRenderStatus(renderStatusMap.get(guideInfoDTO.getDesignTempletId()));
					}
				}
				/*guideInfoDTO.setRenderStatus(renderStatus);*/
				guideInfoDTO.setSpaceArea(item.getSpaceAreaValue());
				guideInfoDTO.setSpaceType(item.getSpaceTypeValue());
				guideInfoDTO.setXCoordinate(item.getXCoordinate());
				guideInfoDTO.setYCoordinate(item.getYCoordinate());
				// ------ 设置该样板房匹配上了哪个推荐方案, 应对于精选推荐方案匹配的时候 ->start
				if(
						designTempletMap != null 
						&& designTempletMap.get(item.getDesignTempletId() == null ? null : item.getDesignTempletId().intValue()) != null
						) {
					guideInfoDTO.setRecommendedId(designTempletMap.get(item.getDesignTempletId() == null ? null : item.getDesignTempletId().intValue()).getMatchRecommendedId());
				}
				// ------ 设置该样板房匹配上了哪个推荐方案, 应对于精选推荐方案匹配的时候 ->end
				guideInfoDTOList.add(guideInfoDTO);
				guideInfoDTO.setBaseHouseGuidePicInfoId(item.getId() == null ? null : item.getId().intValue());
			}
		}
		// transform ->end
		
		return guideInfoDTOList;
	}

	@Override
	public List <GuideInfoDTO> getGuideInfoDTOListByTemplatedIds(Integer picId, List <Integer> templateIds) {
		// 参数验证 ->start
		if(picId == null) {
			log.error(LOGPREFIX + "params error: picId = null");
			return null;
		}
		// 参数验证 ->end
		
		// select 坐标信息 from db->start
		BaseHouseGuidePicInfoSearch baseHouseGuidePicInfoSearch = new BaseHouseGuidePicInfoSearch();
		baseHouseGuidePicInfoSearch.setIsDeleted(0);
		baseHouseGuidePicInfoSearch.setPicId(picId);
		baseHouseGuidePicInfoSearch.setTemplateIds(templateIds);
		List<BaseHouseGuidePicInfo> baseHouseGuidePicInfoList = this.getListBySearch(baseHouseGuidePicInfoSearch);
		// 获取渲染任务状态 ->start
		List<GuideInfoDTO> guideInfoDTOList = new ArrayList<GuideInfoDTO>();
		// transform ->start
		if(baseHouseGuidePicInfoList != null && baseHouseGuidePicInfoList.size() > 0) {
			for(BaseHouseGuidePicInfo item : baseHouseGuidePicInfoList) {
				GuideInfoDTO guideInfoDTO = new GuideInfoDTO();
				guideInfoDTO.setDesignTempletId(item.getDesignTempletId() == null ? null : item.getDesignTempletId().intValue());
				guideInfoDTO.setRenderStatus(BaseHousePicFullHousePlanRelConstant.STATE_SUCCESS);
				guideInfoDTO.setSpaceArea(item.getSpaceAreaValue());
				guideInfoDTO.setSpaceType(item.getSpaceTypeValue());
				guideInfoDTO.setXCoordinate(item.getXCoordinate());
				guideInfoDTO.setYCoordinate(item.getYCoordinate());
				guideInfoDTO.setBaseHouseGuidePicInfoId(item.getId() == null ? null : item.getId().intValue());
				guideInfoDTOList.add(guideInfoDTO);
			}
		}
		return guideInfoDTOList;
	}

	@Override
	public List<BaseHouseGuidePicInfo> getListBySearch(BaseHouseGuidePicInfoSearch baseHouseGuidePicInfoSearch) {
		// 参数验证 ->start
		if(baseHouseGuidePicInfoSearch == null) {
			return null;
		}
		// 参数验证 ->end
		
		return baseHouseGuidePicInfoMapper.selectBySearch(baseHouseGuidePicInfoSearch);
	}
	
}
