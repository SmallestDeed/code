package com.nork.resource.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.resource.dao.ResPicHouseMapper;
import com.nork.resource.model.ResPicHouse;
import com.nork.resource.model.search.ResPicHouseSearch;
import com.nork.resource.service.ResPicHouseService;


/**   
 * @Title: ResPicServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-图片资源库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:06:59
 * @version V1.0   
 */
@Service("resPicHouseService")
@Transactional
public class ResPicHouseServiceImpl implements ResPicHouseService {

	private ResPicHouseMapper resPicHouseMapper;

	@Autowired
	public void setResPicHouseMapper(
			ResPicHouseMapper resPicHouseMapper) {
		this.resPicHouseMapper = resPicHouseMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param resPic
	 * @return  int 
	 */
	@Override
	public int add(ResPicHouse resPicHouse) {
		resPicHouseMapper.insertSelective(resPicHouse);
		return resPicHouse.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param resPic
	 * @return  int 
	 */
	@Override
	public int update(ResPicHouse resPicHouse) {
		return resPicHouseMapper
				.updateByPrimaryKeySelective(resPicHouse);
	}

	
	/**
	 *    获取数据数量
	 *
	 * @param  resPic
	 * @return   int
	 */
	@Override
	public int getCount(ResPicHouseSearch resPicHouseSearch){
		return  resPicHouseMapper.selectCount(resPicHouseSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resPic
	 * @return   List<ResPic>
	 */
	@Override
	public List<ResPicHouse> getPaginatedList(ResPicHouseSearch resPicHouseSearch) {
	
		return resPicHouseMapper.selectPaginatedList(resPicHouseSearch);
	}

	@Override
	public int delete(Integer id) {
		return resPicHouseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ResPicHouse get(Integer id) {
		return resPicHouseMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取上一条房型图片ID
	 * @param resPicHouseId
	 * @return
	 */
	public ResPicHouse getPrevResPicHouseId(Integer resPicHouseId){
		
		return resPicHouseMapper.getPrevResPicHouseId(resPicHouseId);
	}
	
	/**
	 * 获取下一条房型图片ID
	 * @param resPicHouseId
	 * @return
	 */
	public ResPicHouse getNextResPicHouseId(Integer resPicHouseId) {
		
		return resPicHouseMapper.getNextResPicHouseId(resPicHouseId);
	}

}
