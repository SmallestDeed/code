package com.sandu.panorama.model.input;

import com.sandu.common.model.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DesignPlanStoreReleaseSearch extends Mapper implements Serializable {

    @ApiModelProperty(value = "创建人", dataType = "Integer", hidden = true)
    private Integer userId;
    @ApiModelProperty(value = "分享标题", dataType = "String")
    private String shareTitle;
    @ApiModelProperty(value = "分享类型（1:全户型分享、2:随意组合分享）", required = true, dataType = "Integer")
    private Integer shareType;
    @ApiModelProperty(value="小区名称",dataType = "String")
    private String livingName;
    @ApiModelProperty(value = "小区Id集合",dataType = "List", hidden = true)
    private List<Integer> livingIds;

}
