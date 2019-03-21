package com.sandu.api.companyshop.input;

import com.sandu.common.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/11/29 14:03
 * @since 1.8
 */

@Data
public class CompanyshopofflineQuery extends BaseQuery implements Serializable {

    @ApiModelProperty(value = "门店名称")
    private String shopName;

    @ApiModelProperty(value = "是否发布")
    private Integer isRelease;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "街道编码")
    private String streetCode;

    @ApiModelProperty(value = "认领状态")
    private Integer claimStatus;

    @ApiModelProperty(value = "公司ID")
    private Integer companyId;
}
