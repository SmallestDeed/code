package com.nork.home.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nork.common.model.Mapper;

public class BaseHouseResult extends Mapper implements Serializable {
	
	/**小区ID**/
	private Integer livingId;
	/**小区名称**/
	private String livingName;
	/**区域省编码**/
	private String provinceCode;
	/**区域市编码**/
	private String cityCode;
	/**区编码 add By WangHaiLin**/
	private String areaCode;
	/**区域长编码**/
	private String areaLongCode;
	/**区域名称**/
	private String areaName;
	/**户型数量**/
	private String houseCount;
	
	private Integer userType;
	
	/**所属区名**/
	private String districtName;
 
	Integer spaceCommonStatusList[] = null;//存放空间状态的list  用于in 查询
	Integer designTempletPutawayStateList[] = null; //存放样板房状态的list  用于in 查询
	
	public Integer[] getSpaceCommonStatusList() {
		return spaceCommonStatusList;
	}
	public void setSpaceCommonStatusList(Integer[] spaceCommonStatusList) {
		this.spaceCommonStatusList = spaceCommonStatusList;
	}
	public Integer[] getDesignTempletPutawayStateList() {
		return designTempletPutawayStateList;
	}
	public void setDesignTempletPutawayStateList(Integer[] designTempletPutawayStateList) {
		this.designTempletPutawayStateList = designTempletPutawayStateList;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getLivingId() {
		return livingId;
	}
	public void setLivingId(Integer livingId) {
		this.livingId = livingId;
	}
	public String getLivingName() {
		return livingName;
	}
	public void setLivingName(String livingName) {
		this.livingName = livingName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getAreaLongCode() {
		return areaLongCode;
	}
	public void setAreaLongCode(String areaLongCode) {
		this.areaLongCode = areaLongCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getHouseCount() {
		return houseCount;
	}
	public void setHouseCount(String houseCount) {
		this.houseCount = houseCount;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
