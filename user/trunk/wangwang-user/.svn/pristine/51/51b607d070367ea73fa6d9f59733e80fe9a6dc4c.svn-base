package com.sandu.service.system.impl;

import com.sandu.api.redis.RedisService;
import com.sandu.api.system.input.SysDictionarySearch;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.output.SysDictionaryVO;
import com.sandu.api.system.service.SysDictionaryService;
import com.sandu.service.system.dao.SysDictionaryDao;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("sysDictionaryService")
@Log4j2
public class SysDictionaryServiceImpl implements SysDictionaryService {

	private final static String LOGPREFIX = "[数据字典模块]: ";
	
    @Autowired
    private SysDictionaryDao sysDictionaryDao;

    @Autowired
    private RedisService redisService;
    
    @Override
    public List<SysDictionary> getList(SysDictionary sysDictionary) {
        return sysDictionaryDao.selectList(sysDictionary);
    }

    @Override
    public SysDictionary getSysDictionary(String type, Integer value) {
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setIsDeleted(0);
        sysDictionary.setType(type);
        sysDictionary.setValue(value);
        List<SysDictionary> list = sysDictionaryDao.selectList(sysDictionary);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<SysDictionary> getListByType(String type) {
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setType(type);
        List<SysDictionary> sysDictionaries = sysDictionaryDao.selectList(sysDictionary);
        if (sysDictionaries != null && sysDictionaries.size() > 0) {
            return sysDictionaries;
        }
        return null;
    }

    @Override
    public SysDictionary findByTypeAndValueKey(String type, String valueKey) {
        return sysDictionaryDao.findByTypeAndValueKey(type,valueKey);
    }

	@Override
	public List<SysDictionaryVO> getSysDictionaryVOList(String type) {
		String key = this.getRedisKey(type);
		
		@SuppressWarnings("unchecked")
		List<SysDictionaryVO> returnList = (List<SysDictionaryVO>) redisService.getObject(key, List.class);
		
		if(returnList == null) {
			// 我只能从db查数据了
			SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
			sysDictionarySearch.setType(type);
			sysDictionarySearch.setIsDeleted(0);
			List<SysDictionary> sysDictionaryList = this.getList(sysDictionarySearch);
			
			// SysDictionary -> SysDictionaryVO
			returnList = this.getSysDictionaryVOList(sysDictionaryList);
			
			// 放到缓存里(放30分钟, 让他30分钟更新一次)
			redisService.setObject(key, returnList, 30 * 60);
		}
		
		return returnList;
	}
	
	@Override
	public List<SysDictionary> getList(SysDictionarySearch sysDictionarySearch) {
		// ------参数验证 ->start
		if(sysDictionarySearch == null) {
			log.error(LOGPREFIX + "sysDictionarySearch = null");
			return null;
		}
		// ------参数验证 ->end
		
		return sysDictionaryDao.selectListBySearch(sysDictionarySearch);
	}

	/**
	 * 获取List<SysDictionaryVO>数据对应的缓存对应的key
	 * 
	 * @param type
	 * @return
	 */
	private String getRedisKey(String type) {
		// type处理一下
		type = type == null ? "" : type;
		
		String key = "sysDictionary:sysDictionaryVOList_type_" + type;
		
		return key;
	}
	
	/**
	 * List<SysDictionary> -> List<SysDictionaryVO>
	 * 
	 * @author huangsongbo
	 * @param sysDictionaryList
	 * @return
	 */
	private List<SysDictionaryVO> getSysDictionaryVOList(List<SysDictionary> sysDictionaryList) {
		Optional<List<SysDictionary>> listOpt = Optional.ofNullable(sysDictionaryList);
		return listOpt.map(list -> list.stream().map(item -> this.getSysDictionaryVO(item)).collect(Collectors.toList())).orElse(null);
	}

	/**
	 * SysDictionary -> SysDictionaryVO
	 * 
	 * @param sysDictionary
	 * @return
	 */
	private SysDictionaryVO getSysDictionaryVO(SysDictionary sysDictionary) {
		if(sysDictionary == null) {
			return null;
		}
		
		SysDictionaryVO sysDictionaryVO = new SysDictionaryVO();
		sysDictionaryVO.setName(sysDictionary.getName());
		sysDictionaryVO.setValue(sysDictionary.getValue());
		return sysDictionaryVO;
	}

	@Override
	public List<SysDictionary> getListByTypeAndValues(String userType, List<Integer> userTypeValue) {
		return sysDictionaryDao.getListByTypeAndValues(userType,userTypeValue);
	}
}
