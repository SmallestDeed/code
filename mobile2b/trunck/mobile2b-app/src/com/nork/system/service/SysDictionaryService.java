package com.nork.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.system.model.bo.SysDictionaryBo;
import com.nork.system.model.dto.SysDictionaryGetListDTO;

import org.apache.ibatis.annotations.Param;

import com.nork.product.model.PrepProductSearchInfo;
import com.nork.system.model.SysDicitonaryOptimize;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.model.vo.SysDictionaryVo;

/**   
 * @Title: SysDictionaryService.java 
 * @Package com.nork.system.service
 * @Description:系统管理-数据字典Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-26 11:45:04
 * @version V1.0   
 */
public interface SysDictionaryService {
	/**
	 * 新增数据
	 *
	 * @param sysDictionary
	 * @return  int 
	 */
	public int add(SysDictionary sysDictionary);

	/**
	 *    更新数据
	 *
	 * @param sysDictionary
	 * @return  int 
	 */
	public int update(SysDictionary sysDictionary);

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
	 * @return  SysDictionary 
	 */
	public SysDictionary get(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysDictionary 
	 */
	public SysDictionary getFromCache(Integer id);
	
	/**
	 * 所有数据
	 * 
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	public List<SysDictionary> getList(SysDictionary sysDictionary);

	
	/**
	 * 所有数据
	 * 
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	public List<SysDictionary> getList(String type);

	
	/**
	 *    获取数据数量
	 *
	 * @param  sysDictionary
	 * @return   int
	 */
	public int getCount(SysDictionarySearch sysDictionarySearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	public List<SysDictionary> getPaginatedList(
				SysDictionarySearch sysDictionarytSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 *    根据类型和value获取类型名称数据
	 *
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	public SysDictionary getSysDictionaryByValue(String type, Integer value);
	
	public SysDictionary getNameByType(SysDictionary sysDictionary);
	
	/**
	 *    根据类型获取value最大值
	 *
	 * @param  type
	 * @return   int
	 */
	public int getValueMax(String type);
	
	/**
	 *    根据类型获取ordering最大值
	 *
	 * @param  type
	 * @return   int
	 */
	public int getOrderingMax(String type);

	/**
	 * 根据valueKey查找SysDictionary
	 * @param string
	 * @return
	 */
	public SysDictionary findOneByValueKeyInCache(String valueKey);
	
	public SysDictionary getParentByValueKey(String type);
	
	public List<SysDictionary> getList(Integer value,String type,String att3);
	
	public SysDictionary checkType(SysDictionary sysDictionary);
	
	/**
	 * 通过type和value查找sysDictionary
	 * @author huangsongbo
	 * @param type
	 * @param value
	 * @return
	 */
	public SysDictionary findOneByTypeAndValue(String type, Integer value);

	/**
	 * 通过type和typeKey查找sysDictionary
	 * @author huangsongbo
	 * @param type
	 * @param valueKey
	 * @return
	 */
	public SysDictionary findOneByTypeAndValueKey(String type, String valueKey);



	public List<SysDicitonaryOptimize> getListOptimize(SysDictionarySearch sysDictionarySearch);

	public int getCountOptimize(SysDictionarySearch sysDictionarySearch);
	
	/**
	 *    根据类型和value获取数据字典对象
	 *
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	public SysDictionary getSysDictionary(String type, Integer value);
	
	/**
	 * 根据type查找sysDictionary
	 * @author huangsongbo
	 * @param type
	 * @return
	 */
	public List<SysDictionary> findAllByType(String type);

	/**
	 * 根据type和att1查找SysDictionary
	 * @author huangsongbo
	 * @param string
	 * @return
	 */
	public List<SysDictionary> findAllByTypeAndAtt1(String type,String att1);

	/**
	 * 查找所有产品小类的valueKey
	 * @author huangsongbo
	 * @return
	 */
	public List<String> findAllSmallTypeValueKey();

	/**
	 * 通过type查找所有valueKey
	 * @author huangsongbo
	 * @param type
	 * @return
	 */
	public List<String> findValueKeyByType(String type);

	/**
	 * 通过大小类value查找小类Object
	 * @author xiaoxc
	 * @param map
	 * @return
	 */
	public SysDictionary selectSmallTypeObj(Map<String,Object> map);

	/**
	 * 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）*按产品小类排序用到
	 * @param bigSd
	 * @param bmSmallSd
	 * @return
	 */
	public SysDictionary dealWithInconformity(SysDictionary bigSd, SysDictionary bmSmallSd);

	/**
	 * 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）
	 * @param bigValuekey
	 * @param smallValuekey
	 * @param sdAtt3
	 * @return
	 */
	public SysDictionary dealWithInconformity(String bigValuekey,String smallValuekey,String sdAtt3);

	/**
	 * 得到value:valuekey类型的map
	 * @author huangsongbo
	 * @param type 
	 * @param bigTypeList
	 * @return
	 */
	public Map<Integer, String> getValueValueKeyMapByTypeAndValueKey(String string, List<String> bigTypeList);

	/**
	 * 得到valueList
	 * @author huangsongbo
	 * @param bigType
	 * @param smallTypeList
	 * @return
	 */
	public List<Integer> getValueByTypeAndValueKeylist(String bigType, List<String> smallTypeList);

	/**
	 * eg:baimo_mengk获得mengk的value
	 * @author huangsongbo
	 * @param productTypeValue
	 * @param smallTypeValue
	 * @return
	 */
	public Integer getProductValueByBaimoValue(Integer typeValue, Integer smallTypeValue);

	public SysDictionary findOneByTypeAndValueAndValue(String string, Integer productTypeValue, Integer smallTypeValue);

	List<SysDicitonaryOptimize> findAll(SysDictionarySearch sysDictionarySearch);

	public SysDictionary findOneByTypeAndValue(String string, Integer systemType, boolean b);

	public SysDictionary findOneByValueKey(String smallTypeValuekey);

	/**
	 * 获取大类value和小类value,通过小类valuekey
	 * @author huangsongbo
	 * @param valuekeyList
	 * @return
	 */
	public List<PrepProductSearchInfo> getbigTypeValueAndSmallTypeValueBySmallTypeValuekeyList(Set<String> valuekeyList);

	public boolean isSpecialProductType(String type, String value);

	/**
	 * 通过ValueKeys  和 type  查列表
	 * @param type
	 * @param spaceTypeList
	 * @return
	 */
	public List<SysDictionary> getListByValueKeys(String type, List<String> ValueKeys);

	 public List<SysDictionaryVo> getAllListByType(String type);
	/**
	 * 通过通过Value  和 type  查列表
	 * @param type
	 * @param Value
	 * @return
	 */
	List<SysDictionary> getListByValueType(String type,List<String> Value);

	/**
	 * 通过Value查户型空间面积列表
	 * @author xiaoxc
	 * @param typeValue
	 * @return
	 */
	public List<SysDictionary> findSpaceAreaList(Integer typeValue);

	/**
	 * 通过valuekeyList找到valueList
	 * 
	 * @author huangsongbo
	 * @param smallTypeValueKeyList
	 * @return
	 */
	public List<Integer> getValueListBySmallTypeValueKeyList(List<String> smallTypeValueKeyList);
	
	/**
	 * 通过valuekeys获取列表
	 * @author xiaoxc
	 * @param valueKeys
	 * @return
	 */
	List<SysDictionaryBo> getListByValueKeys(List<String> valueKeys);

	/**
	 * 获取前端需要的下拉列表信息
	 * 
	 * @author huangsongbo
	 * @param type
	 * @return
	 */
	List<SysDictionaryGetListDTO> getDTOList(String type);

	/**
	 * 
	 * @author huangsongbo
	 * @param type
	 * @return
	 */
	List<SysDictionary> getSomeInfoList(String type);

}
