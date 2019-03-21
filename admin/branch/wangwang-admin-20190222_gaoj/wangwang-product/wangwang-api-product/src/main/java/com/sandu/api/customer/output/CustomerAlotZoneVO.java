package com.sandu.api.customer.output;

import com.sandu.api.customer.model.AllCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 11:17 2018/8/1
 */
@Data
public class CustomerAlotZoneVO implements Serializable{

    @ApiModelProperty("经销商id")
    private Integer channelCompanyId;

    @ApiModelProperty("经销商名称")
    private String channelCompanyName;

    @ApiModelProperty("区域对象")
    private List<AllCode> AllCode;
}
