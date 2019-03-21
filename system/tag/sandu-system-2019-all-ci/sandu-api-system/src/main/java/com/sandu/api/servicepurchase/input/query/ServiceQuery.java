package com.sandu.api.servicepurchase.input.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ServiceQuery implements Serializable {

    /**
     * 用户类型
     */
    @ApiModelProperty("用户类型")
    private Integer userScope;
    /**
     * 公司ID
     */
    @ApiModelProperty("企业ID")
    @NotNull(message = "企业ID不能为空")
    private Integer companyId;

    @ApiModelProperty("服务ID")
    private Integer serviceId;

    @ApiModelProperty("购买来源(1、三度官网，2、三度后台，3、商家后台，4、科创，5、抢工长)")
    private String saleChannel;


    @Min(value = 1, message = "页面条数不能小于1")
    private Integer limit = 0;

    @Min(value = 1, message = "页码不能为0")
    private Integer page = 10;

    private Integer userId;

}
