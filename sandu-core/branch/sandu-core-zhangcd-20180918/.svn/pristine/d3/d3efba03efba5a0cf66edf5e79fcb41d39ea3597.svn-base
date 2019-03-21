package com.sandu.api.fullhouse.input;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 制作全屋方案数据模型
 */
@Data
public class FullHouseDesignPlanAdd implements Serializable {
    /**
     * 全屋方案名称
     */
    @ApiModelProperty("全屋方案名称")
    @NotBlank(message = "全屋方案名称为空")
    @Length(max = 20, message = "全屋方案名称不能超过20个字")
    private String designPlanName;
    /**
     * 方案风格ID
     */
    @ApiModelProperty("方案风格ID")
    @NotNull(message = "方案风格为空")
    @Min(value = 0, message = "方案风格不合法")
    private Integer designPlanStyleId;
    /**
     * 客餐厅方案ID集合
     */
    @ApiModelProperty("客餐厅方案ID集合")
    @NotEmpty(message = "客餐厅方案不能为空")
    @Size(max = 1, message = "最多只能选择1个客餐厅方案")
    private List<Integer> livingDiningRoom;
    /**
     * 卧室方案ID集合
     */
    @ApiModelProperty("卧室方案ID集合")
    @NotEmpty(message = "卧室方案不能为空")
    @Size(max = 3, message = "最多只能选择3个卧室方案")
    private List<Integer> bedroom;
    /**
     * 厨房方案ID集合
     */
    @ApiModelProperty("厨房方案ID集合")
    @NotEmpty(message = "厨房方案不能为空")
    @Size(max = 1, message = "最多只能选择1个厨房方案")
    private List<Integer> kitchen;
    /**
     * 卫生间方案ID集合
     */
    @ApiModelProperty("卫生间方案ID集合")
    @NotEmpty(message = "卫生间方案不能为空")
    @Size(max = 2, message = "最多只能选择2个卫生间方案")
    private List<Integer> toilet;
    /**
     * 书房方案ID集合
     */
    @ApiModelProperty("书房方案ID集合")
    @NotEmpty(message = "书房方案不能为空")
    @Size(max = 2, message = "最多只能选择2个书房方案")
    private List<Integer> schoolroom;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID,高俊调rest服务时用")
    private Integer userId;
    /**
     * 来源方案ID
     */
    @ApiModelProperty("来源全屋方案ID")
    private Integer fullHousePlanSourceId;
}
