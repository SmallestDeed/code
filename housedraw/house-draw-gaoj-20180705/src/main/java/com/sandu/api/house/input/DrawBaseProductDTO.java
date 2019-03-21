package com.sandu.api.house.input;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 户型绘制,保存数据DTO->产品数据(基本产品表/样板房产品表数据都在此) 备注问题:
 * 
 * @author huangsongbo
 */
@Data
public class DrawBaseProductDTO implements Serializable {

	private static final long serialVersionUID = 4351281303161050399L;

	// draw_base_Product产品表数据 ->start
	@ApiModelProperty("前端生成的产品唯一值(烘培之后作为key更新产品)")
	private String uniqueId;

	@ApiModelProperty("产品大类valuekey")
	private String bigTypeValueKey;

	@ApiModelProperty("产品小类valuekey")
	private String smallTypeValueKey;

	@ApiModelProperty("产品长度/全铺长度")
	private String productLength;

	@ApiModelProperty("产品宽度")
	private String productWidth;

	@ApiModelProperty("产品高度")
	private String productHeight;

	@ApiModelProperty("产品还原/烘培文件id")
	private Long productFileId;

	@ApiModelProperty("产品模型文件id")
	private Long windowsU3dmodelId;

	@ApiModelProperty("产品id")
	private Long productId;

	@ApiModelProperty("产品类型:1:硬装;2:软装;3:public(窗户,栏杆等等)")
	private Integer modelType;

	/**
	 * 是否是软装,户型绘制的话,软装是系统中已经存在的产品白膜,不需要新建
	 */
	private boolean isSoft = false;

	// draw_base_Product产品表数据 ->end

	// draw_design_templet_product样板房产品表数据 ->start

	// 放在烘焙之后给我 ->start
	@ApiModelProperty("产品顺序")
	private String productSequence;

	@ApiModelProperty("挂节点")
	private String posIndexPath;

	@ApiModelProperty("挂节点")
	private String posName;
	// 放在烘焙之后给我 ->end

	@ApiModelProperty("墙体方位")
	private String wallOrientation;

	@ApiModelProperty("墙体类型")
	private String wallType;

	@ApiModelProperty("样板房产品表id")
	private Long designTempletProductId;

	@ApiModelProperty("绑定的主墙产品的uniqueId;eg:如果该产品是背景墙,该字段记录的是其对应的主墙的uniqueId")
	private String bindUniqueId;

	// 组合产品ID
	private Integer groupProductId;
	// 是否主产品 0、否；1、是
	private Integer isMainProduct;
	// 是组合还是结构 0:组合,1:结构
	private Integer groupType;
	// 组合产品唯一标识，判断存在两个相同的组合产品的唯一标识
	private String groupUniqueId;
	
	/**
	 * 软装白膜产品是否拉伸过</p> 
	 * 1、是拉伸过; null或2、没有拉伸过
	 */
	private Integer isChanged;

	// ## 子母门1、单开门2、双开门3、推拉门5
	private Integer proAttrType;

	// 产品空间坐标，烘焙完成后获取
	private String proPosition;

	// draw_design_templet_product样板房产品表数据 ->end
}
