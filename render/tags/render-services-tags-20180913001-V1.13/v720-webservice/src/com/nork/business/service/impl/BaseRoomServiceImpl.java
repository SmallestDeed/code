package com.nork.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.business.dao.BaseRoomMapper;
import com.nork.business.model.BaseRoom;
import com.nork.business.model.search.BaseRoomSearch;
import com.nork.business.service.BaseRoomService;

/**   
 * @Title: BaseRoomServiceImpl.java 
 * @Package com.nork.business.service.impl
 * @Description:业务-房型ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:04:29
 * @version V1.0   
 */
@Service("baseRoomService")
@Transactional
public class BaseRoomServiceImpl implements BaseRoomService {
    @Autowired
	private BaseRoomMapper baseRoomMapper;
	/**
	 * 新增数据
	 *
	 * @param baseRoom
	 * @return  int 
	 */
	@Override
	public int add(BaseRoom baseRoom) {
		baseRoomMapper.insertSelective(baseRoom);
		return baseRoom.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseRoom
	 * @return  int 
	 */
	@Override
	public int update(BaseRoom baseRoom) {
		return baseRoomMapper
				.updateByPrimaryKeySelective(baseRoom);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseRoomMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseRoom 
	 */
	@Override
	public BaseRoom get(Integer id) {
		return baseRoomMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseRoom
	 * @return   List<BaseRoom>
	 */
	@Override
	public List<BaseRoom> getList(BaseRoom baseRoom) {
	    return baseRoomMapper.selectList(baseRoom);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseRoom
	 * @return   int
	 */
	@Override
	public int getCount(BaseRoomSearch baseRoomSearch){
		return  baseRoomMapper.selectCount(baseRoomSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseRoom
	 * @return   List<BaseRoom>
	 */
	@Override
	public List<BaseRoom> getPaginatedList(
			BaseRoomSearch baseRoomSearch) {
		return baseRoomMapper.selectPaginatedList(baseRoomSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
