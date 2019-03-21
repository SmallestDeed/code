package com.sandu.service.fullhouse.impl;

import com.sandu.api.base.common.Utils;
import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.designTemplet.model.DesignTemplet;
import com.sandu.api.designTemplet.service.DesignTempletService;
import com.sandu.api.designplan.model.DesignPlanRecommended;
import com.sandu.api.designplan.service.DesignPlanRecommendedService;
import com.sandu.api.fullhouse.input.FullHouseDesignPlanQuery;
import com.sandu.api.fullhouse.model.FullHouseDesignPlan;
import com.sandu.api.fullhouse.output.MatchInfoVO;
import com.sandu.api.fullhouse.output.FullHouseDesignPlanVO;
import com.sandu.api.fullhouse.service.FullHouseDesignPlanService;
import com.sandu.service.fullhouse.dao.FullHouseDesignPlanMapper;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by zhangchengda
 * 2018/8/15 18:28
 * 全屋方案表单表查询服务
 */
@Service("fullHouseDesignPlanService")
@Log4j2(topic = "[全屋方案表服务]")
public class FullHouseDesignPlanServiceImpl implements FullHouseDesignPlanService {
	
	private static final String LOGPREFIX = "[全屋装修方案模块]:";
	
    @Autowired
    private FullHouseDesignPlanMapper fullHouseDesignPlanMapper;
    private static final int DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_FULL_HOUSE_DESIGN_PLAN_PAGE_SIZE = 10;
    private static final int DEFAULT_START = 0;

    @Autowired
    private DesignTempletService designTempletService;
    
    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;
    
