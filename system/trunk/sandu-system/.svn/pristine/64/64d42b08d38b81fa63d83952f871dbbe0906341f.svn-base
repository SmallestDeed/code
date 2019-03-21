package com.sandu.api.servicepurchase.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandu.api.servicepurchase.input.ServicesPriceAdd;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ServicesPriceVO implements Serializable {

    private static final long serialVersionUID = -3667414028969944922L;
    /** 价格id*/
    @ApiModelProperty(value = "价格id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Long priceId;

    /** 价格名称*/
    @ApiModelProperty(value = "价格id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String priceName;

    private Long id;

    private Integer companyId;

    private String companyName;

    private String companyCode;

    private Long servicesId;

    private List<ServicesPriceAdd> prices;

    private List<Integer> duration;

    private List<Integer> giveDuration;

    private List<Integer> freeRenderDuration;

    private List<Integer> sanduCurrency;

    private List<BigDecimal> price;

    private List<String> priceUnit;
    
}