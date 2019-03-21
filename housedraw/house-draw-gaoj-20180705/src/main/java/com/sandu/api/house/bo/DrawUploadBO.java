package com.sandu.api.house.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2018/1/6
 */
@Data
public class DrawUploadBO {

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("文件名称")
    private String fileName;
    
    @ApiModelProperty("文件唯一标识")
    private String fileUniqueId;
}
