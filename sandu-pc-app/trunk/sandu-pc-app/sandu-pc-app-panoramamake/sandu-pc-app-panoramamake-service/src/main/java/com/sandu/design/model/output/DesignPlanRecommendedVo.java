package com.sandu.design.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DesignPlanRecommendedVo implements Serializable{

    @ApiModelProperty(value = "推荐方案ID", dataType = "Integer")
    private Integer planRecommendedId;
    @ApiModelProperty(value = "推荐方案名称", dataType = "String")
    private String planName;
    @ApiModelProperty(value = "空间类型", dataType = "String")
    private String spaceType;
    @ApiModelProperty(value = "推荐方案封面图", dataType = "String")
    private String picPath;
    @ApiModelProperty(value = "720路径", dataType = "String")
    private String targetResourcePath;
    @ApiModelProperty(value = "目录or文件(1 or 0)", dataType = "Integer")
    private Integer isShear = IsShear.NO;

    public class IsShear{
        public static final int YES = 1;// 是
        public static final int NO = 0;// 否
        public static final int DIRNOEXISTS = 2;// 目录不存在
    }

}
