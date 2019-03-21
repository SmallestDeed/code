package com.sandu.service.base.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sandu.api.base.common.Utils;
import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.base.model.SysDictionary;
import com.sandu.api.base.service.SysDictionaryService;
import com.sandu.service.base.dao.SysDictionaryMapper;

import lombok.extern.log4j.Log4j2;

@EnableScheduling
@Log4j2
@Service("sysDictionaryService")
public class SysDictionaryServiceImpl implements SysDictionaryService {

	private static final String LOGPREFIX = "[数据字典模块]:";
	
	private static Map<String, List<SysDictionary>> houseTypeMap;
	
	private static Map<Integer, SysDictionary> houseTypeSysDictionaryMap = new HashMap<Integer, SysDictionary>();
	
	@Autowired
	private SysDictionaryMapper sysDictionaryMapper;
	
	@Override
	public Integer getAreaValue(Double mainArea, Integer houseTypeValue) throws BizExceptionEE {
		// 参数验证 ->start
		if(mainArea == null) {
			log.error(LOGPREFIX + "mainArea = null");
			return null;
		}
		if(houseTypeValue == null) {
			log.error(LOGPREFIX + "houseTypeValue = null");
			return null;
		}
		if(mainArea.doubleValue() == 0) {
			return null;
		}
		// 参数验证 ->end
		
		Map<String, List<SysDictionary>> houseTypeMap = this.getHouseTypeMap();
		SysDictionary sysDictionaryHouseType = this.getSysDictionaryHouseType(houseTypeValue);
		if(sysDictionaryHouseType == null) {
			log.error(LOGPREFIX + "sysDictionaryHouseType = null");
			return null;
		}
		if(StringUtils.isEmpty(sysDictionaryHouseType.getValuekey())) {
			log.error(LOGPREFIX + "StringUtils.isEmpty(sysDictionaryHouseType.getValuekey()) = true");
			return null;
		}
		List<SysDictionary> sysDictionarieList = houseTypeMap.get(sysDictionaryHouseType.getValuekey());
		if(Utils.isEmpty(sysDictionarieList)) {
			log.error(LOGPREFIX + "Lists.isEmpty(sysDictionarieList) = true");
			return null;
		}
		
		// 选择匹配的面积,返回sysDictionary的value ->start
		return this.getAreaValue(sysDictionarieList, mainArea);
		// 选择匹配的面积,返回sysDictionary的value ->end
	}

	@Override
	public SysDictionary getNameByType(SysDictionary query) {
		return sysDictionaryMapper.getNameByType(query);
	}

	private Map<String, List<SysDictionary>> getHouseTypeMap() {
		if(houseTypeMap != null) {
			return houseTypeMap;
		}
		
		List<SysDictionary> sysDictionaryList = this.getAllHouseTypeSysDictionary();
		Map<String, List<SysDictionary>> houseTypeMapTemp = new HashMap<String, List<SysDictionary>>();
		if(Utils.isNotEmpty(sysDictionaryList)) {
			for(SysDictionary sysDictionary : sysDictionaryList) {
				if(sysDictionary == null) {
					System.out.println();
				}
				if(houseTypeMapTemp.containsKey(sysDictionary.getType())) {
					List<SysDictionary> sysDictionaryListTemp = houseTypeMapTemp.get(sysDictionary.getType());
					if(sysDictionaryListTemp == null) {
						sysDictionaryListTemp = new ArrayList<SysDictionary>();
					}
					sysDictionaryListTemp.add(sysDictionary);
					houseTypeMapTemp.put(sysDictionary.getType(), sysDictionaryListTemp);
				}else {
					List<SysDictionary> sysDictionaryListTemp = new ArrayList<SysDictionary>();
					sysDictionaryListTemp.add(sysDictionary);
					houseTypeMapTemp.put(sysDictionary.getType(), sysDictionaryListTemp);
				}
			}
		}
		houseTypeMap = houseTypeMapTemp;
		return houseTypeMap;
	}

