package com.sandu.api.banner.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author WangHaiLin
 * @date 2018/5/16  14:34
 */
@Data
public class BaseBannerPositionIsExist implements Serializable {

    @ApiModelProperty(value = "位置编码", required = true)
    @NotNull(message = "位置编码不能为空")
    @Size(min = 1, max = 80, message = "编码长度度限{min}-{max}个字符")
    private String code;

    @ApiModelProperty(value = "位置类型(1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺)", required = true)
    @NotNull(message = "位置类型不能为空")
    @Range(min = 1, max = 3, message = "无效状态,限{min}-{max}间整数")
    private Integer type;
}
