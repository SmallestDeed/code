package com.sandu.banner.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 广告--位置查询入参
 * @author WangHaiLin
 * @date 2018/5/14  13:40
 */
@Data
public class BaseBannerPositionQuery implements Serializable {

    @ApiModelProperty(value ="使用位置type (1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺)",required = true)
    @Min(value = 1,message = "entityType最小是1")
    @Max(value = 3,message = "entityType最大是3")
    @NotNull(message = "banner使用位置type不能为空")
    private Integer type;

    @ApiModelProperty(value = "位置编码(system:module:page:positon)",required = true)
    @NotNull(message = "位置编码不能为空")
    private String code;


}
