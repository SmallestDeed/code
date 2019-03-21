package com.sandu.api.user.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 内部账号拥有的服务
 * @author WangHaiLin
 * @date 2018/6/4  19:23
 */
@Data
public class UserServiceVO implements Serializable {

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "服务到期时间")
    private Date serviceEndTime;

    @ApiModelProperty(value = "服务类型：0-正式套餐;1-试用套餐")
    private String servicesType;

}
