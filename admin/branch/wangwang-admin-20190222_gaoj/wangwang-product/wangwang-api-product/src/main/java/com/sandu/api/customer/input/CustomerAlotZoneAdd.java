package com.sandu.api.customer.input;

import com.sandu.api.customer.model.AllCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 11:05 2018/8/1
 */

@Data
public class CustomerAlotZoneAdd implements Serializable {

    private static final long serialVersionUID = -7228833348783580948L;

    @ApiModelProperty("厂商id")
    private Integer companyId;

    @ApiModelProperty("经销商id")
    private Integer channelCompanyId;

    @ApiModelProperty("区域编码集")
    private List<AllCode> AllCode;

    @ApiModelProperty("账户")
    private String userName;
}
