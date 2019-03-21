package com.sandu.product.model.output;



import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/12/23.
 */
@Data
public class ProductCostDetail extends Mapper implements Serializable {

    /**
     * 产品ID
     **/
    private int productId;
    /**
     * 产品名称
     **/
    private String productName;
    /**
     * 产品图片路径
     **/
    private String productPicPath;
    /**
     * 产品原图路径
     **/
    private String productOriginalPicPath;
    /**
     * 品牌名称
     **/
    private String brandName;
    /**
     * 产品单价
     **/
    private BigDecimal unitPrice;
    /**
     * 产品数量
     **/
    private Integer count;
    /**
     * 总价
     **/
    private BigDecimal totalPrice;
    /**
     * 产品型号
     **/
    private String productModelNumber;
    /**
     * 产品规格
     **/
    private String productSpec;
    /**
     * 产品描述
     **/
    private String productDesc;
    /**
     * 产品价格单位
     **/
    private String productUnit;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品挂节点信息
     */
    private String posIndexPath;

    private Integer planProductId;//方案产品id
    private String categoryCode;//产品分类编码
    private String smallCategoryCode;//产品小分类编码
    private Integer isMainProduct;//是否是主产品
    private Integer spaceCommonId;//空间id
    private String productTypeValue;//产品类别value···大类
    private String productSmallTypeValue;//产品类别value···小类

    private String sourcePlanGroupId;//设计方案组合ID
    private Integer sourceProductGroupId;//产品组合ID
    private String sourceGroupProductUniqueId;//组合产品唯一标识
    private Integer sourceGroupProductId;//组合id
    private String sourceGroupProductCode;//组合code

    private String sourceSplitTexturesChooseInfo;//默认材质信息

    private Integer isStandard;//		<!-- 描述(区域、尺寸代码) -->
    private String regionMark;//		<!-- 区域标识 -->
    private Integer styleId;//		<!-- 款式id -->
    private String measureCode;//	<!-- 尺寸代码 -->
    private Integer structureId;//结构id

    /**
     * 用这个值来判断bgwall应该赋什么值
     */
    private String valuekey;
    /**
     * 前段根据这个值来判断墙面是否可以删除
     **/
    private Integer bgWall;
    /**
     * 是否做了材质替换(0:否;1:是)
     */
    private Integer isReplaceTexture;

    //关联样板房id
    private Integer designTempletId;
    //产品分类id
    private Integer proCategoryId;
    //产品分类长编码
    private String proCategoryLongCode;
    //
    private String att2;
    //
    private String typeValue;
    //
    private Integer brandId;
    //产品是否保密
    private Integer secrecyFlag;
    //公司id
    private Integer companyId;
    //产品分类小类valuekey值
    private String typeValueKey;
    //产品平台关联表的产品分类字符串
    private String styleIdStr;

}
