package com.sandu.api.product.model.bo;

import com.sandu.api.category.model.bo.CategoryTreeNode;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.bo.HardProductModelBO;
import com.sandu.api.resmodel.model.bo.ModelAreaBO;
import com.sandu.api.resmodel.model.bo.ModelBO;
import com.sandu.api.restexture.model.bo.ProductTextureVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 */
@Data
public class ProductBO implements Serializable {
    /**
     * 产品id
     */
    private Long id;
    /**
     * 产品编码
     */
    private String code;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品型号
     */
    private String modelNumber;
    /**
     * 产品缩略图图片
     */
    private Integer picId;
    private String picPath;
    /**
     * 产品所有图片
     */
    private String picIds;
//    private List<String> allPicPath;
    /**
     * 品牌ID
     */
    private Integer brandId;
    private String brandName;
    /**
     * 所属分类编码
     */
    private String categoryCodeNames;
    private List<String> categoryCodeList;
    /**
     * 产品风格
     */
    private List<Integer> proStyleId;
    private List<String> proStyleName;
    /**
     * 产品款式
     */
    private Integer modelingId;
    private String modelingName;
    /**
     * 产品模型信息
     */
    private ResModel model;
    /**
     * 产品系列
     */
    private Integer seriesId;
    private String seriesName;
    /**
     * 建议售价
     */
    private Double advicePrice;
    /**
     * 售价
     */
    private Double price;
    /**
     * 售价单位
     */
    private Integer saleUnitValue;
    private String saleUnitName;
    /**
     * 产品描述
     */
    private String desc;

    private String spec;
    private List<ProductPropBO> props;
    /**
     * 图片信息
     */
    private List<PicInfo> picInfos;
    /**
     * 产品贴图
     */
    private ProductTextureVO texture;
    //private List<ProductTextureInfo> modelTextureInfo;
    /**
     * 模型产品贴图
     */
    private List<ModelAreaBO> modelTextureInfo;
    /**
     * 线上分配状态(1:已分配/0:未分配)
     */
    private Integer onlineAllotStatus;
    /**
     * 渠道分配状态(1:已分配/0:未分配)
     */
    private Integer channelAllotStatus;
    /**
     * 判断是否为模型产品
     */
    private boolean checkIsModel;

    private String length;

    private String smallType;

    private String height;

    private String width;

    private List<CategoryTreeNode> categoryNodes;

    @ApiModelProperty(value = "产品属性")
    private String attrNames;


    /**
     * 硬装产品模型信息
     * Map<productId,ModelInfo> </>
     */
    private List<HardProductModelBO> hardProductModelInfo;

    private String materialPicIds;

    private Integer isComplexParquet;
}
