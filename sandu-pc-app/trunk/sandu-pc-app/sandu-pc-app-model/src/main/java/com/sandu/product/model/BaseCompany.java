package com.sandu.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sandu.common.model.BaseModel;
import lombok.Data;

/**   
 * @Title: BaseCompany.java 
 * @Package com.nork.product.model
 * @Description:产品模块-企业表
 * */
@Data
public class BaseCompany  extends BaseModel implements Serializable{
	private static final long serialVersionUID = 1L;

	/**  企业编码  **/
	private String companyCode;
	/**  企业名称  **/
	private String companyName;
	/**  企业标识  **/
	private String companyIdentify;
	/**  企业网站  **/
	private String companyUrl;
	/**  企业介绍  **/
	private String companyDesc;
	/**  企业地址  **/
	private String companyAddress;

	/**  整数备用1-->	企业LOGO  **/
	private Integer companyLogo;

	/*明细行业*/
	private String smallType;
	/*行业*/
	private Integer industry;
	/** 经营类型 **/
	private Integer businessType;
	/** 企业parentId **/
	private Integer pid;
	/** 品牌ID **/
	private String brandId;
	/**
	 * 公司可见产品范围
	 */
	private String categoryIds;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 联系人姓名
	 */
	private String contactName;
}
