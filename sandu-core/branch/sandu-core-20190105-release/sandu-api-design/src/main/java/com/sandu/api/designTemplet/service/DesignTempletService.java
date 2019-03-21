package com.sandu.api.designTemplet.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.designTemplet.model.DesignTemplet;

public interface DesignTempletService {

	/**
	 * find by houseId
	 * 
	 * @author huangsongbo
	 * @param houseId
	 * @return
	 * @throws BizExceptionEE 
	 */
	List<DesignTemplet> getListByHouseId(Integer houseId) throws BizExceptionEE;

	Map<Integer, List<DesignTemplet>> getDesignTempletMap(List<DesignTemplet> designTempletList) throws BizExceptionEE;

	/**
	 * 获取样板房面积value值
	 * 逻辑:
	 * 如果main_area有值,则转化为value值
	 * 如果没值,则应用space_area
	 * @param designTemplet
	 * @return
	 * @throws BizExceptionEE 
	 */
	Integer getAreaValue(DesignTemplet designTemplet) throws BizExceptionEE;

}
