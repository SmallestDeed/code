package com.sandu.api.groupproducct.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class GroupQuery implements Serializable {
    @ApiModelProperty("组合编码")
    private String groupCode;

    @ApiModelProperty("组合名称")
    private String groupName;

    @ApiModelProperty("组合分配状态,未分配:nonAllot,渠道:channel,线上:online")
    @Pattern(regexp = "^(nonAllot|channel|online)$", message = "请输入有效的分配状态")
    private String allotState;

    @ApiModelProperty("组合公开状态,1公开/0未公开")
    @Range(min = 0, max = 1, message = "请输入有效的的公开的状态")
    private String secrecy;

    @ApiModelProperty("排序字段")
    private String orderField;

    @ApiModelProperty("排序方式")
    private String orderMethod;

    @ApiModelProperty("渠道上架状态,1上架/0下架")
    @Range(min = 0, max = 1, message = "请输入有效的的渠道上架状态")
    private String putawayState;

    @ApiModelProperty("线上上架状态,平台ID")
    @Min(value = -1,message = "请输入线上平台ID")
    private String platformId;

    @ApiModelProperty(value = "企业ID", required = true)
    @Min(value = 1,message = "请输入有效的企业ID")
    @NotNull(message = "请输入有效的企业ID")
    private Integer companyId;

    @ApiModelProperty(value = "页码,从1起", required = true)
    @Min(value = 1,message = "页码无效")
    private int page;

    @ApiModelProperty(value = "页面条目", required = true)
    @Min(value = 1,message = "每页数量无效")
    private int limit;

    @ApiModelProperty(hidden = true)
    private List<Long> brandIds;

    @ApiModelProperty(value = "组合类型")
    @Min(value = 1,message = "请输入正确的组合类型")
    private Integer compositeType;


    @ApiModelProperty(value = "渠道的某一平台ID",hidden = true)
    private Integer platform2bId;
    @ApiModelProperty(value = "线上的某一平台ID",hidden = true)
    private Integer platform2cId;
}
