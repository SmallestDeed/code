package com.sandu.product.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class ProducDetail implements Serializable{
	/**
	 * 产品详情
	 */
	private static final long serialVersionUID = 1L;
	private Integer productId;//产品ID
	private String productCode;//产品编码
	private String productName;//产品名称
    private Integer brandId;//品牌id
	private String brandName;//品牌名称
	private String productStyleIdInfo;//产品风格ID
	private String productSpec;//产品规格
	private String productDesc;//产品描述
	private Integer productTypeValue;//产品类型ID
	private String splitTexturesInfo;//产品所有材质ID
	private String picIds;//产品图片列表
	private Integer isFavorite;//是否被收藏.0:未收藏,1收藏
	private List<String> productStyleInfoList;//产品风格集合
	private String productTypeName;//产品类型名称
	private String materialId;//产品单个材质ID
	private Double salePrice;//产品服务
	private Integer planProductId;//方案产品ID
	private String productModelNumber;//产品型号
	private String productColorName;//产品颜色
	private String productStyleName;//产品款式
	private List<ProCategoryPo> categoryList;//产品的分类集合
	private Integer companyId;//公司id
	List smallPiclist = new ArrayList();    
    //图片列表缩略图集合
    List thumbnailList = new ArrayList(); 
    private List<SplitTextureInfoDTO> splitTextureList;//材质列表

}
