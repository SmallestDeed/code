package com.sandu.api.solution.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandWithDeliveredVO implements Serializable{

    @ApiModelProperty("品牌id")
    private Integer brandId;
    @ApiModelProperty("公司id")
    private Integer brandCompanyId;
    @ApiModelProperty("品牌名称")
    private String brandName;
    @ApiModelProperty("品牌所属公司是否交付 0 , 1 ")
    private Integer delivered;
}
