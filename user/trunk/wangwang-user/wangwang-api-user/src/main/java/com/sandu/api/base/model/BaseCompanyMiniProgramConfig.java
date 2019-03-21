package com.sandu.api.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *企业微信信息
 * @author WangHaiLin
 * @date 2018/6/22  14:08
 */
@Data
public class BaseCompanyMiniProgramConfig implements Serializable {

    @ApiModelProperty(value = "主键Id")
    private Long id;

    @ApiModelProperty(value = "企业微信appid")
    private String appId;

    @ApiModelProperty(value = "企业微信app_secret")
    private String appSecret;

    @ApiModelProperty(value = "企业微信商户id")
    private String mchId;

    @ApiModelProperty(value = "key")
    private String mchKey;

    @ApiModelProperty(value = "企业id")
    private Long companyId;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;

    @ApiModelProperty(value = "是否删除：0未删除、1已删除")
    private Integer isDeleted;

}
