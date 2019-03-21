package com.sandu.panorama.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by chenm on 2018/8/2.
 */
@Data
public class UnionSpecialOfferSearch implements Serializable {
    private static final long serialVersionUID = -526015776952446143L;
    @ApiModelProperty(value = "起始行", dataType = "int")
    private Integer start;
    @ApiModelProperty(value = "每页数据数", dataType = "int")
    private Integer limit;
    @ApiModelProperty(value = "720制作唯一标识",dataType = "String")
    private String uuid;
    @ApiModelProperty(value= "创建者",dataType = "int",hidden = true)
    private Integer userId;
    @ApiModelProperty(value="720制作关联的优惠活动Id",dataType = "int",hidden = true)
    private Integer unionOfferId;
    @ApiModelProperty(value="是否显示,0为不显示，1为显示",dataType = "int",hidden = true)
    private Integer isDisplayed;

}
