package com.nork.design.service.impl;

import java.util.List;

import com.nork.design.model.DesignRenderRoam;
import com.nork.design.model.search.DesignRenderRoamSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.design.dao.DesignRenderRoamMapper;
import com.nork.design.service.DesignRenderRoamService;

/**   
 * @Title: DesignRenderRoamServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:渲染漫游-720漫游ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-07-13 17:41:16
 * @version V1.0   
 */
@Service("designRenderRoamService")
public class DesignRenderRoamServiceImpl implements DesignRenderRoamService {

	private DesignRenderRoamMapper designRenderRoamMapper;

	@Autowired
	public void setDesignRenderRoamMapper(
			DesignRenderRoamMapper designRenderRoamMapper) {
		this.designRenderRoamMapper = designRenderRoamMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param designRenderRoam
	 * @return  int 
	 */
	@Override
	public int add(DesignRenderRoam designRenderRoam) {
		designRenderRoamMapper.insertSelective(designRenderRoam);
		return designRenderRoam.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designRenderRoam
	 * @return  int 
	 */
	@Override
	public int update(DesignRenderRoam designRenderRoam) {
		return designRenderRoamMapper
				.updateByPrimaryKeySelective(designRenderRoam);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return designRenderRoamMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignRenderRoam 
	 */
	@Override
	public DesignRenderRoam get(Integer id) {
		return designRenderRoamMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designRenderRoam
	 * @return   List<DesignRenderRoam>
	 */
	@Override
	public List<DesignRenderRoam> getList(DesignRenderRoam designRenderRoam) {
	    return designRenderRoamMapper.selectList(designRenderRoam);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  designRenderRoamSearch
	 * @return   int
	 */
	@Override
	public int getCount(DesignRenderRoamSearch designRenderRoamSearch){
		return  designRenderRoamMapper.selectCount(designRenderRoamSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  designRenderRoamSearch
	 * @return   List<DesignRenderRoam>
	 */
	@Override
	public List<DesignRenderRoam> getPaginatedList(
			DesignRenderRoamSearch designRenderRoamSearch) {
		return designRenderRoamMapper.selectPaginatedList(designRenderRoamSearch);
	}

	/**
	 * 其他
	 * 
	 */

	/**
	 * 通过UUID查询一个漫游
	 * @param uuid
	 * @return
	 */
	@Override
	public DesignRenderRoam selectByUUID(String uuid){
		return designRenderRoamMapper.selectByUUID(uuid);
	}

	/**
	 * 通过截图ID查询一个720漫游
	 * @param screenId
	 * @return
	 */
	@Override
	public DesignRenderRoam selectByScreenId(Integer screenId){
		return designRenderRoamMapper.selectByScreenId(screenId);
	}

	@Override
	public int countByScreenShotId(int screenShotId) {
		DesignRenderRoam designRenderRoam =new DesignRenderRoam();
		designRenderRoam.setScreenShotId(screenShotId);
		designRenderRoam = designRenderRoamMapper.countByScreenShotId(designRenderRoam);
		return designRenderRoam.getId();
	}
}
