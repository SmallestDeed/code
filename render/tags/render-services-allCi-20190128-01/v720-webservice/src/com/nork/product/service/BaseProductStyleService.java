package com.nork.product.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.search.BaseProductStyleSearch;
/**   
 * @Title: BaseProductStyleService.java 
 * @Package com.nork.product.service
 * @Description:产品管理-产品风格Service
 * @createAuthor pandajun 
 * @CreateDate 2017-04-17 11:42:49
 * @version V1.0   
 */
public interface BaseProductStyleService {
	/**
	 * 新增数据
	 *
	 * @param baseProductStyle
	 * @return  int 
	 */
	public int add(BaseProductStyle baseProductStyle);

	/**
	 *    更新数据
	 *
	 * @param baseProductStyle
	 * @return  int 
	 */
	public int update(BaseProductStyle baseProductStyle);

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
	 * @return  BaseProductStyle 
	 */
	public BaseProductStyle get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseProductStyle
	 * @return   List<BaseProductStyle>
	 */
	public List<BaseProductStyle> getList(BaseProductStyle baseProductStyle);

	/**
	 *    获取数据数量
	 *
	 * @param  baseProductStyle
	 * @return   int
	 */
	public int getCount(BaseProductStyleSearch baseProductStyleSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseProductStyle
	 * @return   List<BaseProductStyle>
	 */
	public List<BaseProductStyle> getPaginatedList(
				BaseProductStyleSearch baseProductStyletSearch);

	/**
     *风格管理树形结构数据
     * @param pid
     * @return
     */
    List<BaseProductStyle> asyncLoadTree(Integer pid, String code);
    

	/**
     *AJAX
     * @param baseProductStyle
     * @return
     */
    BaseProductStyle findOne(BaseProductStyle baseProductStyle);

    /**
	 * 修改分类.如果code变动过，则需要修改分类下所有子节点的code
	 * @return
	 */
	int updateRecursion(BaseProductStyle baseProductStyle);
	
	List<BaseProductStyle> findLike(String longCode);
	
	List<BaseProductStyle> editOne(BaseProductStyle baseProductStyle);
	
	int updateOne(String longCode,Integer id);

	/**
	 * 模糊搜索条件longCode查询BaseProductStyleList,(long_code like ".root.%")
	 * @param longCodeStart
	 * @return
	 */
	public List<BaseProductStyle> findAllByLongCodeStart(String longCodeStart);
	
	/**
	 * 通过styleJson获取材质中文名
	 * @param styleJson
	 * @return
	 */
	public List<String> getProductStyleInfo(JSONObject styleJson);

	List<String> getNameByIdList(List<Integer> idList);

	List<Integer> getIdListByPid(Integer parentId);

	/**
	 * 得到所有子节点id(父节点自动获取其子节点)
	 * @param styleJson
	 * @return
	 */
	public List<Integer> getSonIdListByStyleJson(JSONObject styleJson);
	
    /**
     *根据空间类型得到相应风格 
     */
	public List<BaseProductStyle> getStyleByHouseType(Integer functionId);
	
	
}
