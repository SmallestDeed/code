package com.nork.system.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.system.model.bo.SysDictionaryBo;
import com.nork.system.model.vo.SysDictionaryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.PrepProductSearchInfo;
import com.nork.system.model.SysDicitonaryOptimize;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;

/**   
 * @Title: SysDictionaryMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统管理-数据字典Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-26 11:45:04
 * @version V1.0   
 */
@Repository
public interface SysDictionaryMapper {
    int insertSelective(SysDictionary record);

    int updateByPrimaryKeySelective(SysDictionary record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysDictionary selectByPrimaryKey(Integer id);

    int selectCount(SysDictionarySearch sysDictionarySearch);
    
	List<SysDictionary> selectPaginatedList(
			SysDictionarySearch sysDictionarySearch);
			
    List<SysDictionary> selectList(SysDictionary sysDictionary);
    
    int getValueMax(String type);
    
    int getOrderingMax(String type);

	SysDictionary findOneByValueKey(String string);
    
	SysDictionary getNameByType(SysDictionary sysDictionary);
	
	SysDictionary checkType(SysDictionary sysDictionary);

	int getCountOptimize(SysDictionarySearch sysDictionarySearch);

	List<SysDicitonaryOptimize> getListOptimize(SysDictionarySearch sysDictionarySearch);

	List<String> findAllSmallTypeValueKey();

	List<String> findValueKeyByType(String type);

	SysDictionary selectSmallTypeObj(Map<String,Object> map);

	List<SysDictionary> getByTypeAndValueKey(@Param("type") String type, @Param("bigTypeList") List<String> bigTypeList);

	List<Integer> getValueByTypeAndValueKeylist(@Param("bigType") String bigType, @Param("smallTypeList") List<String> smallTypeList);

	Integer getProductValueByBaimoValue(@Param("typeValue") Integer typeValue, @Param("smallTypeValue") Integer smallTypeValue);

	SysDictionary findOneByTypeAndValueAndValue(@Param("type") String type, @Param("typeValue") Integer typeValue, @Param("smallTypeValue") Integer smallTypeValue);

	List<SysDicitonaryOptimize> findAll(SysDictionarySearch sysDictionarySearch);

	List<PrepProductSearchInfo> getbigTypeValueAndSmallTypeValueBySmallTypeValuekeyList(@Param("valuekeyList") Set<String> valuekeyList);
	/**
	 * 通过ValueKeys  和 type  查列表
	 * @param type
	 * @param spaceTypeList
	 * @return
	 */
	List<SysDictionary> getListByValueKeys(@Param("type")String type,@Param("ValueKeys") List<String> ValueKeys);

	List<SysDictionary> findAllByTypes(List<String> list);
	List<SysDictionary> findAllByType(String type);
	List<SysDictionaryVo> findListByType(String type);

	/**
	 * 通过通过Value  和 type  查列表
	 * @param type
	 * @param Value
	 * @return
	 */
	List<SysDictionary> getListByValueType(@Param("type")String type,@Param("Value") List<String> Value);

	/**
	 * 通过通过Value查户型空间面积列表
	 * @param typeValue
	 * @return
	 */
	List<SysDictionary> findSpaceAreaList(@Param("typeValue") Integer typeValue);
	
	/**
	 * 根据Type查询所有，add by yangzhun
	 * @param sysDictionarySearch
	 * @return
	 */
	List<SysDicitonaryOptimize> getListOptimizeByType(SysDictionarySearch sysDictionarySearch);
	
	/**
	 * 根据valuekeyList查询valueList
	 * 
	 * @author huangsongbo
	 * @param smallTypeValueKeyList
	 * @return
	 */
	List<Integer> getValueListBySmallTypeValueKeyList(@Param("smallTypeValueKeyList") List<String> smallTypeValueKeyList);

	/**
	 * 通过通过Valuekeys查列表
	 * @param valueKes
	 * @return
	 */
	List<SysDictionaryBo> findListByValueKeys(@Param("valueKes") List<String> valueKes);

	List<SysDictionary> selectSomeInfoByType(@Param("type") String type);

}
