package com.sandu.product.model.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class GroupProductPO implements Serializable {

	private static final long serialVersionUID = 3810129074653339429L;

	private Integer id;
	
	/**
	 * 组合编码
	 */
	private String groupCode;
	
	/**
	 * 组合名
	 */
	private String groupName;
	
	/**
	 * 组合类型
	 */
	private Integer type;
	
	/**
	 * 组合状态
	 * 0:默认
	 * 1:上架
	 * 2:测试
	 */
	private Integer state;
	
	/**
	 * 排序
	 */
	private Integer sorting;
	
	/**
	 * 封面id
	 */
	private Integer picId;
	
	/**
	 * 组合编码
	 */
	private String code;
	
	/**
	 * 系统编码
	 */
	private String sysCode;
	
	/**
	 * 创建者
	 */
	private String creator;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 修改人
	 */
	private String modifier;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * 是否删除
	 */
	private Integer isDeleted;
	
	/**
	 * 字符备用1
	 */
	private String att1;
	
	/**
	 * 字符备用2
	 */
	private String att2;
	
	/**
	 * 整数备用1
	 */
	private Integer numa1;
	
	/**
	 * 整数备用2
	 */
	private Integer numa2;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 样板房id
	 */
	private Integer designTempletId;
	
	/**
	 * 产品组合位置信息
	 */
	private String location;
	
	/**
	 * 组合价格
	 */
	private Double groupPrice;
	
	/**
	 * 结构id
	 */
	private Integer structureId;
	
	/**
	 * 结构组标记
	 */
	private String groupFlag;

	/**
	 * 组合宽
	 */
	private String groupWidth;
	
	/**
	 * 组合长
	 */
	private String groupLength;
	
	/**
	 * 组合高
	 */
	private String groupHigh;
	
	/**
	 * 空间类型value
	 */
	private Integer spaceFunctionValue;
	
	/**
	 * 组合面积value
	 */
	private Integer spaceAreaValue;
	
	/**
	 * 品牌id(关联base_brand表)
	 */
	private Integer brandId;
	
	/**
	 * 所属公司ID
	 */
	private Integer companyId;
	
	/**
	 * 风格value(关联数据字典value)
	 */
	private Integer styleValue;
	
	/**
	 * 产品风格
	 */
	private String productStyleIdInfo;
	
	private Long styleId;
	
	/**
	 * 产品型号
	 */
	private String productType;
	
	/**
	 * 组合类型,
	 * 0:普通组合
	 * 1:一件装修组合
	 * 对应常量类:GroupProductTypeConstant
	 */
	private Integer groupType;
	
	/**
	 * 组合类型
	 */
	private Integer compositeType;
	
	/**
	 * 是否为标准，null或1：否；2是
	 */
	private Integer isStandard;
	
	/**
	 * 组合建议售价
	 */
	private Double advicePrice;
	
	/**
	 * 组合描述
	 */
	private String description;
	
	/**
	 * 图片
	 */
	private String picIds;
	
	/**
	 * 创建者ID
	 */
	private Integer userId;
	
	/**
	 * 公开状态
	 */
	private Integer secrecyFlag;
	
	/**
	 * 来源组合产品ID
	 */
	private Integer originId;
	
}
