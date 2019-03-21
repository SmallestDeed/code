package com.sandu.api.house.output;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2018/1/2
 */
@Data
public class UploadFileVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件原名称")
    private String fileOriginalName;

    @ApiModelProperty("文件大小")
    private String fileSize;

    @ApiModelProperty("文件地址")
    private String filePath;

    @ApiModelProperty("文件后缀")
    private String fileSuffix;

    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("文件格式")
    private String fileFormat;

    @ApiModelProperty("高")
    private String height;

    @ApiModelProperty("宽度")
    private String width;
    
    @ApiModelProperty("文件id,暂时不区分是pic还是file")
    private Long fileId;
    
    @ApiModelProperty("回填id")
    private Long businessId;
    
    @ApiModelProperty("回填code")
    private String businessCode;
    
    private String fileUniqueId;
}