	/**
	 * 定时钟清理常量(防止更新后,常量中的信息没有更新)
	 * 暂定为30分钟清理一次
	 * 
	 * @author huangsongbo
	 */
	@Scheduled(fixedRate = 1800000)
	@Override
	/*@Scheduled(fixedRate = 5000)*/
	public void clearConstants() {
		log.info(LOGPREFIX + "准备清楚常量(重新更新)");
		houseTypeMap = null;
		houseTypeSysDictionaryMap.clear();
		log.info(LOGPREFIX + "清除常量完毕(重新更新)");
	}
	
	/**
	 * 获取所有type = houseType 其子集的数据字典
	 * @return
	 */
	private List<SysDictionary> getAllHouseTypeSysDictionary() {
		return sysDictionaryMapper.getAllHouseTypeSysDictionary();
	}
	
	/**
	 * type="houseType" value = #{houseTypeValue},查找数据字典
	 * 
	 * @author huangsongbo
	 * @param houseTypeValue
	 * @return
	 * @throws BizExceptionEE 
	 */
	private SysDictionary getSysDictionaryHouseType(Integer houseTypeValue) throws BizExceptionEE {
		// 参数验证 ->start
		if(houseTypeValue == null) {
			log.error(LOGPREFIX + "houseTypeValue = null");
			return null;
		}
		// 参数验证 ->end
		
		if(houseTypeSysDictionaryMap.containsKey(houseTypeValue)) {
			return houseTypeSysDictionaryMap.get(houseTypeValue);
		}
		
		SysDictionary sysDictionary = this.findOneByTypeAndValue("houseType", houseTypeValue);
		if(sysDictionary != null) {
			houseTypeSysDictionaryMap.put(houseTypeValue, sysDictionary);
		}
		return sysDictionary;
	}
	
	/**
	 * 通过type和value查找sysDictionary
	 * 
	 * @author huangsongbo
	 * @param type
	 * @param value
	 * @return
	 * @throws BizExceptionEE 
	 */
	public SysDictionary findOneByTypeAndValue(String type, Integer value) throws BizExceptionEE {
		String throwLogPrefix = "查询数据字典失败";
		
		// 参数验证 ->start
		if(StringUtils.isEmpty(type)) {
			String logMes = "params error: StringUtils.isEmpty(type) = true";
			log.error(LOGPREFIX + logMes);
			throw new BizExceptionEE(throwLogPrefix + "(" + logMes + ")");
		}
		if(value == null) {
			String logMes = "params error: value == null";
			log.error(LOGPREFIX + logMes);
			throw new BizExceptionEE(throwLogPrefix + "(" + logMes + ")");
		}
		// 参数验证 ->end
		
		return sysDictionaryMapper.selectOneByTypeAndValue(type, value);
	}
	
	/**
	 * 获取对应数据字典的value值
	 * 
	 * @author huangsongbo
	 * @param sysDictionarieList
	 * @param mainArea
	 * @return
	 */
	private Integer getAreaValue(List<SysDictionary> sysDictionarieList, Double mainArea) {
		// 参数验证 ->start
		if(Utils.isEmpty(sysDictionarieList)) {
			log.error(LOGPREFIX + "Lists.isEmpty(sysDictionarieList) = true"); 
			return null;
		}
		if(mainArea == null) {
			log.error(LOGPREFIX + "mainArea = null"); 
			return null;
		}
		// 参数验证 ->end
		
		for(SysDictionary sysDictionary : sysDictionarieList) {
			if(mainArea >=  Double.valueOf(sysDictionary.getAtt4()) && mainArea <= Double.valueOf(sysDictionary.getAtt5())) {
				return sysDictionary.getValue();
			}
		}
		
		log.error(LOGPREFIX + "未找到符合面积的sysDictionary; mainArea = {}; type = {}", mainArea, sysDictionarieList.get(0).getType());
		return null;
	}

}
