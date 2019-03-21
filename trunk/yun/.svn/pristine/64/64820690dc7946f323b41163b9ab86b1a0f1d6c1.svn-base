package com.sandu.pay2.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RechargeIntegral implements Serializable {
    private static final long serialVersionUID = 1L;
    private String msgId = null;
    /**
     * 产品id
     */
    @NotNull(message = "productId不能为空")
    @ApiModelProperty(value = "产品id", required = true)
    private Integer productId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 平台编码
     */
    private String platformCode;
}
