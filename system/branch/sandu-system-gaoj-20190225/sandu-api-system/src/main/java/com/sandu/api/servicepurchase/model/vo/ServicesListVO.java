package com.sandu.api.servicepurchase.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.sandu.api.servicepurchase.output.ServicesPriceListVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicesListVO implements Serializable {
    @ApiModelProperty(value = " 套餐id ")
    private String servicesId;

    @ApiModelProperty(value = " 套餐编码 ")
    private String servicesCode;

    @ApiModelProperty(value = " 套餐名称 ")
    private String servicesName;

    @ApiModelProperty(value = " 适用用户(从数据字典读取),多种用户以逗号分隔 ")
    private List<String> scopeNames;

    @ApiModelProperty(value = " 生效策略(0-立即生效;1使用时生效) ")
    private String effectiveStrategy;

    @ApiModelProperty(value = " 套餐描述 ")
    private String serviceDesc;

    @ApiModelProperty(value = " 套餐折扣 ")
    private BigDecimal serviceDiscount;

    @ApiModelProperty(value = " 套餐价格(元)")
    private BigDecimal price;

    @ApiModelProperty(value = " 价格单元(0-年;1-月;2-日)")
    private String priceUnit;
    
    @ApiModelProperty(value = " 促销价 ")
    private List<ServicesPriceListVO> priceList;


}
