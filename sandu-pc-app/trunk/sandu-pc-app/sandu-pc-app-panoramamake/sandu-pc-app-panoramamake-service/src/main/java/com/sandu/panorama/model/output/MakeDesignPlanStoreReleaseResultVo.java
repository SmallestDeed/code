package com.sandu.panorama.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by chenm on 2018/10/19.
 */
@Data
public class MakeDesignPlanStoreReleaseResultVo implements Serializable {

    @ApiModelProperty(value = "720组合分享ID", dataType = "Integer")
    private Integer id;
    @ApiModelProperty(value = "720组合分享UUID", dataType = "String")
    private String uuid;
}
