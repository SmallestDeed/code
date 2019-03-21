package com.nork.system.service;

import java.util.List;

import com.nork.system.model.DesignerWorks;
import com.nork.system.model.DesignerWorksUser;
import com.nork.system.model.search.DesignerWorksSearch;

/**   
 * @Title: DesignerWorksService.java 
 * @Package com.nork.system.service
 * @Description:系统-设计师作品Service
 * @createAuthor pandajun 
 * @CreateDate 2015-10-19 14:43:46
 * @version V1.0   
 */
public interface DesignerWorksService {
	/**
	 * 新增数据
	 *
	 * @param designerWorks
	 * @return  int 
	 */
	public int add(DesignerWorks designerWorks);

	/**
	 *    更新数据
	 *
	 * @param designerWorks
	 * @return  int 
	 */
	public int update(DesignerWorks designerWorks);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignerWorks 
	 */
	public DesignerWorks get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designerWorks
	 * @return   List<DesignerWorks>
	 */
	public List<DesignerWorks> getList(DesignerWorks designerWorks);

	/**
	 *    获取数据数量
	 *
	 * @param  designerWorks
	 * @return   int
	 */
	public int getCount(DesignerWorksSearch designerWorksSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designerWorks
	 * @return   List<DesignerWorks>
	 */
	public List<DesignerWorks> getPaginatedList(
				DesignerWorksSearch designerWorkstSearch);
	
	
	/**
	 *    设计师
	 *
	 * @param  designerWorksUser
	 * @return   DesignerWorksUser
	 */
	public List<DesignerWorksUser> getDesigners(
			DesignerWorksUser designerWorksUser);
	
	/**
	 *    设计师作品列表
	 *
	 * @param  designerWorksUser
	 * @return   List<DesignerWorksUser>
	 */
	public List<DesignerWorksUser> getWorks(
			DesignerWorksUser designerWorksUser);
	
	public List<DesignerWorksUser> getdesignerWorkDetail(DesignerWorksUser designerWorksUser);
	public List<DesignerWorksUser> getdesignerWorkList(DesignerWorksUser designerWorksUser);
	
	public DesignerWorksUser getdesignerWorkRendered(DesignerWorksUser designerWorksUser);
	
	
	public String getFilePath(DesignerWorksUser designerWorksUser);

	/**
	 * 其他
	 * 
	 */

}
