package com.sandu.api.platform.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class PlatformVO implements Serializable{
    /**id*/
    private long id;
    @ApiModelProperty("平台名称")
    private String name;
}
