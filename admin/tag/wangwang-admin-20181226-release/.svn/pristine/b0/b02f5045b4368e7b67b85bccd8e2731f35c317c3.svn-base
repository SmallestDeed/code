package com.sandu.api.resmodel.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class  ModelAreaTextureRelBO implements Serializable {

    @ApiModelProperty(value = "贴图区域ID")
    @Min(value = 1,message = "请输入正确的ID")
    private Integer areaId;
    @ApiModelProperty(value = "贴图ID")
    @Min(value = 1,message = "请输入正确的ID")
    private Integer textureId;
    @ApiModelProperty(value = "是否为默认贴图:1是,0否")
    @Range(min = 0,max = 1,message = "请检查默认贴图参数是否正确")
    private Integer isDefault;
    @ApiModelProperty(value = "影响价格",hidden = true)
    @Range(max = 1000000,message = "请输入正确的价格")
    private Integer affectPrice;
}
