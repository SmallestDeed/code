package com.sandu.api.fullhouse.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改装进我家生成的全屋方案的数据模型
 */
@Data
public class FullHouseDesignPlanSceneUpdate implements Serializable {
    /**
     * 全屋方案ID
     */
    @ApiModelProperty("全屋方案ID")
    @NotNull(message = "全屋方案ID为空")
    @Min(value = 1, message = "非法的全屋方案ID")
    private Integer fullHouseId;
    /**
     * 源方案ID
     */
    @ApiModelProperty("源方案ID")
    @NotNull(message = "源方案ID为空")
    @Min(value = 0, message = "非法的源方案ID")
    private Integer sourcePlanId;
    /**
     * 新方案ID
     */
    @ApiModelProperty("新方案ID")
    @NotNull(message = "新方案ID为空")
    @Min(value = 0, message = "非法的新方案ID")
    private Integer newPlanId;
    /**
     * 空间类型ID
     */
    @ApiModelProperty("空间类型ID")
    @NotNull(message = "空间类型ID为空")
    @Min(value = 1, message = "非法的空间类型ID")
    private Integer spaceFunctionId;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID为空")
    @Min(value = 1, message = "非法的用户ID")
    private Integer userId;
    /**
     * 户型ID
     */
    @ApiModelProperty("户型ID")
    private Integer houseId;
}
