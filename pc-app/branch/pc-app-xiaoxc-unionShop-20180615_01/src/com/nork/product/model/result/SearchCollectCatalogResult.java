package com.nork.product.model.result;

/**
 * Created by Administrator on 2016/7/01.
 * 搜索收藏目录接口响应结果中组详情实体
 */
public class SearchCollectCatalogResult {
	
	
    private Integer id;
	/**  目录名称  **/
	private String catalogName;
	/**  系统编码  **/
	private String sysCode;
	
	private Integer isLocked;
	
	
	public Integer getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
}

