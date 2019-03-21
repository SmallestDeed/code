package com.sandu.service.platform.impl;

import com.sandu.api.platform.model.BasePlatform;
import com.sandu.api.platform.service.BasePlatformService;
import com.sandu.service.platform.dao.BasePlatformMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WangHaiLin
 * @date 2018/6/4  21:16
 */
@Service("basePlatformService")
public class BasePlatformServiceImpl implements BasePlatformService {

    @Autowired
    private BasePlatformMapper basePlatformMapper;

    @Override
    public BasePlatform getByPlatformCode(String platformCode) {
        return basePlatformMapper.getByPlatformCode(platformCode);
    }
    
    /**
	 * @param id
	 * @return  platform 
	 */
	@Override
	public BasePlatform get(Integer id) {
		return basePlatformMapper.selectByPrimaryKey(id);
	}

	/**
	 * @param  platform
	 * @return   List<platform>
	 */
	@Override
	public List<BasePlatform> getList(BasePlatform platform) {
	    return basePlatformMapper.selectList(platform);
	}


    @Override
    public Map<String, List<Integer>> groupPlatformWithBussinessType(List<Integer> usedPlatformIds) {
		List<Integer> list2b = basePlatformMapper.groupPlatformWithBussinessType(usedPlatformIds, "2b");
		List<Integer> list2c = basePlatformMapper.groupPlatformWithBussinessType(usedPlatformIds, "2c");
		List<Integer> listsandu = basePlatformMapper.groupPlatformWithBussinessType(usedPlatformIds, "sandu");
		Map<String, List<Integer>> ret = new HashMap<>(3);
		ret.put("2b",list2b);
		ret.put("2c",list2c);
		ret.put("sandu",listsandu);
		return ret;
    }

	@Override
	public List<Integer> getPlatformIdsByBussinessTypes(List<String> bussinessTypes) {
		if(bussinessTypes.isEmpty()){
			return Collections.emptyList();
		}
		return basePlatformMapper.getPlatformIdsByBussinessTypes(bussinessTypes);
	}

    @Override
    public Integer getOnePlatformIdByBussinessType(String s) {
        return basePlatformMapper.getOnePlatformIdByBussinessType(s);
    }

	@Override
	public Map<Integer, String> getAllPlatformIdAndName() {
		List<Map<String,Object>> list = basePlatformMapper.getAllPlatformIdAndName();
		Map<Integer,String> map = new HashMap<>(list.size());
		for(Map<String,Object> tmp : list){
			map.put(Integer.parseInt(tmp.get("id").toString()),tmp.get("name").toString());
		}
		return map;
	}

    @Override
    public Map<Integer, String> getPlatformIdAndNameByBussinessType(String type) {
		List<BasePlatform> list = basePlatformMapper.getPlatformIdAndNameByBussinessType(type);
		Map<Integer,String> map = new HashMap<>(list.size());
		for(BasePlatform tmp : list){
			map.put(Integer.parseInt(tmp.getId().toString()),tmp.getPlatformName());
		}
		return map;
    }
}
