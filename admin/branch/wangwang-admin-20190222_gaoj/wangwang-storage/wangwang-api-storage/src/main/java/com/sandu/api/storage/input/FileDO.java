package com.sandu.api.storage.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by bvvy
 */
@Data
@ApiModel("上传文件填写信息")
public class FileDO {

    @ApiModelProperty(value = "文件的fileKey res.properties中的前缀",required = true)
    private String fileKey;

    @ApiModelProperty(value = "上传文件类型 目前只有 image file model三种",required = true)
    private String fileType;
}
