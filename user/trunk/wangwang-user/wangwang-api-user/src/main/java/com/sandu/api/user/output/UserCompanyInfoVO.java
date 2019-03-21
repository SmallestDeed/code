package com.sandu.api.user.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 内部账号拥有的服务
 * @author xiaoxc
 * @date 2018/6/24
 */
@Data
public class UserCompanyInfoVO implements Serializable {

    @ApiModelProperty(value = "企业ID")
    private Long companyId;

    @ApiModelProperty(value = "经销商ID")
    private Long franchiserId;

}
