package com.sandu.api.storage.input;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * create by bvvy
 */
@Data
@ApiModel("删除文件信息")
public class FileDelDO {

    @ApiModelProperty(value = "资源id们", required = true)
    @NotNull
    @Size(min = 1,message = "必须要一个资源id")
    private List<Long> resIds;

    @ApiModelProperty(value = "文件类型", required = true)
    @NotEmpty(message = "类型不能为空")
    private String type;
}
