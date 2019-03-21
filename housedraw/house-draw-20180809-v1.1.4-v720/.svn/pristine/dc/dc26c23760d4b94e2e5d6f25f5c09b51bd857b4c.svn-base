package com.sandu.api.house.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/28
 */
@Data
public class DrawSpaceCommonBO {
    
    /**
     * space_common.id
     */
    private Long spaceId;

    /**
     * 通用房型编码<p>
     * space_common.space_code
     */
    private String spaceCode;

    /**
     * 通用房型名称<p>
     * space_common.space_name
     */
    private String spaceName;

    /**
     * 空间功能类型
     */
    private Integer spaceFunctionId;

    /** 空间长度*/
    private String mainLength;

    /** 空间宽度*/
    private String mainWidth;

    /** 空间面积*/
    private String spaceAreas;

    /** 空间形状*/
    private String spaceShape;

    @ApiModelProperty("文件id")
    private Long fileId;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件大小")
    private String fileSize;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("文件编码")
    private String fileCode;

    @ApiModelProperty("文件后缀")
    private String fileSuffix;

    @ApiModelProperty("文件原名称")
    private String fileOriginalName;

    /** 空间下产品信息*/
    private List<DrawBaseProductBO> productData;

}
