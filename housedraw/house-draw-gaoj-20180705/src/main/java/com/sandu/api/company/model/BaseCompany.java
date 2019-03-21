package com.sandu.api.company.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseCompany implements Serializable {
    @ApiModelProperty("公司ID")
    private Integer id;

    @ApiModelProperty("公司名称")
    private String companyName;
}
