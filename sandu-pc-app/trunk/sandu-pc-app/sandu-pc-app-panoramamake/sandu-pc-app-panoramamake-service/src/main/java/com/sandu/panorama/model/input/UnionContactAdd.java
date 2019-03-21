package com.sandu.panorama.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class UnionContactAdd implements Serializable{

    @ApiModelProperty(value = "联系人名称", dataType = "string", required = true)
    @NotBlank(message = "联系人名称不能为空")
    private String name;
    @ApiModelProperty(value = "联系人电话", dataType = "string", required = true)
    private String phone;
    @ApiModelProperty(value = "联系人头像(使用base64编码传递)", dataType = "string")
    private String pic;
}
