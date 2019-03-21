package com.sandu.api.house.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2018/1/3
 */
@Data
public class SystemDictionarySearch {

    @ApiModelProperty("分类")
    private String type;

    @ApiModelProperty("小分类")
    private String valuekey;

    @ApiModelProperty("value值")
    private String value;

    @ApiModelProperty("是否白膜")
    private String att3;
    
    private String att4;
    
    private String att6;
}
