package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.DesignerWorksMapper;
import com.nork.system.model.DesignerWorks;
import com.nork.system.model.DesignerWorksUser;
import com.nork.system.model.search.DesignerWorksSearch;
import com.nork.system.service.DesignerWorksService;

/**   
 * @Title: DesignerWorksServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-设计师作品ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-10-19 14:43:46
 * @version V1.0   
 */
@Service("designerWorksService")
@Transactional
public class DesignerWorksServiceImpl implements DesignerWorksService {

	private DesignerWorksMapper designerWorksMapper;

	@Autowired
	public void setDesignerWorksMapper(
			DesignerWorksMapper designerWorksMapper) {
		this.designerWorksMapper = designerWorksMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param designerWorks
	 * @return  int 
	 */
	@Override
	public int add(DesignerWorks designerWorks) {
		designerWorksMapper.insertSelective(designerWorks);
		return designerWorks.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designerWorks
	 * @return  int 
	 */
	@Override
	public int update(DesignerWorks designerWorks) {
		return designerWorksMapper
				.updateByPrimaryKeySelective(designerWorks);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return designerWorksMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignerWorks 
	 */
	@Override
	public DesignerWorks get(Integer id) {
		return designerWorksMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designerWorks
	 * @return   List<DesignerWorks>
	 */
	@Override
	public List<DesignerWorks> getList(DesignerWorks designerWorks) {
	    return designerWorksMapper.selectList(designerWorks);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  designerWorks
	 * @return   int
	 */
	@Override
	public int getCount(DesignerWorksSearch designerWorksSearch){
		return  designerWorksMapper.selectCount(designerWorksSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  designerWorks
	 * @return   List<DesignerWorks>
	 */
	@Override
	public List<DesignerWorks> getPaginatedList(
			DesignerWorksSearch designerWorksSearch) {
		return designerWorksMapper.selectPaginatedList(designerWorksSearch);
	}

	@Override
	public List<DesignerWorksUser> getDesigners(
			DesignerWorksUser designerWorksUser) {
		return designerWorksMapper.getDesigners(designerWorksUser);
	}

	@Override
	public List<DesignerWorksUser> getWorks(DesignerWorksUser designerWorksUser) {
		return designerWorksMapper.getWorks(designerWorksUser);
	}

	@Override
	public String getFilePath(DesignerWorksUser designerWorksUser) {
		return designerWorksMapper.getFilePath(designerWorksUser);
	}

	@Override
	public List<DesignerWorksUser> getdesignerWorkDetail(
			DesignerWorksUser designerWorksUser) {
		return designerWorksMapper.getdesignerWorkDetail(designerWorksUser);
	}

	@Override
	public DesignerWorksUser getdesignerWorkRendered(
			DesignerWorksUser designerWorksUser) {
		return designerWorksMapper.getdesignerWorkRendered(designerWorksUser);
	}

	@Override
	public List<DesignerWorksUser> getdesignerWorkList(
			DesignerWorksUser designerWorksUser) {
		return designerWorksMapper.getdesignerWorkList(designerWorksUser);
	}

	/**
	 * 其他
	 * 
	 */


}
