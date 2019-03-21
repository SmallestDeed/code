package com.sandu.api.groupproducct.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Sandu
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupProductListBO implements Serializable {
    private Integer id;
    @ApiModelProperty("组合编码")
    private String groupCode;
    @ApiModelProperty("组合名称")
    private String groupName;
    @ApiModelProperty("品牌名称")
    private String brandName;
    private Integer brandId;
    @ApiModelProperty("组合类型")
    private String compositeTypeName;
    private Integer compositeType;
    @ApiModelProperty("组合线上分配状态")
    private Integer status2c;
    @ApiModelProperty("组合渠道分配状态")
    private Integer status2b;
    @ApiModelProperty("组合渠道上架状态")
    private Integer putStatus2b;
    @ApiModelProperty("组合线上上架的平台名称")
    private String platformIds;
    private String putAwayStatus2c;
    private List<String> putStatus2cNames;
    private List<String> putStatusNames;
    @ApiModelProperty("组合创建时间")
    private Date createTime;
    @ApiModelProperty("组合分配时间")
    private Date allotTime;
    @ApiModelProperty("组合公开状态")
    private Integer secrecy;
    @ApiModelProperty("组合缩略图")
    private Integer picId;
    private String picPath;
    @ApiModelProperty("组合模型ID")
    private Integer modelId;
    private String modelCode;
}
