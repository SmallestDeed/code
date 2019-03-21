package com.sandu.api.solution.output;

import com.sandu.api.solution.model.bo.DecoratePriceInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 9:36 2018/8/23
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullHouseDesignPlanVO implements Serializable {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("通用唯一识别码")
    private String uuid;

    @ApiModelProperty("方案编码")
    private String planCode;

    @ApiModelProperty("方案名称")
    private String planName;

    @ApiModelProperty("方案风格id")
    private Integer planStyleId;

    @ApiModelProperty("方案风格名称")
    private String planStyleName;

    @ApiModelProperty("方案缩略图id")
    private Integer planPicId;

    @ApiModelProperty("方案缩略图路径")
    private String planPicPath;

    @ApiModelProperty("方案描述")
    private String planDescribe;

    @ApiModelProperty("公司id")
    private Integer companyId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("品牌id")
    private String brandId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("平台id")
    private String platformId;

    @ApiModelProperty("平台名称")
    private String platformName;

    @ApiModelProperty("设计师id")
    private Integer userId;

    @ApiModelProperty("设计师名称")
    private String userName;

    @ApiModelProperty("方案来源类型1：内部制作，2：装进我家")
    private Integer sourceType;

    @ApiModelProperty("方案来源名称")
    private String sourceName;

    @ApiModelProperty("来源方案id")
    private Integer sourcePlanId;

    @ApiModelProperty("交付次数")
    private Integer deliverStatus;

    @ApiModelProperty("公开状态")
    private Integer openState;

    @ApiModelProperty("720uuid")
    private String vrResourceUuid;

    @ApiModelProperty("方案版本")
    private Integer version;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("修改人")
    private String modifier;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    @ApiModelProperty("是否删除0：未删除，1：已删除")
    private Integer isDeleted;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("公开时间")
    private Date openTime;

    @ApiModelProperty("是否发布(店铺)")
    private String isPublish;

    @ApiModelProperty("方案报价信息")
    private List<DecoratePriceInfo> decoratePriceInfoList;

    @ApiModelProperty(value = "售卖方案是否收费:0,免费,1收费")
    private  Integer salePriceChargeType;

    @ApiModelProperty(value = "方案售卖价格")
    private Double salePrice;

    @ApiModelProperty(value = "方案版权费是否收费:0,免费,1收费")
    private Integer chargeType;

    @ApiModelProperty(value = "方案版权费")
    private Double planPrice;

    @ApiModelProperty(value = "是否改变过：0 -> 否；1 -> 是")
    private Integer isChanged;
}
