package com.nork.home.service;

import java.util.List;

import com.nork.design.model.RecommendationSpaceResult;
import com.nork.home.model.DesignProgramDiy;
import com.nork.home.model.DesignRecommendation;
import com.nork.home.model.search.DesignProgramDiySearch;
import com.nork.home.model.search.DesignRecommendationSearch;

/**   
 * @Title: DesignRecommendationService.java 
 * @Package com.nork.home.service
 * @Description:户型房型-设计推荐表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-07-03 16:15:50
 * @version V1.0   
 */
public interface DesignRecommendationService {
	/**
	 * 新增数据
	 *
	 * @param designRecommendation
	 * @return  int 
	 */
	public int add(DesignRecommendation designRecommendation);

	/**
	 *    更新数据
	 *
	 * @param designRecommendation
	 * @return  int 
	 */
	public int update(DesignRecommendation designRecommendation);

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
	 * @return  DesignRecommendation 
	 */
	public DesignRecommendation get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designRecommendation
	 * @return   List<DesignRecommendation>
	 */
	public List<DesignRecommendation> getList(DesignRecommendation designRecommendation);

	/**
	 *    获取数据数量
	 *
	 * @param  designRecommendation
	 * @return   int
	 */
	public int getCount(DesignRecommendationSearch designRecommendationSearch);
	
	/**
	 *    获取数据数量
	 *
	 * @param  designRecommendation
	 * @return   int
	 */
	public int getTotal(DesignProgramDiy dpd);
	/**
	 * 获得列表页面数据数量
	 * @param designRecommendationSearch
	 * @return
	 */
	public int getdpdCount(DesignProgramDiySearch designProgramDiySearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designRecommendation
	 * @return   List<DesignRecommendation>
	 */
	public List<DesignRecommendation> getPaginatedList(
				DesignRecommendationSearch designRecommendationtSearch);

	/**
	 * 首页管理列表页面查询
	 * 
	 */
	
	public List<DesignProgramDiy> getDesignProgramDiyList(DesignProgramDiy dpd);
	
	public List<DesignProgramDiy> getDropDownBoxList(DesignProgramDiy dpd);
	
	public DesignProgramDiy getDesignProgramDiyByCode (DesignProgramDiy dpd);
	
	public DesignProgramDiy getSampleroomSourceByCode (DesignProgramDiy dpd);
	
	public RecommendationSpaceResult getSpaceRender (Integer id);

}
