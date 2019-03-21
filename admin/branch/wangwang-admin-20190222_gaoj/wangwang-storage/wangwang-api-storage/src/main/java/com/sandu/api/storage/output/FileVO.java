package com.sandu.api.storage.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * create by bvvy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileVO implements Serializable{

    @ApiModelProperty("资源id")
    private Long resId;

    @ApiModelProperty("资源路径")
    private String url;

    @ApiModelProperty("资源大小 单位字节")
    private Long size;

    @ApiModelProperty("图片高度")
    private Integer height;

    @ApiModelProperty("图片宽度")
    private Integer width;

}

