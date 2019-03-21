package com.sandu.api.groupproducct.model.bo;

import com.sandu.api.product.model.bo.PicInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupProductInfoBO implements Serializable {
    private Integer id;
    @ApiModelProperty("组合内产品详情")
    private List<ProductInfo> products;
    @ApiModelProperty("组合名称")
    private String name;
    @ApiModelProperty("组合编码")
    private String code;
    @ApiModelProperty("组合描述")
    private String desc;
    @ApiModelProperty("使用房间名称")
    private String houseType;
    @ApiModelProperty("使用房间id")
    private Integer houseValue;
    @ApiModelProperty("使用区域类型")
    private String areaType;
    @ApiModelProperty("使用区域ID")
    private Integer areaValue;
    @ApiModelProperty("组合类型名称")
    private String groupType;
    @ApiModelProperty("使用区域ID")
    private Integer groupValue;
    @ApiModelProperty("品牌名称")
    private String brandName;
    @ApiModelProperty("品牌ID")
    private Integer brandId;
    @ApiModelProperty("品牌ID")
    private String groupNumber;
    @ApiModelProperty("价格")
    private Double price;
    @ApiModelProperty("建议售价")
    private Double advicePrice;
    @ApiModelProperty("所有图片信息")
    private List<PicInfo> picInfos;
    @ApiModelProperty("默认图片ID")
    private Integer defaultPicId;
    @ApiModelProperty("风格ID")
    private Integer styleValue;
    @ApiModelProperty("风格名称")
    private String styleName;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProductInfo implements Serializable {
        private int id;
        private String code;
        private String pic;
        private String name;
        private boolean isMain;
    }
}