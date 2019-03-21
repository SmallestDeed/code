package com.sandu.api.solution.input;

import com.sandu.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 15:18 2018/8/22
 */
@Data
public class FullHouseDesignPlanQuery extends BaseQuery implements Serializable {


    @ApiModelProperty("方案id")
    @Min(value = 1, message = "方案id不正确")
    private Integer id;

    @ApiModelProperty("方案来源")
    @Length(max = 30,message = "最长 {max}")
    private String sourceType;

    @ApiModelProperty("方案名称/设计师名称/编号")
    @Length(max = 50,message = "方案名称最长{max}")
    private String queryName;

    @ApiModelProperty("品牌id")
    private String brandId;

    @ApiModelProperty("上架平台")
    @Length(max = 20,message = "上架平台最长{max}")
    private String platformId;

    @ApiModelProperty(value = "公司id",required = true)
    @Min(value = 1, message = "公司id不正确")
    @NotNull(message = "公司id不能为空")
    private Integer companyId;

    @ApiModelProperty(value = "分享公司id")
    private Integer shareCompanyId;

    @ApiModelProperty("交付状态 Y ,N")
    @Length(max = 1)
    private String deliverStatus;

    @ApiModelProperty("店铺ID")
    private Integer shopId;

    @ApiModelProperty("是否发布")
    private Integer isPublish;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("方案是否公开:0.未公开,1.公开")
    private Integer openState;

    @ApiModelProperty("方案售卖价格是否收费")
    private Integer salePriceChargeType;

    @ApiModelProperty("方案版权费是否免费")
    private Integer chargeType;

    @ApiModelProperty("方案风格ID")
    private Integer planStyleId;

    private String planGroupStyleId;
    private List<Integer> listGroupStyleId;
}
