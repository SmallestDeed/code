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
 * DesignPlanBO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignPlanVO implements Serializable {


    private static final long serialVersionUID = -6957251774525435391L;
    /** */
    @ApiModelProperty("id")
    private Long planId;
    /**
     * 方案名称
     */
    @ApiModelProperty("方案名称")
    private String planName;
    /**
     * 方案编码
     */
    @ApiModelProperty("方案编号")
    private String planCode;

    /**
     * 方案名称
     */
    @ApiModelProperty("是否被删除")
    private Integer companyShopDesignPlanisDeleted;

    /**
     * 推荐类型
     */
    @ApiModelProperty("推荐类型")
    private Integer isOpen;


    /**
     * 推荐类型
     */
    @ApiModelProperty("店铺方案ID")
    private Long companyShopDesignPlanId;
    /**
     * 来源
     */
    @ApiModelProperty("来源")
    private String origin;
    /**
     * 来源id
     */
    @ApiModelProperty("来源id")
    private Integer originId;

    /**
     * 设计师
     */
    @ApiModelProperty("设计师")
    private String designer;
    /**
     * 设计师id
     */
    @ApiModelProperty("设计师id")
    private String designerId;

    @ApiModelProperty("完成时间")
    private Date completeDate;

    /**
     * 设计风格 id
     */
    @ApiModelProperty("设计风格id")
    private Integer designStyleId;

    /**
     * 设计风格 名称
     */
    @ApiModelProperty("设计风格名称")
    private String designStyleName;

    /**
     * 方案图片 id
     */
    @ApiModelProperty("方案图片 id")
    private Integer picId;

    /**
     * 方案图片地址
     */
    @ApiModelProperty("方案图片地址")
    private String picPath;

    /**
     * 方案备注
     */
    @ApiModelProperty("方案备注")
    private String planDesc;

    @ApiModelProperty("包含未公开产品 0 包含   1 不包含 ")
    private String containsSecrecyFlag;

    @ApiModelProperty("品牌id")
    private String brandId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("分配情况 2b 2c  ")
    private String distributionStatus;

    @ApiModelProperty("上架情况 id ,隔开")
    private String putawayStatusId;

    @ApiModelProperty("平台 ,隔开")
    private String platformNames;

    @ApiModelProperty("交付状态 Y, N")
    private String deliverStatus;

    @ApiModelProperty("平台 ,隔开")
    private String platformIds;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("上架状态")
    private String shelfStatus;

    @ApiModelProperty("公司名称")
    private String  companyName;

    @ApiModelProperty("是否使用过 Y / N")
    private String copied;

    @ApiModelProperty("空间名称")
    private String spaceCommonName;

    @ApiModelProperty("空间id")
    private String spaceCommonId;

    @ApiModelProperty("方案类型 1 分享 2 推介")
    private Integer recommendedType;

    @ApiModelProperty("对应空间类型id")
    private Integer spaceTypeId;

    @ApiModelProperty("对应空间类型名称")
    private String spaceTypeName;
    
    @ApiModelProperty("组合方案/单个方案")
    private String solutionType;
    
    @ApiModelProperty("审核方案/未审核方案")
    private String isCheck;
    
    @ApiModelProperty("是否公开:0-不公开;1公开")
    private Integer secrecyFlag;

    @ApiModelProperty("是否发布(店铺)")
    private String isPublish;

    private Integer shopId;
    @ApiModelProperty("方案价格")
    private Double planPrice;

    @ApiModelProperty("收费类型")
    private Integer chargeType;

    @ApiModelProperty("方案使用次数")
    private Integer useCount;

    /**
     * 是否需要设置方案价格
     */
    private Integer IsSetPlanPrice;

    @ApiModelProperty("是否主方案(0：主方案, 1：子方案)")
    private Integer isPrimary;
    /**
     * 是否有权限打开企业收益列表:0.没有权限 1.有权限
     */
    private Integer companyIncomePermissions;

    /**
     * 是否显示使用按钮:本公司不显示 0,其他公司显示 1
     */
    private Integer whetherDisplay;


    @ApiModelProperty("方案报价信息")
    private List<DecoratePriceInfo> decoratePriceInfoList;

    @ApiModelProperty(value = "售卖方案是否收费:0,免费,1收费")
    private  Integer salePriceChargeType;

    @ApiModelProperty(value = "方案售卖价格")
    private Double salePrice;

    @ApiModelProperty(value = "是否改变过：0 -> 否；1 -> 是")
    private Integer isChanged;
}
