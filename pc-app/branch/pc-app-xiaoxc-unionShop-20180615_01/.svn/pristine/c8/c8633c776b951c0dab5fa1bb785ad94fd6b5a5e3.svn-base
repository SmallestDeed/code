package com.nork.product.service;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.product.model.BaseWaterjetTemplate;
import com.nork.product.model.search.BaseWaterjetTemplateSearch;

/**   
 * @Title: BaseWaterjetTemplateService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-水刀模版Service
 * @createAuthor pandajun 
 * @CreateDate 2018-06-04 14:30:27
 * @version V1.0   
 */
public interface BaseWaterjetTemplateService {
	
	/**
	 * 新增数据
	 *
	 * @param baseWaterjetTemplate
	 * @return  int 
	 */
	public int add(BaseWaterjetTemplate baseWaterjetTemplate);

	/**
	 *    更新数据
	 *
	 * @param baseWaterjetTemplate
	 * @return  int 
	 */
	public int update(BaseWaterjetTemplate baseWaterjetTemplate);

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
	 * @return  BaseWaterjetTemplate 
	 */
	public BaseWaterjetTemplate get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseWaterjetTemplate
	 * @return   List<BaseWaterjetTemplate>
	 */
	public List<BaseWaterjetTemplate> getList(BaseWaterjetTemplate baseWaterjetTemplate);

	/**
	 *    获取数据数量
	 *
	 * @param  baseWaterjetTemplate
	 * @return   int
	 */
	public int getCount(BaseWaterjetTemplateSearch baseWaterjetTemplateSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseWaterjetTemplate
	 * @return   List<BaseWaterjetTemplate>
	 */
	public List<BaseWaterjetTemplate> getPaginatedList(
				BaseWaterjetTemplateSearch baseWaterjetTemplatetSearch);

	/**
	 * 新建/更新水刀模版
	 * 
	 * @author huangsongbo
	 * @param baseWaterjetTemplate
	 * @param loginUser
	 */
	public boolean save(BaseWaterjetTemplate baseWaterjetTemplate, LoginUser loginUser);

	/**
	 * 新增水刀模版,验证参数
	 * 
	 * @author huangsongbo
	 * @param baseWaterjetTemplate
	 * @return
	 */
	boolean checkParamsForAdd(BaseWaterjetTemplate baseWaterjetTemplate);

	public List<BaseWaterjetTemplate> findAllBySearch(BaseWaterjetTemplateSearch search);

	public int getCountBySearch(BaseWaterjetTemplateSearch search);

	/**
	 * 为列表设置额外的信息
	 * eg:缩略图url
	 * 
	 * @author huangsongbo
	 * @param list
	 * @return
	 */
	public List<BaseWaterjetTemplate> setMoreInfoForfindAll(List<BaseWaterjetTemplate> list);

}
