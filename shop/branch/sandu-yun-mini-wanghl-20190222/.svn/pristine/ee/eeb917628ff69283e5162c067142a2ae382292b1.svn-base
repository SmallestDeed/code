package com.sandu.company.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工程案例列表VO界面
 *
 * @auth xiaoxc
 * @Data 2018-06-23
 */
@Data
@ApiModel(value = "工程案例", description = "店铺工程案例")
public class ProjectCaseVo implements Serializable {

    @ApiModelProperty(value = "案例ID")
    private Integer caseId;
    @ApiModelProperty(value = "案例标题")
    private String caseTitle;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "图片地址")
    private String picPath;
    @ApiModelProperty(value = "文本地址")
    private String filePath;
    @ApiModelProperty(value = "文本内容")
    private String content;
	@ApiModelProperty(value = "富文本图片IDS")
    private String picIds;
    @ApiModelProperty(value = "浏览数量")
	private Integer browseCount;

    @ApiModelProperty(value = "类目数量,根据shopId统计")
    private Integer count;

    @ApiModelProperty(value = "店铺Id")
    private Integer shopId;

    @ApiModelProperty(value = "店铺头像")
    private String shopLogo;

    @ApiModelProperty(value = "n天前发布")
    private Integer releaseDay;

    @ApiModelProperty(value = "店铺详情")
    private CompanyShopDetailVo shopDetail;

    @ApiModelProperty(value = "来源ID")
    private Integer sid;
}
