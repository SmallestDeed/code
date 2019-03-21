package com.nork.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.business.dao.HouseSpaceMapper;
import com.nork.business.model.HouseSpace;
import com.nork.business.model.search.HouseSpaceSearch;
import com.nork.business.service.HouseSpaceService;

/**   
 * @Title: HouseSpaceServiceImpl.java 
 * @Package com.nork.business.service.impl
 * @Description:业务-房型空间定义ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:58:14
 * @version V1.0   
 */
@Service("houseSpaceService")
@Transactional
public class HouseSpaceServiceImpl implements HouseSpaceService {

	private HouseSpaceMapper houseSpaceMapper;

	@Autowired
	public void setHouseSpaceMapper(
			HouseSpaceMapper houseSpaceMapper) {
		this.houseSpaceMapper = houseSpaceMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param houseSpace
	 * @return  int 
	 */
	@Override
	public int add(HouseSpace houseSpace) {
		houseSpaceMapper.insertSelective(houseSpace);
		return houseSpace.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param houseSpace
	 * @return  int 
	 */
	@Override
	public int update(HouseSpace houseSpace) {
		return houseSpaceMapper
				.updateByPrimaryKeySelective(houseSpace);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return houseSpaceMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  HouseSpace 
	 */
	@Override
	public HouseSpace get(Integer id) {
		return houseSpaceMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  houseSpace
	 * @return   List<HouseSpace>
	 */
	@Override
	public List<HouseSpace> getList(HouseSpace houseSpace) {
	    return houseSpaceMapper.selectList(houseSpace);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  houseSpace
	 * @return   int
	 */
	@Override
	public int getCount(HouseSpaceSearch houseSpaceSearch){
		return  houseSpaceMapper.selectCount(houseSpaceSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  houseSpace
	 * @return   List<HouseSpace>
	 */
	@Override
	public List<HouseSpace> getPaginatedList(
			HouseSpaceSearch houseSpaceSearch) {
		return houseSpaceMapper.selectPaginatedList(houseSpaceSearch);
	}

	@Override
	public List<HouseSpace> getSpaceHousePicList(HouseSpaceSearch houseSpaceSearch) {
		return houseSpaceMapper.spaceHousePicList(houseSpaceSearch);
	}
	
	@Override
	public int getSpaceHousePicCount(HouseSpaceSearch houseSpaceSearch) {
		return houseSpaceMapper.spaceHousePicCount(houseSpaceSearch);
	}
	@Override
	public List<HouseSpace> getMySpaceHousePicList(HouseSpaceSearch houseSpaceSearch) {
		return houseSpaceMapper.mySpaceHousePicList(houseSpaceSearch);
	}
	
	@Override
	public int getMySpaceHousePicCount(HouseSpaceSearch houseSpaceSearch) {
		return houseSpaceMapper.mySpaceHousePicCount(houseSpaceSearch);
	}

	@Override
	public List<String> getSpaceTypeListByHouseId(Integer id) {
		return houseSpaceMapper.getSpaceTypeListByHouseId(id);
	}

	@Override
	public List<Integer> getDistinctHouseIdWhereSpaceIdIsNotNull() {
		return houseSpaceMapper.getDistinctHouseIdWhereSpaceIdIsNotNull();
	}

	@Override
	public List<HouseSpace> getDeduplicationListList(HouseSpace houseSpace) {
		return houseSpaceMapper.selectDeduplicationList(houseSpace);
	}

	@Override
	public int getCountByType(HouseSpaceSearch houseSpaceSearch) {
		return houseSpaceMapper.selectCountByType(houseSpaceSearch);
	}


	@Override
	public Integer getHouseCountBySearch(HouseSpaceSearch houseSpaceSearch) {
		return houseSpaceMapper.getHouseCountBySearch(houseSpaceSearch);
	}

	@Override
	public int deleteByHouseId(Integer id) {
		return houseSpaceMapper.deleteByHouseId(id);
	}

	@Override
	public List<HouseSpace> getModifiedList(HouseSpace houseSpace) {
		return houseSpaceMapper.selectModifiedList(houseSpace);
	}
	/**
	 * 其他
	 * 
	 */

	@Override
	public Integer getLivingCountBySearch(HouseSpaceSearch houseSpaceSearch) {
		return houseSpaceMapper.getLivingCountBySearch(houseSpaceSearch);
	}

	@Override
	public Integer gainCountByType(HouseSpaceSearch houseSpaceSearch) {
		return houseSpaceMapper.getCountByType(houseSpaceSearch);
	}


}
