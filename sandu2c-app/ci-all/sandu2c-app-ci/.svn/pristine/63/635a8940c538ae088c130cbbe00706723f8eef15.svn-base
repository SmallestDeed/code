package com.sandu.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.platform.BasePlatform;
import com.sandu.platform.dao.BasePlatformMapper;
import com.sandu.platform.search.BasePlatformSearch;
import com.sandu.system.service.BasePlatformService;


/**   
 * @Title: BasePlatformServiceImpl.java 
 * @Package com.nork.platform.service.impl
 * @Description:基础-平台表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
@Service("basePlatformService")
public class BasePlatformServiceImpl implements BasePlatformService {

	private BasePlatformMapper basePlatformMapper;

	@Autowired
	public void setBasePlatformMapper(
			BasePlatformMapper basePlatformMapper) {
		this.basePlatformMapper = basePlatformMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param basePlatform
	 * @return  int 
	 */
	@Override
	public int add(BasePlatform basePlatform) {
		basePlatformMapper.insertSelective(basePlatform);
		return basePlatform.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param basePlatform
	 * @return  int 
	 */
	@Override
	public int update(BasePlatform basePlatform) {
		return basePlatformMapper
				.updateByPrimaryKeySelective(basePlatform);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return basePlatformMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    逻辑删除
	 */
	public int deleteById(Integer id){
		return basePlatformMapper.deleteById(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BasePlatform 
	 */
	@Override
	public BasePlatform get(Integer id) {
		return basePlatformMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  basePlatform
	 * @return   List<BasePlatform>
	 */
	@Override
	public List<BasePlatform> getList(BasePlatform basePlatform) {
	    return basePlatformMapper.selectList(basePlatform);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  basePlatform
	 * @return   int
	 */
	@Override
	public int getCount(BasePlatformSearch basePlatformSearch){
		return  basePlatformMapper.selectCount(basePlatformSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  basePlatform
	 * @return   List<BasePlatform>
	 */
	@Override
	public List<BasePlatform> getPaginatedList(
			BasePlatformSearch basePlatformSearch) {
		return basePlatformMapper.selectPaginatedList(basePlatformSearch);
	}

    @Override
    public BasePlatform getByPlatformCode(String platformCode) {
        return basePlatformMapper.getByPlatformCode(platformCode);
    }

    @Override
    public Map<String, List> groupPlatformWithBussinessType(List<Integer> usedPlatformIds) {
		List<Integer> list2b = basePlatformMapper.groupPlatformWithBussinessType(usedPlatformIds, "2b");
		List<Integer> list2c = basePlatformMapper.groupPlatformWithBussinessType(usedPlatformIds, "2c");
		List<Integer> listsandu = basePlatformMapper.groupPlatformWithBussinessType(usedPlatformIds, "sandu");
		Map ret = new HashMap();
		ret.put("2b",list2b);
		ret.put("2c",list2c);
		ret.put("sandu",listsandu);
		return ret;
    }

	@Override
	public BasePlatform getBasePlatform(String platformCode) {
		
		return basePlatformMapper.selectPlatformInfoByPlatformCode(platformCode);
	}

    /**
	 * 其他
	 * 
	 */


}
