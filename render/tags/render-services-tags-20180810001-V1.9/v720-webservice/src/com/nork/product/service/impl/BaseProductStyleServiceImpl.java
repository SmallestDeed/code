package com.nork.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Utils;
import com.nork.product.dao.BaseProductStyleMapper;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.search.BaseProductStyleSearch;
import com.nork.product.service.BaseProductStyleService;

/**   
 * @Title: BaseProductStyleServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品管理-产品风格ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-04-17 11:42:49
 * @version V1.0   
 */
@Service("baseProductStyleService")
public class BaseProductStyleServiceImpl implements BaseProductStyleService {

	private BaseProductStyleMapper baseProductStyleMapper;

	@Autowired
	public void setBaseProductStyleMapper(
			BaseProductStyleMapper baseProductStyleMapper) {
		this.baseProductStyleMapper = baseProductStyleMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param baseProductStyle
	 * @return  int 
	 */
	@Override
	public int add(BaseProductStyle baseProductStyle) {
		baseProductStyleMapper.insertSelective(baseProductStyle);
		return baseProductStyle.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseProductStyle
	 * @return  int 
	 */
	@Override
	public int update(BaseProductStyle baseProductStyle) {
		return baseProductStyleMapper
				.updateByPrimaryKeySelective(baseProductStyle);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseProductStyleMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseProductStyle 
	 */
	@Override
	public BaseProductStyle get(Integer id) {
		return baseProductStyleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseProductStyle
	 * @return   List<BaseProductStyle>
	 */
	@Override
	public List<BaseProductStyle> getList(BaseProductStyle baseProductStyle) {
	    return baseProductStyleMapper.selectList(baseProductStyle);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseProductStyle
	 * @return   int
	 */
	@Override
	public int getCount(BaseProductStyleSearch baseProductStyleSearch){
		return  baseProductStyleMapper.selectCount(baseProductStyleSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseProductStyle
	 * @return   List<BaseProductStyle>
	 */
	@Override
	public List<BaseProductStyle> getPaginatedList(
			BaseProductStyleSearch baseProductStyleSearch) {
		return baseProductStyleMapper.selectPaginatedList(baseProductStyleSearch);
	}

	/**
     * 组装产品属性树形结构数据
     * @param pid
     * @return
     */
	@Override
	public List<BaseProductStyle> asyncLoadTree(Integer pid, String code) {
		return baseProductStyleMapper.asyncLoadTree(pid, code);
	}
	
	/**
     * Ajax
     * @param baseProductStyle
     * @return
     */
	@Override
	public BaseProductStyle findOne(BaseProductStyle baseProductStyle){
		return baseProductStyleMapper.findOne(baseProductStyle);
	}

	/**
	 * 修改分类.如果code变动过，则需要修改分类下所有子节点的code
	 * @return
	 */
	@Override
	public int updateRecursion(BaseProductStyle baseProductStyle) {
		BaseProductStyle oldProCategory = baseProductStyleMapper.selectByPrimaryKey(baseProductStyle.getId());
		if( !oldProCategory.getCode().equals(baseProductStyle.getCode()) ){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("oldCode","."+oldProCategory.getCode()+".");
			params.put("newCode","."+baseProductStyle.getCode()+".");
			baseProductStyleMapper.recursionUpdate(params);
		}
		return baseProductStyleMapper.updateByPrimaryKeySelective(baseProductStyle);
	}

	@Override
	public List<BaseProductStyle> findLike(String longCode) {
		return baseProductStyleMapper.findLike(longCode);
	}

	@Override
	public int updateOne(String longCode,Integer id) {
		return baseProductStyleMapper.updateOne(longCode,id);
	}

	@Override
	public List<BaseProductStyle> editOne(BaseProductStyle baseProductStyle) {
		return baseProductStyleMapper.editOne(baseProductStyle);
	}

	@Override
	public List<BaseProductStyle> findAllByLongCodeStart(String longCodeStart) {
		BaseProductStyleSearch baseProductStyleSearch = new BaseProductStyleSearch();
		baseProductStyleSearch.setStart(-1);
		baseProductStyleSearch.setLimit(-1);
		baseProductStyleSearch.setSchLongCode_(longCodeStart);
		baseProductStyleSearch.setIsDeleted(0);
		return this.getPaginatedList(baseProductStyleSearch);
	}

 
	@Override
	public List<String> getProductStyleInfo(JSONObject styleJson) {
		String isLeaf_1 = "";
		if(styleJson.containsKey("isLeaf_1")){
			isLeaf_1 = styleJson.getString("isLeaf_1");
		}
		String isLeaf_0 = "";
		if(styleJson.containsKey("isLeaf_0")){
			isLeaf_0 = styleJson.getString("isLeaf_0");
		}
		
		// 获取父节点中的子节点,并且与isLeaf_0取并集 ->start
		List<Integer> sonIdList = new ArrayList<Integer>();
		if( StringUtils.isNotBlank(isLeaf_1) ){
			List<Integer> parentIdList = Utils.getIntegerListFromStringList(isLeaf_1);
			for(Integer parentId : parentIdList){
				List<Integer> sonIdListItem = new ArrayList<Integer>();
				this.getIdListByPidRecursion(parentId, sonIdListItem);
				if(sonIdListItem != null && sonIdListItem.size() > 0){
					sonIdList.addAll(sonIdListItem);
				}
			}
		}
		if( StringUtils.isNotBlank(isLeaf_0) ){
			sonIdList.addAll(Utils.getIntegerListFromStringList(isLeaf_0));
		}
		// 获取父节点中的子节点,并且与isLeaf_0取并集 ->end
		
		List<String> nameList = new ArrayList<String>();
		if(sonIdList.size() > 0){
			nameList = this.getNameByIdList(sonIdList);
		}
		return nameList;
	}
	/**
	 * 通过idList找到所有风格名称
	 * @author huangsongbo
	 * @param sonIdList
	 * @return
	 */
	@Override
	public List<String> getNameByIdList(List<Integer> idList) {
		return baseProductStyleMapper.getNameByIdList(idList);
	}

	/**
	 * 通过id查找所有子节点id
	 * @author huangsongbo
	 * @param parentId
	 * @return
	 */
	@Override
	public List<Integer> getIdListByPid(Integer parentId) {
		return baseProductStyleMapper.getIdListByPid(parentId);
	}

	@Override
	public List<Integer> getSonIdListByStyleJson(JSONObject styleJson) {
		String isLeaf_1 = "";
		if(styleJson.containsKey("isLeaf_1")){
			isLeaf_1 = styleJson.getString("isLeaf_1");
		}
		String isLeaf_0 = "";
		if(styleJson.containsKey("isLeaf_0")){
			isLeaf_0 = styleJson.getString("isLeaf_0");
		}
		
		// 获取父节点中的子节点,并且与isLeaf_0取并集 ->start
		List<Integer> sonIdList = new ArrayList<Integer>();
		List<Integer> parentIdList = Utils.getIntegerListFromStringList(isLeaf_1);
		for(Integer parentId : parentIdList){
			List<Integer> sonIdListItem = this.getIdListByPid(parentId);
			if(sonIdListItem != null && sonIdListItem.size() > 0){
				sonIdList.addAll(sonIdListItem);
			}
		}
		sonIdList.addAll(Utils.getIntegerListFromStringList(isLeaf_0));
		// 获取父节点中的子节点,并且与isLeaf_0取并集 ->end
		return sonIdList;
	}
	
	/**
	 * 递归查询子节点
	 * @param parentId
	 * @return
	 */
	private List<Integer> getIdListByPidRecursion(Integer parentId, List<Integer> returnList) {
		List<Integer> sonIdList = this.getIdListByPid(parentId);
		if(sonIdList != null && sonIdList.size() > 0){
			for(Integer sonId : sonIdList){
				this.getIdListByPidRecursion(sonId, returnList);
			}
		}else{
			returnList.add(parentId);
		}
		return returnList;
	}
	/**
	 * 根据空间类型Id得到对应风格
	 */
	@Override
	public List<BaseProductStyle> getStyleByHouseType(Integer functionId) {
		List<BaseProductStyle> styleLs=new ArrayList<BaseProductStyle>();
		styleLs=baseProductStyleMapper.getStyleByHouseType(functionId);
		for (BaseProductStyle baseProductStyle : styleLs) {
			//风格类型
			String styleName=null;
			int nLength=0;
			String end="";
			styleName=baseProductStyle.getName();
			nLength=styleName.length();
			//剪切掉“风格”
			  if(nLength>2){
			     end=styleName.substring(nLength-2, nLength);
			  }
			  if(end.equals("风格")){
				  styleName=styleName.substring(0, nLength-2) ; 
				  baseProductStyle.setName(styleName);
			  }
		}
		return styleLs;
	}
}
