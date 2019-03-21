package com.sandu.designtemplate.service.impl;

import com.sandu.common.util.collections.Lists;
import com.sandu.design.model.DesignTemplet;
import com.sandu.design.model.search.DesignTempletSearch;
import com.sandu.designplan.model.DesignPlanRecommended;
import com.sandu.designtemplate.dao.DesignTempletMapper;
import com.sandu.designtemplate.service.DesignTempletService;
import com.sandu.home.model.SpaceCommon;
import com.sandu.home.service.SpaceCommonService;
import com.sandu.system.service.SysDictionaryService;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Title: DesignTempletServiceImpl.java
 * @Package com.sandu.design.service.impl
 * @Description:设计模块-设计方案样板房表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 */
@Service("designTempletService")
@Log4j2
public class DesignTempletServiceImpl implements DesignTempletService {

	private static final String LOGPREFIX = "[样板房模块]:";
	
    @Autowired
    private DesignTempletMapper designTempletMapper;
    
    @Autowired
    private SpaceCommonService spaceCommonService;
    
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 新增数据
     *
     * @param designTemplet
     * @return int
     */
    @Override
    public int add(DesignTemplet designTemplet) {
        designTempletMapper.insertSelective(designTemplet);
        return designTemplet.getId();
    }

    /**
     * 更新数据
     *
     * @param designTemplet
     * @return int
     */
    @Override
    public int update(DesignTemplet designTemplet) {
        return designTempletMapper.updateByPrimaryKeySelective(designTemplet);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return designTempletMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return DesignTemplet
     */
    @Override
    public DesignTemplet get(Integer id) {
        return designTempletMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DesignTemplet> getPaginatedList(DesignTempletSearch designTempletSearch) {
        List<DesignTemplet> designTemplets = designTempletMapper.selectPaginatedList(designTempletSearch);
        return designTemplets;
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(DesignTempletSearch designTempletSearch) {
        return designTempletMapper.selectCount(designTempletSearch);
    }

	@Override
	public List<DesignTemplet> getListByHouseId(Integer houseId) {
		// 参数验证 ->start
		if(houseId == null) {
			return null;
		}
		// 参数验证 ->end
		
		return designTempletMapper.getListByHouseId(houseId);
	}

	@Override
	public boolean getIsMatch(DesignTemplet designTemplet, DesignPlanRecommended designPlanRecommended) {
		// ------ params verify ->start
		if(designTemplet == null) {
			log.error(LOGPREFIX + "params error: designTemplet = null");
			return false;
		}
		if(designTemplet.getSpaceFunctionId() == null) {
			log.error(LOGPREFIX + "params error: designTemplet.getSpaceFunctionId() = null");
			return false;
		}
		if(designPlanRecommended == null) {
			log.error(LOGPREFIX + "params error: designTemplet = null");
			return false;
		}
		if(designPlanRecommended.getApplySpaceAreas() == null) {
			log.error(LOGPREFIX + "该推荐方案没有标注适用面积(designPlanRecommended.getApplySpaceAreas() = null); designPlanRecommendedId = {}", designPlanRecommended.getId());
			return false;
		}
		// ------ params verify ->end
		
		// ------空间类型不匹配就pass ->start
		Integer recommendedSpaceFuntionId = designPlanRecommended.getSpaceFunctionId();
		if(recommendedSpaceFuntionId == null) {
			if(designPlanRecommended.getSpaceCommonId() != null) {
				SpaceCommon spaceCommon = spaceCommonService.get(designPlanRecommended.getSpaceCommonId());
				if(spaceCommon != null) {
					recommendedSpaceFuntionId = spaceCommon.getSpaceFunctionId();
				}
			}
		}
		
		if(recommendedSpaceFuntionId == null) {
			log.error(LOGPREFIX + "没有获取到推荐方案的空间类型; recommendedId = " + recommendedSpaceFuntionId);
			return false;
		}
		
		if(designTemplet.getSpaceFunctionId().intValue() != recommendedSpaceFuntionId.intValue()) {
			log.info(LOGPREFIX + "样板房与推荐方案空间类型不匹配; designTempletId = {}; recommendedId = {}", designTemplet.getId(), designPlanRecommended.getId());
			return false;
		}
		// ------空间类型不匹配就pass ->end
		
		// ------ 获取样板房用于匹配的面积 ->start
		Integer spaceAreaValue = null;
		String spaceArea = null;
		if(designTemplet.getMainArea() == null) {
			spaceArea = designTemplet.getSpaceAreas();
		}else {
			spaceArea = designTemplet.getMainArea() + "";
		}
		spaceAreaValue = sysDictionaryService.getSpaceAreaValue(designTemplet.getSpaceFunctionId(), spaceArea);
		if(spaceAreaValue == null) {
			log.info(LOGPREFIX + "没有找到对应空间类型和对应面积的数据字典; spaceFunctionId = {}; spaceArea = {}", designTemplet.getSpaceFunctionId(), spaceArea);
			return false;
		}
		// ------ 获取样板房用于匹配的面积 ->end
		
		String applySpaceAreas = designPlanRecommended.getApplySpaceAreas();
		if(("," + applySpaceAreas + ",").indexOf("," + spaceAreaValue + ",") == -1) {
			log.info(LOGPREFIX + "样板房与推荐方案空间类型匹配但是面积不匹配; designTempletId = {}; recommendedId = {}", designTemplet.getId(), designPlanRecommended.getId());
			return false;
		}else {
			log.info(LOGPREFIX + "样板房与推荐方案匹配成功; designTempletId = {}; recommendedId = {}", designTemplet.getId(), designPlanRecommended.getId());
			return true;
		}
		
	}

	@Override
	public Integer getMatchRecommendedId(DesignTemplet designTemplet,
			List<DesignPlanRecommended> designPlanRecommendedList) {
		// ------ 参数验证 ->start
		if(designTemplet == null) {
			log.error(LOGPREFIX + "designTemplet = null");
			return null;
		}
		if(Lists.isEmpty(designPlanRecommendedList)) {
			log.error(LOGPREFIX + "Lists.isEmpty(designPlanRecommendedList) = true");
			return null;
		}
		// ------ 参数验证 ->end
		
		for(DesignPlanRecommended designPlanRecommended : designPlanRecommendedList) {
			if(this.getIsMatch(designTemplet, designPlanRecommended)) {
				return designPlanRecommended.getId();
			}
		}
		return null;
	}

	@Override
	public DesignTemplet getPlanStyleAndAreaByTempletId(Integer templateId) {
		return designTempletMapper.selectPlanStyleAndAreaByTempletId(templateId);
	}

}
