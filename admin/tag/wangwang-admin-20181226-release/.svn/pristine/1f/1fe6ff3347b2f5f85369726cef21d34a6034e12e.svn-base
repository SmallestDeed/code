package com.sandu.api.basewaterjet.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/11/13 0013 11:13
 * @Modified By
 */
@Data
public class StatusUpdate implements Serializable {
    private static final long serialVersionUID = -2597570137172559106L;

    @ApiModelProperty(value = "idStr")
    @NotBlank(message = "id不能为空")
    private String basewaterjetIds;

    @ApiModelProperty(value = "状态")
    @NotBlank(message = "状态不能为空")
    private String templateStatus;

}
