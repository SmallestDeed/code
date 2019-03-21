package com.nork.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.system.model.bo.SysDictionaryBo;
import com.nork.system.model.vo.SysDictionaryVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.product.model.PrepProductSearchInfo;
import com.nork.system.cache.SysDictionaryCacher;
import com.nork.system.dao.SysDictionaryMapper;
import com.nork.system.model.SysDicitonaryOptimize;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: SysDictionaryServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统管理-数据字典ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-26 11:45:04
 * @version V1.0   
 */
@Service("sysDictionaryService")
@Transactional
public class SysDictionaryServiceImpl implements SysDictionaryService {
	private static Logger logger = Logger
			.getLogger(SysDictionaryServiceImpl.class);
	
	private SysDictionaryMapper sysDictionaryMapper;
	
	@Autowired
	public void setSysDictionaryMapper(
			SysDictionaryMapper sysDictionaryMapper) {
		this.sysDictionaryMapper = sysDictionaryMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysDictionary
	 * @return  int 
	 */
	@Override
	//@TriggersRemove(cacheName="baseCache",removeAll=true)
	public int add(SysDictionary sysDictionary) {
		sysDictionaryMapper.insertSelective(sysDictionary);
		
		return sysDictionary.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysDictionary
	 * @return  int 
	 */
	@Override
	//@TriggersRemove(cacheName="baseCache",removeAll=true)
	public int update(SysDictionary sysDictionary) {
		
		return sysDictionaryMapper
				.updateByPrimaryKeySelective(sysDictionary);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
//	@TriggersRemove(cacheName="baseCache",removeAll=true)
	//@CacheEvict(value = "serviceCache", key="#id") 
	public int delete(Integer id) {
		return sysDictionaryMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysDictionary 
	 */
	@Override
	//@Cacheable(value = "baseCache", key="#id")
	public SysDictionary get(Integer id) {
		return sysDictionaryMapper.selectByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysDictionary 
	 */
	@Override
	public SysDictionary getFromCache(Integer id) {
		
		List<SysDictionary> dictionaryList=null;
		if(Utils.enableRedisCache()){
			dictionaryList=SysDictionaryCacher.getAllList();
		}else{
			SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
			sysDictionarySearch.setStart(-1);
			sysDictionarySearch.setLimit(-1);
			dictionaryList=getList(sysDictionarySearch);
		}
		
		List<SysDictionary> newList = new ArrayList<SysDictionary>();
		if(CustomerListUtils.isNotEmpty(dictionaryList)&& id != null ){
			for(SysDictionary dictionary:dictionaryList){
				if(dictionary.getId().intValue()==id.intValue()){
					return dictionary;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 所有数据
	 * 
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	@Override
	//@Cacheable(value = "baseCache") 
	public List<SysDictionary> getList(SysDictionary sysDictionary) {
		//logger.info("+++DB+++getList");
	    return sysDictionaryMapper.selectList(sysDictionary);
	}
	
	@Override
	//@Cacheable(value = "baseCache") 
	public List<SysDictionary> getList(Integer value,String type,String att3) {
		//logger.info("+++DB+++getList");
		SysDictionary sysDictionary = new SysDictionary();
		sysDictionary.setAtt3(att3);
		sysDictionary.setType(type);
		sysDictionary.setValue(value);
		
		List<SysDictionary> dictionaryList=null;
		if(Utils.enableRedisCache()){
			dictionaryList=SysDictionaryCacher.getAllList();
		}else{
			SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
			sysDictionarySearch.setStart(-1);
			sysDictionarySearch.setLimit(-1);
			dictionaryList=getList(sysDictionarySearch);
		}
		//List<SysDictionary> dictionaryList = (List<SysDictionary>)request.getSession().getAttribute("sysDictionaryList");
		
		List<SysDictionary> newList = new ArrayList<SysDictionary>();
		if(CustomerListUtils.isNotEmpty(dictionaryList)&&!StringUtils.isEmpty(type)){
			for(SysDictionary dictionary:dictionaryList){
				if(type.equals(dictionary.getType()) 
						&& att3.equals(dictionary.getAtt3()) 
						&& value.equals(dictionary.getValue())
						){
					newList.add(dictionary);
				}
			}
		}
	    return newList;
	}
	
	/**
	 * 所有数据
	 * 
	 * @param  sysDictionary
	 * @return   List<SysDictionary>
	 */
	@SuppressWarnings("unchecked")
	@Override
	//@Cacheable(value = "baseCache")
	public List<SysDictionary> getList(String type) {
		
		//List<SysDictionary> dictionaryList = (List<SysDictionary>)request.getSession().getAttribute("sysDictionaryList");
		//List<SysDictionary> dictionaryList=SysDictionaryCacher.getAllList();
		
		List<SysDictionary> dictionaryList=null;
		if(Utils.enableRedisCache()){
			dictionaryList=SysDictionaryCacher.getAllList();
		}else{
			SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
			sysDictionarySearch.setStart(-1);
			sysDictionarySearch.setLimit(-1);
			dictionaryList=getList(sysDictionarySearch);
		}
		List<SysDictionary> newList = new ArrayList<SysDictionary>();
		if(CustomerListUtils.isNotEmpty(dictionaryList)&&!StringUtils.isEmpty(type)){
			for(SysDictionary dictionary:dictionaryList){
				if(type.equals(dictionary.getType())){
					newList.add(dictionary);
				}
			}
		}
	    return newList;
	}
	
	/**
	 *    获取数据数量
	 *
	 * @return   int
	 */
	@Override
	//@Cacheable(value = "baseCache")
	public int getCount(SysDictionarySearch sysDictionarySearch){
		logger.info("getCount");
		return  sysDictionaryMapper.selectCount(sysDictionarySearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @return   List<SysDictionary>
	 */
	@Override
	//@Cacheable(value = "baseCache")
	public List<SysDictionary> getPaginatedList(
			SysDictionarySearch sysDictionarySearch) {
		logger.info("getPaginatedList");
		return sysDictionaryMapper.selectPaginatedList(sysDictionarySearch);
	}

	/**
	 *    获取SysDictionary对象
	 *
	 * @param  type,value
	 * @return  SysDictionary
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SysDictionary getSysDictionaryByValue(String type, Integer value) {
		if(StringUtils.isEmpty(type)||value==null||new Integer(value).intValue()<=0){
			return new SysDictionary();
		}
		
		List<SysDictionary> dictionaryList=null;
		if(Utils.enableRedisCache()){
			dictionaryList=SysDictionaryCacher.getAllListWithType(type);
		}else{
			SysDictionary sysDictionary=new SysDictionary();
			sysDictionary.setIsDeleted(0);
			sysDictionary.setType(type);
			dictionaryList=getList(sysDictionary);
		}
		if(CustomerListUtils.isNotEmpty(dictionaryList)){
			for(SysDictionary dictionary:dictionaryList){
				if(dictionary==null||dictionary.getType()==null||dictionary.getValue()==null){
					logger.info("dictionary="+dictionary+";dictionary.type="+(dictionary!=null?dictionary.getType():"")
							+";dictionary.value="+(dictionary!=null?dictionary.getValue():""));
					continue;
				}
				if(type.equals(dictionary.getType()) && value.equals(dictionary.getValue())){
					return dictionary;
				}
			}
		}
		return new SysDictionary();
	}
	
	/**
	 *    获取SysDictionary对象
	 *
	 * @param  type,value
	 * @return  SysDictionary
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SysDictionary getSysDictionary(String type, Integer value) {
		if(StringUtils.isEmpty(type)||value==null||new Integer(value).intValue()<=0){
			return new SysDictionary();
		}
		
		List<SysDictionary> dictionaryList = null;
		if(Utils.enableRedisCache()){
			dictionaryList = SysDictionaryCacher.getSysDictionary(type, value);
		}else{
			SysDictionary sysDictionary=new SysDictionary();
			sysDictionary.setIsDeleted(0);
			sysDictionary.setType(type);
			sysDictionary.setValue(value);
			dictionaryList = getList(sysDictionary);
		}
		if(Lists.isNotEmpty(dictionaryList) && dictionaryList.size()>0){
			return dictionaryList.get(0);
		}
		
		return new SysDictionary();
	}
	

	/**
	 *    根据类型查询value最大值
	 *
	 * @param  type
	 * @return  int
	 */
	@Override
	public int getValueMax(String type) {
	
		return sysDictionaryMapper.getValueMax(type);
	}
	
	/**
	 *    根据类型查询Ordering最大值
	 *
	 * @param  type
	 * @return  int
	 */
	@Override
	public int getOrderingMax(String type) {
		 
		return sysDictionaryMapper.getOrderingMax(type);
	}

	@Override
	public SysDictionary findOneByValueKeyInCache(String valueKey) {
		//List<SysDictionary> dictionaryList = (ArrayList<SysDictionary>)request.getSession().getAttribute("sysDictionaryList");
		//List<SysDictionary> dictionaryList=SysDictionaryCacher.getAllList();
		

		List<SysDictionary> dictionaryList=null;
		if(Utils.enableRedisCache()){
			dictionaryList=SysDictionaryCacher.getAllList();
		}else{
			SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
			sysDictionarySearch.setStart(-1);
			sysDictionarySearch.setLimit(-1);
			dictionaryList=getList(sysDictionarySearch);
		}
		if(CustomerListUtils.isNotEmpty(dictionaryList)){

			for(SysDictionary dictionary:dictionaryList){

				if(StringUtils.isNotBlank(valueKey) && valueKey.equals(StringUtils.trimToEmpty(dictionary.getValuekey()))){
					return dictionary;
				}
			}
		}
		return new SysDictionary(); 
		//return sysDictionaryMapper.findOneByValueKey(string);
	}

	@Override
	public SysDictionary getParentByValueKey(String type) {
		if(StringUtils.isBlank(type)){
			return new SysDictionary();
		}
		//List<SysDictionary> dictionaryList = (ArrayList<SysDictionary>)request.getSession().getAttribute("sysDictionaryList");
		//List<SysDictionary> dictionaryList=SysDictionaryCacher.getAllList();
		
		List<SysDictionary> dictionaryList=null;
		if(Utils.enableRedisCache()){
			dictionaryList=SysDictionaryCacher.getAllList();
		}else{
			SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
			sysDictionarySearch.setStart(-1);
			sysDictionarySearch.setLimit(-1);
			dictionaryList=getList(sysDictionarySearch);
		}
		if(CustomerListUtils.isNotEmpty(dictionaryList)){
			for(SysDictionary dictionary:dictionaryList){
				if(type.equals(dictionary.getValuekey())){
					return dictionary;
				}
			}
		}
		return new SysDictionary();
	}

	@Override
	public SysDictionary getNameByType(SysDictionary sysDictionary) {
		if(StringUtils.isBlank(sysDictionary.getType())||sysDictionary.getValue()==null){
			return new SysDictionary();
		}
		
		//List<SysDictionary> dictionaryList = (ArrayList<SysDictionary>)request.getSession().getAttribute("sysDictionaryList");
		//List<SysDictionary> dictionaryList=SysDictionaryCacher.getAllList();
		
		List<SysDictionary> dictionaryList=new ArrayList<SysDictionary>();
		if(Utils.enableRedisCache()){
			dictionaryList=SysDictionaryCacher.getAllList();
		}else{
			SysDictionary sd=new SysDictionary();
			sd.setIsDeleted(0);
			dictionaryList=getList(sd);
		}
		/**SysDictionary sdic = new SysDictionary() ; 
		if(dictionaryList!=null&&dictionaryList.size()>0){
			for(int i=0;i<dictionaryList.size();i++){
				if(sysDictionary!=null){
					String type=sysDictionary.getType();
					String value=sysDictionary.getValue()+"";
					sdic = (SysDictionary)dictionaryList.get(0);
					break;
				}
			}
		}*/
		for(SysDictionary dictionary:dictionaryList){
			if(sysDictionary.getType().equals(dictionary.getType()) && sysDictionary.getValue().intValue()==dictionary.getValue().intValue()){
				return dictionary;
			}
		}

		
		return sysDictionaryMapper.getNameByType(sysDictionary);
	}

	@Override
	public SysDictionary checkType(SysDictionary sysDictionary) {
		if( sysDictionary.getValue() ==null   || sysDictionary.getSmallValue() == null){
			return new SysDictionary();
		}
		//List<SysDictionary> dictionaryList1 = (ArrayList<SysDictionary>)request.getSession().getAttribute("sysDictionaryList");
		//List<SysDictionary> dictionaryList2 = (ArrayList<SysDictionary>)request.getSession().getAttribute("sysDictionaryList");
		//List<SysDictionary> dictionaryList1=SysDictionaryCacher.getAllList();
		
		List<SysDictionary> dictionaryList1=null;
		if(Utils.enableRedisCache()){
			dictionaryList1=SysDictionaryCacher.getAllList();
		}else{
			SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
			sysDictionarySearch.setStart(-1);
			sysDictionarySearch.setLimit(-1);
			dictionaryList1=getList(sysDictionarySearch);
		}
		List<SysDictionary> dictionaryList2=dictionaryList1;
		if(CustomerListUtils.isNotEmpty(dictionaryList1)){
			for(SysDictionary dictionary1:dictionaryList1){
				if("productType".equals(dictionary1.getType()) && sysDictionary.getValue().intValue()==dictionary1.getValue()){
					for(SysDictionary dictionary2:dictionaryList2){
						if(sysDictionary.getSmallValue().intValue() == dictionary2.getValue() && dictionary1.getValuekey().equals(dictionary2.getType())){
							return dictionary2;
						}
					}
				}
			}
		}
		
		return sysDictionaryMapper.checkType(sysDictionary);
	}

	/**
	 * 通过type和value查找sysDictionary
	 * @author huangsongbo
	 * @param type
	 * @param value
	 * @return
	 */
	public SysDictionary findOneByTypeAndValue(String type, Integer value) {
		SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
		sysDictionarySearch.setStart(0);
		sysDictionarySearch.setLimit(1);
		sysDictionarySearch.setType(type);
		sysDictionarySearch.setValue(value);
		sysDictionarySearch.setIsDeleted(0);
		List<SysDictionary> sysDictionaries=null;
		if(Utils.enableRedisCache()){
			sysDictionaries = SysDictionaryCacher.getPageList(sysDictionarySearch);
		}else{
			sysDictionaries=getPaginatedList(sysDictionarySearch);
		}
		
		if(sysDictionaries!=null&&sysDictionaries.size()>0){
			return sysDictionaries.get(0);
		}
		return null;
	}
	
	
	
	
	/**
	 * 通过type和value查找sysDictionary
	 * @author huangsongbo
	 * @param type
	 * @param value
	 * @return
	 */
	public SysDictionary findOneByTypeAndValue(String type, Integer value,boolean flag) {
		SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
		sysDictionarySearch.setStart(0);
		sysDictionarySearch.setLimit(1);
		sysDictionarySearch.setType(type);
		sysDictionarySearch.setValue(value);
		sysDictionarySearch.setIsDeleted(0);
		List<SysDictionary> sysDictionaries=null;
		sysDictionaries=getPaginatedList(sysDictionarySearch);

		if(sysDictionaries!=null&&sysDictionaries.size()>0){
			return sysDictionaries.get(0);
		}
		return null;
	}
	
	
	/**
	 * 其他
	 * 
	 */

	/**


	/**
	 * 通过type和typeKey查找sysDictionary
	 * @author huangsongbo
	 * @param type
	 * @param valueKey
	 * @return
	 */
	public SysDictionary findOneByTypeAndValueKey(String type, String valueKey) {
		SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
		sysDictionarySearch.setStart(0);
		sysDictionarySearch.setLimit(1);
		sysDictionarySearch.setType(type);
		sysDictionarySearch.setValuekey(valueKey);
		List<SysDictionary> sysDictionaries=getPaginatedList(sysDictionarySearch);
		if(sysDictionaries!=null&&sysDictionaries.size()>0){
			return sysDictionaries.get(0);
		}
		return null;
	}


	@Override
	public List<SysDicitonaryOptimize> getListOptimize(SysDictionarySearch sysDictionarySearch) {
		List<SysDicitonaryOptimize> list = sysDictionaryMapper.getListOptimize(sysDictionarySearch);
		//TODO  APP端为了生成导航，需要产品小类来排序
		// 先获取所有大类
		List<SysDictionary> productTypeList = this.findAllByType("productType");
		if( productTypeList != null && productTypeList.size() > 0 ){
			// 获取大类下的所有小类
			List<SysDicitonaryOptimize> productSmallTypeList = null;
			SysDictionarySearch sysDicSearch = null;
			for( SysDictionary sysDictionary : productTypeList ){
				String productType = sysDictionary.getValuekey();
				if( StringUtils.isNotBlank(productType) ){
					sysDicSearch = new SysDictionarySearch();
					sysDicSearch.setType(productType);
					productSmallTypeList = sysDictionaryMapper.findAll(sysDicSearch);
					list.addAll(productSmallTypeList);
				}
			}
		}
		return list;
	}

	@Override
	public int getCountOptimize(SysDictionarySearch sysDictionarySearch) {
		return sysDictionaryMapper.getCountOptimize(sysDictionarySearch);
	}

	/**
	 * 根据type查找sysDictionary
	 * @param type
	 * @return
	 */
	public List<SysDictionary> findAllByType(String type) {
		SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
		sysDictionarySearch.setStart(-1);
		sysDictionarySearch.setLimit(-1);
		sysDictionarySearch.setOrder("ordering");
		sysDictionarySearch.setOrderNum("asc");
		sysDictionarySearch.setType(type);
		sysDictionarySearch.setIsDeleted(0);
		return getPaginatedList(sysDictionarySearch);
	}

	/**
	 * 根据type和att1查找SysDictionary
	 * @author huangsongbo
	 * @return
	 */
	public List<SysDictionary> findAllByTypeAndAtt1(String type, String att1) {
		SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
		sysDictionarySearch.setStart(-1);
		sysDictionarySearch.setLimit(-1);
		sysDictionarySearch.setType(type);
		sysDictionarySearch.setAtt1(att1);
		sysDictionarySearch.setIsDeleted(0);
		return getPaginatedList(sysDictionarySearch);
	}

	/**
	 * 查找所有产品小类的valueKey
	 * @author huangsongbo
	 * @return
	 */
	public List<String> findAllSmallTypeValueKey() {
		return sysDictionaryMapper.findAllSmallTypeValueKey();
	}

	/**
	 * 通过type查找所有valueKey
	 * @author huangsongbo
	 * @param type
	 * @return
	 */
	public List<String> findValueKeyByType(String type) {
		return sysDictionaryMapper.findValueKeyByType(type);
	}

	public SysDictionary selectSmallTypeObj(Map<String,Object> map){
		return sysDictionaryMapper.selectSmallTypeObj(map);
	}

	/**
	 * 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）*按产品小类排序用到
	 * @param bigSd
	 * @param bmSmallSd
	 * @return
	 */
	public SysDictionary dealWithInconformity(SysDictionary bigSd, SysDictionary bmSmallSd) {
		SysDictionary smallSd;
		String productType = Utils.getValue("app.product.typeInconformity", "");
		boolean isInconformity = Utils.isMateProductType(bmSmallSd == null ? "" : bmSmallSd.getValuekey(),
				productType);
		if (bmSmallSd != null && "baimo".equals(bmSmallSd.getAtt3()) && !isInconformity) {
			smallSd = this.findOneByTypeAndValueKey(bigSd.getValuekey(),
					Utils.getTypeValueKey(bmSmallSd.getValuekey()));
			if (smallSd == null) {
				smallSd = bmSmallSd;
			}
		} else {
			smallSd = bmSmallSd;
		}
		return smallSd;
	}

	/**
	 * 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）
	 * @param bigValuekey
	 * @param smallValuekey
	 * @param sdAtt3
	 * @return
	 */
	public SysDictionary dealWithInconformity(String bigValuekey,String smallValuekey,String sdAtt3) {
		SysDictionary smallSd = null;
		String productType = Utils.getValue("app.product.typeInconformity", "");
		boolean isInconformity = Utils.isMateProductType(smallValuekey, productType);
		if ( "baimo".equals(sdAtt3) && !isInconformity ) {
			smallSd = this.findOneByTypeAndValueKey(bigValuekey,Utils.getTypeValueKey(smallValuekey));
			return smallSd;
		}
		return smallSd;
	}

	@Override
	public Map<Integer, String> getValueValueKeyMapByTypeAndValueKey(String type, List<String> bigTypeList) {
		Map<Integer, String> returnMap = new HashMap<Integer, String>();
		List<SysDictionary> sysDictionarieList = sysDictionaryMapper.getByTypeAndValueKey(type, bigTypeList);
		for(SysDictionary sysDictionary : sysDictionarieList){
			returnMap.put(sysDictionary.getValue(), sysDictionary.getValuekey());
		}
		return returnMap;
	}

	@Override
	public List<Integer> getValueByTypeAndValueKeylist(String bigType, List<String> smallTypeList) {
		return sysDictionaryMapper.getValueByTypeAndValueKeylist(bigType, smallTypeList);
	}

	@Override
	public Integer getProductValueByBaimoValue(Integer typeValue, Integer smallTypeValue) {
		return sysDictionaryMapper.getProductValueByBaimoValue(typeValue, smallTypeValue);
	}

	@Override
	public SysDictionary findOneByTypeAndValueAndValue(String type, Integer productTypeValue,
			Integer smallTypeValue) {
		return sysDictionaryMapper.findOneByTypeAndValueAndValue(type, productTypeValue, smallTypeValue);
	}

	@Override
	public List<SysDicitonaryOptimize> findAll(SysDictionarySearch sysDictionarySearch){
		return sysDictionaryMapper.findAll(sysDictionarySearch);
	}

	@Override
	public SysDictionary findOneByValueKey(String smallTypeValuekey) {
		SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
		sysDictionarySearch.setStart(0);
		sysDictionarySearch.setLimit(1);
		sysDictionarySearch.setValuekey(smallTypeValuekey);
		sysDictionarySearch.setIsDeleted(0);
		List<SysDictionary> sysDictionarieList = this.getPaginatedList(sysDictionarySearch);
		if(sysDictionarieList != null && sysDictionarieList.size() > 0){
			return sysDictionarieList.get(0);
		}
		return null;
	}

	@Override
	public List<PrepProductSearchInfo> getbigTypeValueAndSmallTypeValueBySmallTypeValuekeyList(Set<String> valuekeyList) {
		return sysDictionaryMapper.getbigTypeValueAndSmallTypeValueBySmallTypeValuekeyList(valuekeyList);
	}

	/**
	 * 通过type和value查找
	 * 通过att1 和 att6判断返回true，false
	 * @author xiaoxc
	 * @param type
	 * @param value
	 * @return
	 */
	public boolean isSpecialProductType(String type, String value) {
		if( StringUtils.isEmpty(type) || StringUtils.isEmpty(value) ){
			return true;
		}
		if( "meng".equals(type) ){
			return true;
		}
		SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
		sysDictionarySearch.setStart(0);
		sysDictionarySearch.setLimit(1);
		sysDictionarySearch.setType(type);
		sysDictionarySearch.setValue(Utils.getIntValue(value));
		sysDictionarySearch.setAtt4("1");
		
		List<SysDictionary> sysDictionaries=null;
		if(Utils.enableRedisCache()){
			sysDictionaries = SysDictionaryCacher.getPageList(sysDictionarySearch);
		}else{
			sysDictionaries=getPaginatedList(sysDictionarySearch);
		}
		if( sysDictionaries!=null&&sysDictionaries.size() > 0 ){
			if("1".equals(sysDictionaries.get(0).getAtt6())){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}

	/**
	 * 通过ValueKeys  和 type  查列表
	 * @param type
	 * @param spaceTypeList
	 * @return
	 */
	@Override
	public List<SysDictionary> getListByValueKeys(String type, List<String> ValueKeys) {
		return sysDictionaryMapper.getListByValueKeys(type,ValueKeys);
	}


	/**
	 * 通过通过Value  和 type  查列表
	 * @param type
	 * @param Value
	 * @return
	 */
	@Override
	public List<SysDictionary> getListByValueType(String type, List<String> Value) {
		return sysDictionaryMapper.getListByValueType(type, Value);
	}

	/**
	 * 根据type获取所有数据
	 * @param type
	 * @return
	 */
	@Override
	public List<SysDictionaryVo> getAllListByType(String type) {
		if (StringUtils.isEmpty(type)){
			return null;
		}
		return sysDictionaryMapper.findListByType(type);
	}

	@Override
	public List<SysDictionary> findSpaceAreaList(Integer typeValue){
		return sysDictionaryMapper.findSpaceAreaList(typeValue);
	}

	@Override
	public List<Integer> getValueListBySmallTypeValueKeyList(List<String> smallTypeValueKeyList) {
		// 参数验证 ->start
		if(Lists.isEmpty(smallTypeValueKeyList)) {
			return null;
		}
		// 参数验证 ->end

		return sysDictionaryMapper.getValueListBySmallTypeValueKeyList(smallTypeValueKeyList);
	}

	/**
	 * 通过valuekeys获取列表
	 * @param valueKeys
	 * @return
	 */
	@Override
	public List<SysDictionaryBo> getListByValueKeys(List<String> valueKeys){
		return sysDictionaryMapper.findListByValueKeys(valueKeys);
	}

	@Override
	public List<SysDictionary> findAllByValueKeyList(List<String> smallTypeValuekeyList) {
		// 参数验证 ->start
		if(Lists.isEmpty(smallTypeValuekeyList)) {
			return null;
		}
		// 参数验证 ->end
		
		return sysDictionaryMapper.findAllByValueKeyList(smallTypeValuekeyList);
	}

}
