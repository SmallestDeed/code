package com.sandu.product.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by chenm on 2018/8/1.
 */
@Data
public class BaseCompanyVo implements Serializable{

    @ApiModelProperty(value="企业Id")
    private Integer id;
    @ApiModelProperty(value="企业名称")
    private String name;
    @ApiModelProperty(value="联系人电话")
    private String phone;
    @ApiModelProperty(value="企业地址")
    private String address;
}
