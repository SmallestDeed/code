package com.sandu.api.shop.output;

import com.sandu.api.pic.model.ResPic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 工程案例展示详情vo界面
 *
 * @auth xiaoxc
 * @data 2018-06-19
 */
@Data
public class ProjectCaseDetailsVO implements Serializable {

    @ApiModelProperty(value="案例Id")
    private Integer caseId;

    @ApiModelProperty(value="案例标题")
    private String caseTitle;

    @ApiModelProperty(value="案例内容文本路径")
    private String filePath;

    @ApiModelProperty(value="案例内容文本")
    private String content;

    @ApiModelProperty(value = "图片Ids")
    private String picIds;

    @ApiModelProperty(value = "博文图片集合")
    private List<ResPic> resPics;

}
