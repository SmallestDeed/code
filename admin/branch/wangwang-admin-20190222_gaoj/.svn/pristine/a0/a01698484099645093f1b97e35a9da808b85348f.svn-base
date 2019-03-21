package com.sandu.service.platform.impl;

import com.sandu.api.platform.model.Platform;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.service.platform.dao.PlatformDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**   
 * @author Sandu
 * @Title: PlatformServiceImpl.java
 * @Package com.nork.platform.service.impl
 * @Description:基础-平台表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
@Service("platformService")
public class PlatformServiceImpl implements PlatformService {
	
	
	public static final Map<Long, String> platformMap = new ConcurrentHashMap<>();


	@Autowired
	private PlatformDao platformDao;

	/**
	 * 新增数据
	 *
	 * @param platform
	 * @return  int 
	 */
	@Override
	public long add(Platform platform) {
		platformDao.insertSelective(platform);
		return platform.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param platform
	 * @return  int 
	 */
	@Override
	public int update(Platform platform) {
		return platformDao
				.updateByPrimaryKeySelective(platform);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return platformDao.deleteByPrimaryKey(id);
	}

	/**
	 *    逻辑删除
	 */
	public int deleteById(Integer id){
		return platformDao.deleteById(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  platform 
	 */
	@Override
	public Platform get(Integer id) {
		return platformDao.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  platform
	 * @return   List<platform>
	 */
	@Override
	public List<Platform> getList(Platform platform) {
	    return platformDao.selectList(platform);
	}


    @Override
    public Platform getByPlatformCode(String platformCode) {
        return platformDao.getByPlatformCode(platformCode);
    }

    @Override
    public Map<String, List<Integer>> groupPlatformWithBussinessType(List<Integer> usedPlatformIds) {
		List<Integer> list2b = platformDao.groupPlatformWithBussinessType(usedPlatformIds, "2b");
		List<Integer> list2c = platformDao.groupPlatformWithBussinessType(usedPlatformIds, "2c");
		List<Integer> listsandu = platformDao.groupPlatformWithBussinessType(usedPlatformIds, "sandu");
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
		return platformDao.getPlatformIdsByBussinessTypes(bussinessTypes);
	}

    @Override
    public Integer getOnePlatformIdByBussinessType(String s) {
        return platformDao.getOnePlatformIdByBussinessType(s);
    }

	@Override
	public Map<Integer, String> getAllPlatformIdAndName() {
		List<Map<String,Object>> list = platformDao.getAllPlatformIdAndName();
		Map<Integer,String> map = new HashMap<>(list.size());
		for(Map<String,Object> tmp : list){
			map.put(Integer.parseInt(tmp.get("id").toString()),tmp.get("name").toString());
		}
		return map;
	}

    @Override
    public Map<Integer, String> getPlatformIdAndNameByBussinessType(String type) {
		List<Platform> list = platformDao.getPlatformIdAndNameByBussinessType(type);
		Map<Integer,String> map = new HashMap<>(list.size());
		for(Platform tmp : list){
			map.put(Integer.parseInt(tmp.getId().toString()),tmp.getPlatformName());
		}
		return map;
    }

	@Override
	public Map<Integer, String> getUpDownPlatformIdAndName() {
		List<Map<String,Object>> list = platformDao.getUpDownPlatformIdAndName();
		Map<Integer,String> map = new HashMap<>(list.size());
		for(Map<String,Object> tmp : list){
			map.put(Integer.parseInt(tmp.get("id").toString()),tmp.get("name").toString());
		}
		return map;
	}

	@Override
	public String getPlatformBussinessTypeById(String platformId) {
		if(platformMap.isEmpty()) {
			List<Platform> dataList = this.getList(new Platform());
			platformMap.putAll(
					dataList.stream().collect(Collectors.toMap(Platform::getId, Platform::getPlatformBussinessType)));
		}
		return platformMap.get(Long.parseLong(platformId));
	}

}
