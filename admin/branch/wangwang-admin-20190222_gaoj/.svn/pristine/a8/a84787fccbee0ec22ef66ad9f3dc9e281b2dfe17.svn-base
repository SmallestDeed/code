package com.sandu.api.company.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class CompanyVO implements Serializable {
    /**
     * id
     */
    private Long id;
    private Integer[] categoryIds;
    /**
     * logo 图片
     */
    @ApiModelProperty("企业logo资源路径")
    private String logo;
    /**
     * 企业名称
     */
    @ApiModelProperty("企业名称")
    private String name;
    /**
     * 企业二级域名
     */
    @ApiModelProperty("企业二级域名")
    private String domain;
    /**
     * 企业qq
     */
    @ApiModelProperty("企业QQ")
    private String qq;
    /**
     * 品牌名称
     */
    @ApiModelProperty("企业品牌信息")
    private String brands;
    @ApiModelProperty("分类名称")
    private List<String> categoryNames;
}