    /**
     * created by zhangchengda
     * 2018/8/15 18:28
     * 根据主键ID删除数据
     * @param id 全屋方案表id
     * @return 返回影响条数
     */
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return fullHouseDesignPlanMapper.deleteByPrimaryKey(id);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 18:28
     * 插入一条全屋方案
     * @param record 插入的数据
     * @return 返回影响条数
     */
    @Override
    public int insert(FullHouseDesignPlan record) {
        return fullHouseDesignPlanMapper.insert(record);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 18:29
     * 动态插入一条全屋方案
     * @param record 插入的数据
     * @return 返回影响条数
     */
    @Override
    public int insertSelective(FullHouseDesignPlan record) {
        return fullHouseDesignPlanMapper.insertSelective(record);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 18:32
     * 根据主键查询数据
     * @param id 全屋方案表id
     * @return 返回一行数据
     */
    @Override
    public FullHouseDesignPlan selectByPrimaryKey(Integer id) {
        return fullHouseDesignPlanMapper.selectByPrimaryKey(id);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 18:33
     * 根据主键动态更新数据
     * @param record 更新的数据
     * @return 返回影响条数
     */
    @Override
    public int updateByPrimaryKeySelective(FullHouseDesignPlan record) {
        return fullHouseDesignPlanMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 18:33
     * 根据主键更新数据
     * @param record 更新的数据
     * @return 返回影响条数
     */
    @Override
    public int updateByPrimaryKey(FullHouseDesignPlan record) {
        return fullHouseDesignPlanMapper.updateByPrimaryKey(record);
    }
    
    @Override
    public List<MatchInfoVO> getMatchInfo(
    		Integer houseId,
    		Integer fullHousePlanId
    		) throws BizExceptionEE {
    	String throwLogPrefix = "获取全屋装修匹配信息失败";
    	
    	// 参数验证 ->start
    	if(houseId == null) {
    		String logStr = "params error: houseId = null";
    		log.error(LOGPREFIX + logStr);
    		throw new BizExceptionEE(throwLogPrefix + "(" + logStr + ")");
    	}
    	if(fullHousePlanId == null) {
    		String logStr = "params error: fullHousePlanId = null";
    		log.error(LOGPREFIX + logStr);
    		throw new BizExceptionEE(throwLogPrefix + "(" + logStr + ")");
    	}
    	// 参数验证 ->end
    	
    	// 获取户型关联的样板房信息
    	List<DesignTemplet> designTempletList = designTempletService.getListByHouseId(houseId);
    	if(Utils.isEmpty(designTempletList)) {
    		String logStr = "Utils.isEmpty(designTempletList) = true; houseId = " + houseId;
    		log.error(LOGPREFIX + logStr);
    		throw new BizExceptionEE(throwLogPrefix + "(" + logStr + ")");
    	}
    	
    	// 获取全屋装修方案关联的所有主方案信息
    	List<DesignPlanRecommended> designPlanRecommendedList = designPlanRecommendedService.getListByFullHouseId(fullHousePlanId);
    	if(Utils.isEmpty(designPlanRecommendedList)) {
    		String logStr = "Utils.isEmpty(designPlanRecommendedList) = true; fullHousePlanId = " + fullHousePlanId;
    		log.error(LOGPREFIX + logStr);
    		throw new BizExceptionEE(throwLogPrefix + "(" + logStr + ")");
    	}
    	
    	List<MatchInfoVO> matchInfoVOList = this.getMatchInfo(designTempletList, designPlanRecommendedList);
		return matchInfoVOList;
    }

    @Override
	public List<MatchInfoVO> getMatchInfo(List<DesignTemplet> designTempletList,
			List<DesignPlanRecommended> designPlanRecommendedList) throws BizExceptionEE {
    	String throwLogPrefix = "全屋装修匹配错误";
    	
    	// 参数验证 ->start
    	if(Utils.isEmpty(designTempletList)) {
    		String logStr = "params error: Utils.isEmpty(designTempletList) = true";
    		log.error(LOGPREFIX + logStr);
    		throw new BizExceptionEE(throwLogPrefix + "(" + logStr + ")");
    	}
    	
    	// 获取全屋装修方案关联的所有主方案信息
    	if(Utils.isEmpty(designPlanRecommendedList)) {
    		String logStr = "params error: Utils.isEmpty(designPlanRecommendedList) = true";
    		log.error(LOGPREFIX + logStr);
    		throw new BizExceptionEE(throwLogPrefix + "(" + logStr + ")");
    	}
    	// 参数验证 ->end
    	
    	// 获取key = 空间类型value, value = List<DesignTemplet>的样板房信息map
    	Map<Integer, List<DesignTemplet>> designTempletMap = designTempletService.getDesignTempletMap(designTempletList);
    	
    	Map<Integer, List<DesignPlanRecommended>> designPlanRecommendedMap = designPlanRecommendedService.getDesignPlanRecommendedMap(designPlanRecommendedList);
    	
    	List<MatchInfoVO> matchInfoVOList = new ArrayList<MatchInfoVO>();
    	
    	for(Integer houseTypeValue : designTempletMap.keySet()) {
    		List<DesignTemplet> designTempletListFromMap = designTempletMap.get(houseTypeValue);
    		if(Utils.isEmpty(designTempletListFromMap)) {
    			continue;
    		}
    		
    		List<DesignPlanRecommended> designPlanRecommendedListFromMap = designPlanRecommendedMap.get(houseTypeValue);
    		if(designPlanRecommendedListFromMap == null) {
    			// 推荐方案没有同空间类型
    			continue;
    		}
    		
    		int designPlanRecommendedListSize = designPlanRecommendedListFromMap.size();
    		
    		for (int i = 0; i < designTempletListFromMap.size(); i++) {
				DesignTemplet designTemplet = designTempletListFromMap.get(i);
				
				/* 
				 	根据index取方案组信息(主方案信息)
				 	逻辑:
				 	eg: 
				 	designTempletListFromMap.size = 2(1,2), designPlanRecommendedListFromMap.size = 3(1,2,3)
				 	则:
				 	designTemplet	designPlanRecommended
				 	1						1
				 	2						2
				 	
				 	eg: 
				 	designTempletListFromMap.size = 3(1,2,3), designPlanRecommendedListFromMap.size = 2(1,2)
				 	则:
				 	designTemplet	designPlanRecommended
				 	1						1
				 	2						2
				 	3						2
				 	
				 	备注:
				 	designTempletListFromMap:面积从大到小排序
				 	designPlanRecommendedListFromMap:优先级排序
				 */
				DesignPlanRecommended designPlanRecommendedMain = null;
				if(designPlanRecommendedListSize > i) {
					designPlanRecommendedMain = designPlanRecommendedListFromMap.get(i);
				}else {
					designPlanRecommendedMain = designPlanRecommendedListFromMap.get(designPlanRecommendedListSize - 1);
				}
				
				// 样板房与方案包匹配,返回面积最适配的推荐方案id
				// 获取匹配时所需要的条件:样板房面积(考虑实际面积(去除玄关/过道的面积)和总面积)
				Integer areaValue = designTempletService.getAreaValue(designTemplet);
				Integer designPlanRecommendedId = designPlanRecommendedService.getMatchId(areaValue, designPlanRecommendedMain.getId());
				matchInfoVOList.add(
						new MatchInfoVO((designTemplet.getId() == null ? null : designTemplet.getId().intValue()), 
								designPlanRecommendedId,
								designTemplet.getSpaceFunctionId(),
								designTemplet.getSpaceCode(),
								designTemplet.getDesignCode(),
								designTemplet.getSpaceCommonId()
								));
			}
    		
    	}
    	
		return matchInfoVOList;
	}
    
    /**
     * created by zhangchengda
     * 2018/8/17 11:05
     * 查询当前登录用户制作的符合查询条件的全屋方案
     * @param query 查询参数
     * @return 全屋方案集合
     */
    @Override
    public List<FullHouseDesignPlanVO> selectFullHouseDesignPlan(FullHouseDesignPlanQuery query) {
        // 处理分页
        if (query.getCurPage() == null || query.getCurPage() <= 0){
            query.setCurPage(DEFAULT_CURRENT_PAGE);
        }
        if (query.getPageSize() == null || query.getPageSize() <= 0){
            query.setPageSize(DEFAULT_FULL_HOUSE_DESIGN_PLAN_PAGE_SIZE);
        }
        if (query.getPageSize() != null && query.getCurPage() != null){
            query.setStart((query.getCurPage() - 1) * query.getPageSize());
        }else {
            query.setStart(DEFAULT_START);
        }
        return fullHouseDesignPlanMapper.selectFullHouseDesignPlan(query);
    }

    /**
     * created by zhangchengda
     * 2018/8/17 11:05
     * 查询当前登录用户制作的符合查询条件的全屋方案总数
     * @param query 查询参数
     * @return 全屋方案总数
     */
    @Override
    public Integer selectFullHouseDesignPlanCount(FullHouseDesignPlanQuery query) {
        // 处理分页
        query.setCurPage(null);
        query.setPageSize(null);
        query.setStart(null);
        return fullHouseDesignPlanMapper.selectFullHouseDesignPlanCount(query);
    }

    /**
     * created by zhangchengda
     * 2018/8/18 14:14
     * 通过UUID查询全屋方案
     * @param uuid 全屋方案的UUID
     * @return 全屋方案
     */
    @Override
    public FullHouseDesignPlan selectFullHouseDesignPlanByUuid(String uuid) {
        return fullHouseDesignPlanMapper.selectFullHouseDesignPlanByUuid(uuid);
    }

    /**
     * created by zhangchengda
     * 2018/8/18 19:06
     * 逻辑删除全屋方案，is_deleted = 1
     * @param id 将要删除的全屋方案ID
     * @return 返回影响条数
     */
    @Override
    public int deleteFullHouseDesignPlan(Integer id) {
        return fullHouseDesignPlanMapper.logicDeleteFullHouseDesignPlan(id);
    }
    
}
