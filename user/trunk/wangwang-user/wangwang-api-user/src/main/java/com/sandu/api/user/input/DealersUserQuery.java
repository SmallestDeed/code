package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author WangHaiLin
 * @date 2018/6/5  16:44
 */
@Data
public class DealersUserQuery implements Serializable {

    @ApiModelProperty(value = "企业Id")
    private Long companyId;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "经销商企业Id")
    private Long dealersCompanyId;

    @ApiModelProperty(value="每页数量:默认10")
    private Integer limit;

    @ApiModelProperty(value="当前页:默认第一页")
    private Integer page;

}
