package com.sandu.product.model;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Data
public class CategoryProductResult extends Mapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 商品ID
     */
    private Integer productId;
    /**
     * 商品code
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 产品风格名称
     */
    private String proStyleId;
    /**
     * 产品风格名称
     */
    private String proStyle;
    /**
     * 产品色调
     */
    private String colorId;
    /**
     * 产品色调名称
     */
    private String color;

    /**
     * 产品规格
     */
    private String productSpec;
    /**
     * 销售价格
     */
    private String salePrice;
    /**
     * 销售价格单位
     */
    private String salePriceValueName;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 产品图片路径
     */
    private String picPath;
    
    private String productStyleIdInfo;
    
    private List<String> productStyleInfoList;
    private Integer isFavorite;//是否被收藏.0:未收藏,1收
	/**
     * 分类ID
     */
    private String categoryId;
    /**
     * 分类名称
     */
    private String categoryName;

    /*大分类*/
    private Integer productTypeValue;

    private String productTypeName;

    private String productTypeCode;
    /*小分类*/
    private Integer productSmallTypeValue;

    private String productSmallTypeName;

    private String productSmallTypeCode;

    private String productLength;

    private String productWidth;

    private String productHeight;

    private Integer collectState;
    //产品使用量
    private Integer productCount;
    //排序字段
    private Integer orderType;
    //当前模型产品ID
    private Integer modelProductId;
    //产品最小高度（天花用到）
    private String minHeight;
    /**
     * 产品u3d模型文件
     **/
    private String productModelPath;
    private String u3dModelPath;
    /**
     * 软装硬装分类
     **/
    private String rootType;
    //0、实体墙  1、背景墙 2、门框
    private Integer bgWall;

    /**
     * 空间ID
     **/
    private Integer spaceCommonId;

    /**
     * 材质ID
     **/
    private String materialPicId;

    /**
     * 材质路径
     **/
    private String materialPicPath;

    /**
     * parentId
     **/
    private Integer parentId;

    /**
     * 材质路径s
     **/
    private String[] materialPicPaths;

    /**
     * 材质配置文件路径
     **/
    private String materialConfigPath;

    /**
     * 同类型产品ID
     **/
    private Integer parentProductId;

    /**
     * 规则json
     **/
    private Map<String, String> rulesMap;

    /*品牌id*/
    private Integer brandId;
    /**
     * 模型长度
     **/
    private int modelLength;
    /**
     * 模型宽度
     **/
    private int modelWidth;
    /**
     * 模型高度
     **/
    private int modelHeight;
    /**
     * 模型最小高度
     **/
    private int modelMinHeight;
    /**
     * 产品属性
     **/
    private Map<String, String> propertyMap;
    /*材质属性*/
    private Integer textureAttrValue;
    /*铺设方式*/
    private String laymodes;
    /**
     * 材质宽
     */
    private String textureWidth;
    /**
     * 材质高
     */
    private String textureHeight;

    List<Map<String, String>> attributeList = new ArrayList<Map<String, String>>();
//	/** 床垫白模 **/
//	private CategoryProductResult basicMattress;
//	/** 床上用品白模 **/
//	private CategoryProductResult basicBedding;

    /**
     * 携带得白模产品集合
     **/
    private Map<String, CategoryProductResult> basicModelMap;

    private Integer productOrdering;

    /**
     * 是否是定制
     **/
    private Integer isCustomized;

    /**
     * 创建时间
     **/
    private Date gmtCreate;
    //是否是主产品 1 是 0 否
    private Integer isMainProduct;
    /*判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜)*/
    private Integer isSplit = new Integer(0);
    /*可拆分材质信息*/
    private List<SplitTextureDTO> splitTexturesChoose;
    //色系排序
    private Integer colorsOrdering;
    /**
     * 产品色系
     */
    private String colorsLongCode;
    /**
     * 产品多材质信息
     */
    private String splitTexturesInfoStr;
    /**
     * 单材质产品的材质图片路径
     */
    private String resTexturePath;
    /**
     * 用来判断软硬装
     */
    private String smallTypeAtt1;
    /**
     * 样板房产品ID
     */
    private String templateProductId;
    /**
     * 产品全铺长度
     */
    private Integer fullPaveLength;
    /**
     * 产品全铺长度
     */
    private Integer targetBmProductId;

    /**
     * 产品标准
     * 款式ID
     * 区域款式
     * 尺寸代码
     * 天花布局标识
     */
    private Integer isStandard;
    private Integer styleId;
    private String regionMark;
    private String measureCode;
    private Integer productSmallpoxIdentify;

}
