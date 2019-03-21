package com.sandu.api.servicepurchase.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfficalServicesListVO implements Serializable {
	
    @ApiModelProperty(value = "套餐id")
    private Long id;

    @ApiModelProperty(value = "套餐名称")
    private String servicesName;

    @ApiModelProperty(value = "试用时长")
    private Integer duration;
    
    @ApiModelProperty(value = "赠送度币")
    private Integer sanduCurrency;
    
    @ApiModelProperty(value = "优惠价")
    private BigDecimal price;

}
