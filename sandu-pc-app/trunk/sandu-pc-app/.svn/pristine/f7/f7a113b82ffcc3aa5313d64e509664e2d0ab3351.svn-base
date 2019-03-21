package com.sandu.panorama.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class UnionContactSearch implements Serializable{

	private static final long serialVersionUID = -526015776952446143L;
	
	@ApiModelProperty(value = "起始行", dataType = "int")
    private Integer start;
    @ApiModelProperty(value = "每页数据数", dataType = "int")
    private Integer limit;
    @ApiModelProperty(value="720制作打组唯一标识",dataType = "String")
    private String uuid;
    @ApiModelProperty(value = "创建人", dataType = "int", hidden = true)
    private Integer userId;
    @ApiModelProperty(value="720打组关联联系人Id",dataType = "int",hidden = true)
    private Integer contactId;
    @ApiModelProperty(value="是否被删除",hidden = true)
    private Integer isDeleted;
}
