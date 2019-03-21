package com.sandu.system.service;

import java.util.List;
import java.util.Map;

import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;

public interface SysDictionaryService {

	/**
	 * 获取组合类型:电视柜组合的数据字典的value
	 * valuekey = "TVbench"
	 * 
	 * @author huangsongbo
	 * @return
	 */
	public Integer getTvbenchSysDictionaryValue();
	
	/**
	 * 得到valueList
	 * @author huangsongbo
	 * @param bigType
	 * @param smallTypeList
	 * @return
	 */
	public List<Integer> getValueByTypeAndValueKeylist(String bigType, List<String> smallTypeList);
	
	public SysDictionary findOneByValueKey(String smallTypeValuekey);
	
	/**
	 * 通过valuekeyList找到valueList
	 * 
	 * @author huangsongbo
	 * @param smallTypeValueKeyList
	 * @return
	 */
	public List<Integer> getValueListBySmallTypeValueKeyList(List<String> smallTypeValueKeyList);

	List<SysDictionary> getPaginatedList(SysDictionarySearch sysDictionarySearch);
	
	/**
	 * 通过大小类value查找小类Object
	 * @author xiaoxc
	 * @param map
	 * @return
	 */
	public SysDictionary selectSmallTypeObj(Map<String,Object> map);
	
	/**
	 *    根据类型和value获取数据字典对象
	 *
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	public SysDictionary getSysDictionary(String type, Integer value);
	
	/**
	 * 所有数据
	 * 
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	public List<SysDictionary> getList(SysDictionary sysDictionary);
	
	/**
	 *    根据类型和value获取类型名称数据
	 *
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	public SysDictionary getSysDictionaryByValue(String type, Integer value);
	
	/**
	 * 通过type和value查找sysDictionary
	 * @author huangsongbo
	 * @param type
	 * @param value
	 * @return
	 */
	public SysDictionary findOneByTypeAndValue(String type, Integer value);
	
}
