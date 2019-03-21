package com.sandu.api.shop.model;

import com.sandu.commons.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**   
 * 企业商铺-实体类
 * @auth xiaoxc
 * @date 2018-06-04
 * @version V1.0   
 */
@Data
public class CompanyShop extends Mapper implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 主键ID **/
    private Integer id;
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
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  备注  **/
	private String remark;
	//富文本介绍文本Id
	private Long introducedFileId;
	//富文本介绍图片Ids
	private String introducedPicIds;

	//add by wangHaiLin
	/**  经度  **/
	private Double longitude;
	/**  纬度  **/
	private Double latitude;
	/**设计最低价格**/
    private Double designFeeStarting;
    /**设计最高价格**/
	private Double designFeeEnding;

	/**装修报价区间**/
	private Double decorationPriceStart;
	/**装修报价区间**/
	private Double decorationPriceEnd;
	/**装修方式:0.半包,1.全包*/
	private Integer decorationType;
	/**从业年限**/
	private Integer workingYears;
	/**是否被拉黑（0：未被拉黑；1:是被拉黑）**/
	private Integer isBlacklist;
	/** 店铺类型 **/
	private Integer shopType;
	/**创建人账号**/
	private String creatorAccount;
	/**创建人手机**/
	private String creatorPhone;
	/**服务区域**/
	private String serviceArea;
	/**企业店铺装修方式:1.半包,2.全包,3.包软装,4.清水**/
	private String businessShopDecorationType;
}
