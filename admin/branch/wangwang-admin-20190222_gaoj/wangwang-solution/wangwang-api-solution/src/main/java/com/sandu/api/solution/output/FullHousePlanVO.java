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
public class FullHousePlanVO implements Serializable {

    @ApiModelProperty("id")
    private Integer planId;

    @ApiModelProperty("通用唯一识别码")
    private String uuid;

    @ApiModelProperty("方案编码")
    private String planCode;

    @ApiModelProperty("方案名称")
    private String planName;

    @ApiModelProperty("方案风格id")
    private Integer designStyleId;

    @ApiModelProperty("方案风格名称")
    private String designStyleName;

    @ApiModelProperty("方案缩略图id")
    private Integer picId;

    @ApiModelProperty("方案缩略图路径")
    private String picPath;

    @ApiModelProperty("方案描述")
    private String planDesc;

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
    private Integer designerId;

    @ApiModelProperty("设计师名称")
    private String designer;

    @ApiModelProperty("方案来源类型1：内部制作，2：装进我家")
    private Integer sourceType;

    @ApiModelProperty("方案来源名称")
    private String origin;

    @ApiModelProperty("来源方案id")
    private Integer sourcePlanId;

    @ApiModelProperty("交付次数")
    private Integer deliverStatus;

    @ApiModelProperty("公开状态")
    private Integer secrecyFlag;

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

    @ApiModelProperty("完成时间")
    private Date completeDate;

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

    @ApiModelProperty(value = "方案版权费是否收费:0,免费,1收费")
    private Integer chargeType;

    @ApiModelProperty(value = "方案版权费")
    private Double planPrice;
}
