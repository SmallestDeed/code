package com.sandu.api.fullhouse.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 单空间方案组查询参数模型
 */
@Data
public class DesignPlanQuery implements Serializable {
    /**
     * 空间类型(1:客厅,2:餐厅,3:客餐厅,4:卧室,5:厨房,6:卫生间,7:书房,8:衣帽间)
     */
    @ApiModelProperty(value = "空间类型(1:客厅,2:餐厅,3:客餐厅,4:卧室,5:厨房,6:卫生间,7:书房,8:衣帽间)")
    @NotNull(message = "空间类型为空")
    private Integer spaceType;
    /**
     * 方案名称
     */
    @ApiModelProperty(value = "方案名称")
    @Length(max = 20, message = "方案名称超过了20个字")
    private String designPlanName;
    /**
     * 方案风格
     */
    @ApiModelProperty(value = "方案风格ID")
    private Integer designPlanStyleId;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页码参数为空")
    @Min(value = 1, message = "当前页码参数小于1")
    private Integer curPage;
    /**
     * 查询开始位置
     */
    @ApiModelProperty(value = "数据库查询开始位置(后端生成，不需要前端传入)")
    private Integer start;
    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数据条数")
    @NotNull(message = "每页数据条数参数为空")
    @Min(value = 1, message = "每页数据条数参数小于1")
    private Integer pageSize;
    /**
     * 设计师ID
     */
    @ApiModelProperty(value = "设计师ID(后端直接获取登录用户，不需要前端传入)")
    private Integer userId;
    /**
     * 当前全屋方案当前空间类型的已选的单空间方案ID集合
     */
    @ApiModelProperty(value = "当前全屋方案当前空间类型的已选的单空间方案ID集合")
    private List<Integer> selectedDesignPlanIds;
}
