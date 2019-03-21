package com.nork.product.service;

import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.product.model.BaseWaterjetTemplate;
import com.nork.product.model.search.BaseWaterjetTemplateSearch;
import com.nork.product.model.vo.BrandNameVO;
import com.nork.system.model.vo.SysDictionaryVo;

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

	public BaseWaterjetTemplate getMoreInfoById(Integer id);

	/**
	 * 将图片上传信息保存到resPic
	 * @param map 上传服务器文件的详细信息
	 * @param businessId 该资源所关联的表id
	 * @param loginUser 登录名
	 * @return
	 */
	Integer saveUploadImgPic(Map map, Integer businessId, LoginUser loginUser, String mes);

	/**
	 * 将文件上传信息保存到resfile
	 * @author chenqiang
	 * @param map 上传服务器文件的详细信息
	 * @param businessId 该资源所关联的表id
	 * @param loginUser 登录名
	 * @return 新增数据主键id
	 */
	Integer saveUploadFile(Map map, Integer businessId, LoginUser loginUser, String mes);

	List<BrandNameVO> selectBrandNameListByUser(int userId, Integer userType);

	List<SysDictionaryVo> getSysDictionaryList();
}
