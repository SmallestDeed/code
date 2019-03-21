package com.sandu.api.groupproducct.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * wangwang-product
 *
 * @author Sandu
 * @datetime 2018/3/27 16:08
 */
@Data
public class GroupAdd implements Serializable {
    @ApiModelProperty("组合类型")
    @Range(min = 1, message = "请检查组合类型ID")
    private Integer groupType;
    @ApiModelProperty("组合名称")
    private String groupName;
    @ApiModelProperty("公司id")
    @Range(min = 1, message = "公司ID有误")
    @NotNull(message = "公司ID不能为空")
    private Integer companyId;
    @ApiModelProperty("产品id集合")
    @Size(min = 1, message = "产品信息有误")
    private List<Integer> productIds;
    @ApiModelProperty("品牌ID")
    @Range(min = 1, message = "品牌ID有误")
    @NotNull(message = "品牌ID不能为空")
    private Integer brandId;
    @ApiModelProperty("主产品ID")
    @Range(min = 1, message = "主产品ID有误")
    @NotNull(message = "主产品ID不能为空")
    private Integer mainProductId;
    @ApiModelProperty("用户ID")
    @Range(min = 1, message = "用户ID有误")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;


}
