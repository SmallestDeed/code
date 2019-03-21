package com.sandu.api.solution.input;

import com.sandu.api.solution.model.bo.DecoratePriceInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * DesignPlanUpdate class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
@ApiModel("修改设计方案")
public class DesignPlanUpdate implements Serializable {

    @ApiModelProperty(value = "方案名称", required = true)
    @NotEmpty(message = "方案名称不能为空")
    private String planName;

    @ApiModelProperty(value = "方案id", required = true)
    @NotNull(message = "方案不能为空")
    @Min(value = 1, message = "方案id不合法")
    private Integer planId;

    @ApiModelProperty(value = "品牌ids")
    private List<Integer> brandIds;

    @ApiModelProperty(value = "风格id", required = true)
    @NotNull(message = "方案风格不能为空")
    @Min(value = 1, message = "风格id不合法")
    private Integer styleId;

    @ApiModelProperty(value = "方案描述")
    @Length(max = 200, message = "方案描述长度小于{max}")
    private String planDesc;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "图片di")
    @Min(value = 1, message = "图片id不合法")
    private Integer picId;

    @ApiModelProperty("图片地址")
    @Length(max = 200, message = "图片地址不合法")
    private String picPath;

    @ApiModelProperty("公司id")
    @NotNull(message = "公司id不能为空")
    @Min(value = 1, message = "公司id不能小于{value}")
    private Integer companyId;

    @ApiModelProperty("方案报价信息")
    @NotNull(message = "报价信息不能为空")
//    @Size(min = 1,message = "请选择至少一组报价信息")
    private List<DecoratePriceInfo> decoratePriceInfoList;


}
