package com.sandu.api.solution.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
/**
 * DesignPicUpdate class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
public class DesignPicUpdate implements Serializable {

    @ApiModelProperty("图片id")
    @Min(value = 1,message = "请传入合法的图片id")
    private Integer picId;

    @ApiModelProperty("方案id")
    @Min(value = 1,message = "请传入合法的方案方案id")
    private Integer planId;
}
