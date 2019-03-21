package com.sandu.product.model;

import com.sandu.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**   
 * @Title: CompanyShop.java 
 * @Package com.nork.product.model
 * @Description:企业商铺-企业店铺
 * @version V1.0
 */
@Data
public class CompanyShop extends BaseModel implements Serializable{
private static final long serialVersionUID = 1L;
	/**  企业Id  **/
	private Integer companyId;
	/** 企业父ID(经销商-->厂商) **/
	private Integer companyPid;
	/**  用户Id  **/
	private Integer userId;
	/** 店铺编码 **/
	private String shopCode;
	/**  店铺名称  **/
	private String shopName;
	/**  店铺类型 设计师、设计公司、装修公司、工长、品牌馆、家居建材  **/
	private Integer businessType;
	/** 分类ids（设计师---擅长风格，施工单位---施工类型，家居建材---产品分类等）**/
	private String categoryIds;
	/** 一级分类ids（建材、家居、电器）**/
	private String firstCategoryIds;
	/**  省编码  **/
	private String provinceCode;
	/**  市编码  **/
	private String cityCode;
	/**  区编码  **/
	private String areaCode;
	/**  街道编码  **/
	private String streetCode;
	/**  地区长编码  **/
	private String longAreaCode;
	/**  详细地址  **/
	private String shopAddress;
	/**  联系电话  **/
	private String contactPhone;
	/**  联系人姓名  **/
	private String contactName;
	/**  logo图ID  **/
	private Integer logoPicId;
	/** 店铺封面资源Ids **/
	private String coverResIds;
	/** 店铺封面资源类型 1:图片列表,2:全景图,3:视频 **/
	private Integer coverResType;
	/**  发布平台类型 1:小程序2:选装网3:同城联盟  **/
	private String releasePlatformValues;
	/**  显示状态(1是0否)  **/
	private Integer displayStatus;
	/**  访问次数  **/
	private Integer visitCount;
	/**  好评率  **/
	private Double praiseRate;
	/**  店铺介绍  **/
	private String shopIntroduced;
	//富文本介绍文本Id
	private Long introducedFileId;
	//富文本介绍图片Ids
	private String introducedPicIds;
	//修改企业类型清空店铺属性分类标识
	private String cleanCategorySign;
	/** 封面图ID **/
	private Integer coverPicId;


	@Override
	public String toString() {
		return "CompanyShop{" +
				"companyId=" + companyId +
				", companyPid=" + companyPid +
				", userId=" + userId +
				", shopCode='" + shopCode + '\'' +
				", shopName='" + shopName + '\'' +
				", businessType=" + businessType +
				", categoryIds='" + categoryIds + '\'' +
				", firstCategoryIds='" + firstCategoryIds + '\'' +
				", provinceCode='" + provinceCode + '\'' +
				", cityCode='" + cityCode + '\'' +
				", areaCode='" + areaCode + '\'' +
				", streetCode='" + streetCode + '\'' +
				", longAreaCode='" + longAreaCode + '\'' +
				", shopAddress='" + shopAddress + '\'' +
				", contactPhone='" + contactPhone + '\'' +
				", contactName='" + contactName + '\'' +
				", logoPicId=" + logoPicId +
				", coverResIds='" + coverResIds + '\'' +
				", coverResType=" + coverResType +
				", releasePlatformValues='" + releasePlatformValues + '\'' +
				", displayStatus=" + displayStatus +
				", visitCount=" + visitCount +
				", praiseRate=" + praiseRate +
				", shopIntroduced='" + shopIntroduced + '\'' +
				", introducedFileId=" + introducedFileId +
				", introducedPicIds='" + introducedPicIds + '\'' +
				", cleanCategorySign='" + cleanCategorySign + '\'' +
				", coverPicId=" + coverPicId +
				'}';
	}
}
