package com.nork.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.business.dao.RoomCommonMapper;
import com.nork.business.model.RoomCommon;
import com.nork.business.model.search.RoomCommonSearch;
import com.nork.business.service.RoomCommonService;

/**   
 * @Title: RoomCommonServiceImpl.java 
 * @Package com.nork.business.service.impl
 * @Description:业务-通用房型ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:08:11
 * @version V1.0   
 */
@Service("roomCommonService")
@Transactional
public class RoomCommonServiceImpl implements RoomCommonService {

	private RoomCommonMapper roomCommonMapper;

	@Autowired
	public void setRoomCommonMapper(
			RoomCommonMapper roomCommonMapper) {
		this.roomCommonMapper = roomCommonMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param roomCommon
	 * @return  int 
	 */
	@Override
	public int add(RoomCommon roomCommon) {
		roomCommonMapper.insertSelective(roomCommon);
		return roomCommon.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param roomCommon
	 * @return  int 
	 */
	@Override
	public int update(RoomCommon roomCommon) {
		return roomCommonMapper
				.updateByPrimaryKeySelective(roomCommon);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return roomCommonMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  RoomCommon 
	 */
	@Override
	public RoomCommon get(Integer id) {
		return roomCommonMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  roomCommon
	 * @return   List<RoomCommon>
	 */
	@Override
	public List<RoomCommon> getList(RoomCommon roomCommon) {
	    return roomCommonMapper.selectList(roomCommon);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  roomCommon
	 * @return   int
	 */
	@Override
	public int getCount(RoomCommonSearch roomCommonSearch){
		return  roomCommonMapper.selectCount(roomCommonSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  roomCommon
	 * @return   List<RoomCommon>
	 */
	@Override
	public List<RoomCommon> getPaginatedList(
			RoomCommonSearch roomCommonSearch) {
		return roomCommonMapper.selectPaginatedList(roomCommonSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
