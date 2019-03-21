package com.nork.cityunion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionDesignPlanStoreReleaseMapper;
import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseSearch;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;

/**   
 * @Title: UnionDesignPlanStoreReleaseServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟素材发布表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:43
 * @version V1.0   
 */
@Service("unionDesignPlanStoreReleaseService")
@EnableScheduling
public class UnionDesignPlanStoreReleaseServiceImpl implements UnionDesignPlanStoreReleaseService {

	private UnionDesignPlanStoreReleaseMapper unionDesignPlanStoreReleaseMapper;

	private static final Logger logger = LoggerFactory.getLogger(UnionDesignPlanStoreReleaseServiceImpl.class);
	
	@Autowired
	public void setUnionDesignPlanStoreReleaseMapper(
			UnionDesignPlanStoreReleaseMapper unionDesignPlanStoreReleaseMapper) {
		this.unionDesignPlanStoreReleaseMapper = unionDesignPlanStoreReleaseMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStoreRelease
	 * @return  int 
	 */
	@Override
	public int add(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease) {
		unionDesignPlanStoreReleaseMapper.insertSelective(unionDesignPlanStoreRelease);
		return unionDesignPlanStoreRelease.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStoreRelease
	 * @return  int 
	 */
	@Override
	public int update(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease) {
		return unionDesignPlanStoreReleaseMapper
				.updateByPrimaryKeySelective(unionDesignPlanStoreRelease);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionDesignPlanStoreReleaseMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionDesignPlanStoreRelease 
	 */
	@Override
	public UnionDesignPlanStoreRelease get(Integer id) {
		return unionDesignPlanStoreReleaseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStoreRelease
	 * @return   List<UnionDesignPlanStoreRelease>
	 */
	@Override
	public List<UnionDesignPlanStoreRelease> getList(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease) {
	    return unionDesignPlanStoreReleaseMapper.selectList(unionDesignPlanStoreRelease);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStoreRelease
	 * @return   int
	 */
	@Override
	public int getCount(UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch){
		return  unionDesignPlanStoreReleaseMapper.selectCount(unionDesignPlanStoreReleaseSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreRelease
	 * @return   List<UnionDesignPlanStoreRelease>
	 */
	@Override
	public List<UnionDesignPlanStoreRelease> getPaginatedList(
			UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch) {
		return unionDesignPlanStoreReleaseMapper.selectPaginatedList(unionDesignPlanStoreReleaseSearch);
	}

	/**
	 * 自动增加人气值配置
	 */
	private static final Map<Integer, UpdatePopularityValueConfig>  updatePopularityValueConfigMap = updatePopularityValueConfigMap();
	
	@Override
	@Scheduled(fixedRate = 600000)
	public void updatePopularityValue() {
		logger.info("开始自动增加人气值...");
		
		// 查询人气值大于20并且最近一个月的union_design_plan_store_release数据 ->start
		List<UnionDesignPlanStoreRelease> unionDesignPlanStoreReleaseList = this.getByParam(20, Utils.getDateBefore(new Date(), 30));
		// 查询人气值大于20并且最近一个月的union_design_plan_store_release数据 ->end
		
		if(Lists.isEmpty(unionDesignPlanStoreReleaseList)) {
			return;
		}
		
		// 0-8点,每10分钟增加1-2人气值,8-24点,每次增加1-5人气值 ->start
		Date date = new Date();
		unionDesignPlanStoreReleaseList.forEach(item ->{
			Integer value = this.getValue(updatePopularityValueConfigMap.get(Utils.getHour(date)));
			item.setPopularityValue((item.getPopularityValue() == null ? 0 : item.getPopularityValue()) + value);
		});
		
		this.update(unionDesignPlanStoreReleaseList);
		// 0-8点,每10分钟增加1-2人气值,8-24点,每次增加1-5人气值 ->start
		logger.info("自动增加人气值结束...");
	}

	
	private void update(List<UnionDesignPlanStoreRelease> unionDesignPlanStoreReleaseList) {
		// 参数验证 ->start
		if(Lists.isEmpty(unionDesignPlanStoreReleaseList)) {
			return;
		}
		// 参数验证 ->end
		
		unionDesignPlanStoreReleaseList.forEach(item -> {
			unionDesignPlanStoreReleaseMapper.updatePopularityValue(item);
		});
	}

	private List<UnionDesignPlanStoreRelease> getByParam(Integer popularityValue, Date dateBefore) {
		// 参数验证 ->start
		if(popularityValue == null) {
			return null;
		}
		if(dateBefore == null) {
			return null;
		}
		// 参数验证 -end
		
		return unionDesignPlanStoreReleaseMapper.selectByPopularityValueAndDateBefore(popularityValue, dateBefore);
	}

	/**
	 * 获取人气值增加的随机数
	 * 
	 * 
	 * @param updatePopularityValueConfig
	 * @return
	 */
	private Integer getValue(UpdatePopularityValueConfig updatePopularityValueConfig) {
		// 参数验证 ->start
		if(updatePopularityValueConfig == null) {
			return 0;
		}
		if(updatePopularityValueConfig.getMinValue() == null) {
			return 0;
		}
		if(updatePopularityValueConfig.getMaxValue() == null) {
			return 0;
		}
		// 参数验证 ->end
		
		return Utils.getRandomInteger(updatePopularityValueConfig.getMinValue(), updatePopularityValueConfig.getMaxValue());
	}

	public static Map<Integer, UpdatePopularityValueConfig> updatePopularityValueConfigMap() {
		List<UpdatePopularityValueConfig> updatePopularityValueConfigList = getupdatePopularityValueConfig();
		Map<Integer, UpdatePopularityValueConfig> resultMap = new HashMap<>();
		for(int i = 0; i < 24; i ++) {
			resultMap.put(i, getUpdatePopularityValueConfigByHour(i, updatePopularityValueConfigList));
		}
		return resultMap;
	}

	private static UpdatePopularityValueConfig getUpdatePopularityValueConfigByHour(Integer hour, List<UpdatePopularityValueConfig> updatePopularityValueConfigList) {
		UpdatePopularityValueConfig updatePopularityValueConfig = new UpdatePopularityValueConfig(null, null, 0, 0);
		// 参数验证 ->start
		if(hour == null) {
			throw new RuntimeException("hour = null");
		}
		if(updatePopularityValueConfigList == null || updatePopularityValueConfigList.size() < 1) {
			throw new RuntimeException("(updatePopularityValueConfigList == null || updatePopularityValueConfigList.size() < 1) = true");
		}
		// 参数验证 ->end
		
		for(UpdatePopularityValueConfig item : updatePopularityValueConfigList) {
			Integer startTime = item.getStartTime();
			Integer endTime = item.getEndTime();
			if(startTime == null) {
				throw new RuntimeException("startTime = null");
			}
			if(endTime == null) {
				throw new RuntimeException("endTime = null");
			}
			if(hour >= startTime && hour < endTime) {
				updatePopularityValueConfig.setMinValue(item.getMinValue());
				updatePopularityValueConfig.setMaxValue(item.getMaxValue());
				return updatePopularityValueConfig;
			}
		}
			
		return updatePopularityValueConfig;
	}

	private static List<UpdatePopularityValueConfig> getupdatePopularityValueConfig() {
		List<UpdatePopularityValueConfig> resultlist = new ArrayList<>();
		UpdatePopularityValueConfig config01 = new UpdatePopularityValueConfig(0, 8, 1, 2);
		UpdatePopularityValueConfig config02 = new UpdatePopularityValueConfig(8, 24, 1, 5);
		resultlist.add(config01);
		resultlist.add(config02);
		return resultlist;
	}
	
	

	/**
	 * 自动更新人气值配置
	 * 
	 * @author huangsongbo
	 *
	 */
	public static class UpdatePopularityValueConfig{
		
		private Integer startTime;
		
		private Integer endTime;
		
		private Integer minValue;
		
		private Integer maxValue;

		public UpdatePopularityValueConfig(Integer startTime, Integer endTime, Integer minValue, Integer maxValue) {
			super();
			this.startTime = startTime;
			this.endTime = endTime;
			this.minValue = minValue;
			this.maxValue = maxValue;
		}

		public Integer getStartTime() {
			return startTime;
		}

		public void setStartTime(Integer startTime) {
			this.startTime = startTime;
		}

		public Integer getEndTime() {
			return endTime;
		}

		public void setEndTime(Integer endTime) {
			this.endTime = endTime;
		}

		public Integer getMinValue() {
			return minValue;
		}

		public void setMinValue(Integer minValue) {
			this.minValue = minValue;
		}

		public Integer getMaxValue() {
			return maxValue;
		}

		public void setMaxValue(Integer maxValue) {
			this.maxValue = maxValue;
		}
		
	}
	
}
