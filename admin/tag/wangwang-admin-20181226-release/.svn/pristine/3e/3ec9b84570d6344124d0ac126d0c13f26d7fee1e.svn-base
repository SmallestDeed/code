package com.sandu.api.decorateorder.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * <p>decorate_order
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecorateOrderVO implements Serializable {


    @ApiModelProperty(value = "订单ID")
    private Integer id;

    @ApiModelProperty(value = "签约装企/退款装企")
    private String companyName;

    @ApiModelProperty(value = "合同状态")
    private Integer contractStatus;

    @ApiModelProperty(value = "合同状态名称")
    private String contractStatusStr;

    @ApiModelProperty(value = "合同查看")
    private Integer contractId;

    @ApiModelProperty(value = "签约价格")
    private String contractFee;

    @ApiModelProperty(value = "服务费（签约价格*5%）")
    private String serviceFee;

    @ApiModelProperty(value = "财务收款状态")
    private Integer financeReceiptsStatus;

    @ApiModelProperty(value = "财务收款状态名称")
    private String financeReceiptsStatusStr;

    @ApiModelProperty(value = "退款状态")
    private Integer refundStatus;

    @ApiModelProperty(value = "退款状态名称")
    private String refundStatusStr;

    @ApiModelProperty(value = "退款原因")
    private String refundReason;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "城市")
    private String cityName;

    @ApiModelProperty(value = "驳回原因")
    private String refundRejectReason;	
    
    @ApiModelProperty(value = "合同下载链接")
    private String contractUrl;
}
