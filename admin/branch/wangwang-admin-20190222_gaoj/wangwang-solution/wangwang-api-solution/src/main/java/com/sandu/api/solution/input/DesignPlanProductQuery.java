package com.sandu.api.solution.input;

import com.sandu.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DesignPlanProductQuery class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
public class DesignPlanProductQuery extends BaseQuery implements Serializable {



    @ApiModelProperty(value = "方案id", required = true)
    @Min(value = 1,message = "方案id不合法")
    private Integer planId;

    @ApiModelProperty(value = "类型")
    @Length(max = 30,message = "类型最长30")
    private String categoryCode;

    @ApiModelProperty(value = "公开")
    @Min(value = 0,message = "公开参数不合法")
    @Max(value = 1,message = "公开参数不合法")
    private Integer secrecy;

    @ApiModelProperty("显示是否 0 , 1 ")
    @Min(value = 0,message = "显示参数不合法")
    @Max(value = 1,message = "显示参数不合法")
    private Integer displayStatus;

}
