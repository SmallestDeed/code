package com.sandu.api.servicepurchase.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ServicesBaseInfoVO implements Serializable {

    private static final long serialVersionUID = -1305035820577215241L;
    @ApiModelProperty(value = "套餐id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
	private Long id;

    @ApiModelProperty(value = "套餐名称")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String servicesName;

    @ApiModelProperty(value = "适用用户")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<UserScopeVO> userScope;
    
    @ApiModelProperty(value = "时长选择")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<ServicesPriceVO> priceList;

    @ApiModelProperty(value = "套餐价格 ")
    @JsonInclude(JsonInclude.Include.ALWAYS)
	private BigDecimal price;
    
    @ApiModelProperty(value = "套餐价格 ")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private BigDecimal serviceDiscount;

}