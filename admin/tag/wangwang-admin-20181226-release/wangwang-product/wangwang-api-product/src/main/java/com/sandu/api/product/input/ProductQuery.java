package com.sandu.api.product.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class ProductQuery implements Serializable {
    @ApiModelProperty(value = "分类编码")
    @Length(max = 100, message = "分类编码长度错误,应小于{max}")
    private String categoryCode;
    /**
     * 分配状态
     */
    @ApiModelProperty(value = "分配状态, 未分配:nonAllot,渠道:channel,线上:online")
    @Pattern(regexp = "^(nonAllot|channel|online)$", message = "过滤查询,分配状态参数有误")
    private String allotState;
    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    @Size(max = 20, message = "产品编码过长")
    private String productCode;
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    @Size(max = 40, message = "产品名称过长")
    private String productName;
    /**
     * 产品公开状态
     */
    @ApiModelProperty(value = "产品公开状态：-1未选择,0公开，1不公开")
    @Range(min = -1, max = 1, message = "请选择正确的公开状态参数")
    private Integer secrecy;

    @ApiModelProperty(value = "页码", required = true)
    @Min(value = 1, message = "请输入正确的数值")
    @NotNull(message = "页码不能为空")
    private Integer page;

    @ApiModelProperty(value = "条数", required = true)
    @Min(value = 1, message = "请输入正确的数值")
    @NotNull(message = "条数不能为空")
    private Integer limit;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String orderField;
    /**
     * 排序方式
     */
    @ApiModelProperty(value = "排序方式")
    private String orderMethod;

    @ApiModelProperty(value = "查询类型:库查询library/渠道查询channel/线上查询online",required = true)
    @Pattern(regexp = "^(library|channel|online)$", message = "查询类型有误,请检查")
    @NotEmpty
    private String queryType;

    @ApiModelProperty(value = "线上平台ID,线上用于过滤查询")
    @Min(value = -1, message = "请输入正确的数值")
    private Integer platformId;

    @ApiModelProperty(value = "渠道产品上下架状态:1上架/0下架/-1全部")
    @Range(max = 1, min = -1, message = "请输入正确的上下架状态")
    private Integer putawayState;

    @ApiModelProperty(value = "企业ID",required = true)
    @Min(value = 1, message = "请输入正确的企业ID")
    @NotNull(message = "企业ID不能为空")
    private Integer companyId;

    @ApiModelProperty(value = "产品型号")
    private String productModelNumber;

    @ApiModelProperty(value = "产品小类编码")
    private String productType;

    /**
     * Added by songjianming@sanduspace.com on 2018/12/20
     * 产品库列表页面增加产品大类筛选。为兼容以前productType
     * @link http://jira.3du-inc.net/browse/CMS-634
     */
    @ApiModelProperty(value = "产品大类编码")
    private String bigProductType;
}
