package com.sandu.api.product.input;

import com.sandu.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
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
 * @datetime 2018/3/22 9:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorProductQuery extends BaseQuery implements Serializable {
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
    @Size(max = 20, message = "产品名称过长")
    private String productName;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "模型编码")
    @Size(max = 20, message = "模型编码过长")
    private String modelCode;
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品型号")
    @Size(max = 20, message = "产品型号过长")
    private String modelNumber;

    @ApiModelProperty(value = "分类编码")
    @Length(max = 20, message = "分类编码长度错误,应小于{max}")
    private String categoryCode;

    @ApiModelProperty(value = "分类ID,用于mapper查询",hidden = true)
    private List<Integer> categoryIds;

    @ApiModelProperty(value = "企业ID",required = true)
    @Min(value = 1, message = "请输入正确的企业ID")
    @NotNull(message = "企业ID不能为空")
    private Integer companyId;

    @ApiModelProperty(hidden = true)
    private List<Integer> brandIds;


    private String productType;
}
