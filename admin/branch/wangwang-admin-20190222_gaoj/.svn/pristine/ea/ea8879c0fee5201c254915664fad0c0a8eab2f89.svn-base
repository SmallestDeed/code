package com.sandu.api.solution.input;

import com.sandu.api.solution.model.bo.DecoratePriceInfo;
import com.sandu.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 15:18 2018/8/22
 */
@Data
public class FullHouseDesignPlanUpdate implements Serializable {


    @ApiModelProperty("方案id")
    @NotNull(message = "方案id不能为空")
    @Min(value = 1, message = "方案id不正确")
    private Integer id;

    @ApiModelProperty("方案名称")
    @Length(max = 50,message = "方案名称最长{max}")
    private String planName;

    @ApiModelProperty("品牌id")
    private String brandId;

    @ApiModelProperty("风格id")
    private Integer planStyleId;

    @ApiModelProperty("风格名称")
    private String planStyleValue;

    @ApiModelProperty("方案描述")
    private String planDescribe;

    @ApiModelProperty("方案备注")
    private String remark;

    @ApiModelProperty(value = "公司id",required = true)
    @Min(value = 1, message = "公司id不正确")
    @NotNull(message = "公司id不能为空")
    private Integer companyId;

    @ApiModelProperty("修改人id")
    private String modifier;

    @ApiModelProperty("方案报价信息")
    private List<DecoratePriceInfo> decoratePriceInfoList;

}
