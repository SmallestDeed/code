package com.sandu.api.solution.output;

import com.sandu.api.solution.model.bo.DesignPlanEffectDiagramBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * DesignPlanEffectDiagramMergeBO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignPlanEffectDiagramMergeVO implements Serializable {

    @ApiModelProperty("图片")
    private List<DesignPlanEffectDiagramBO> pics;

    @ApiModelProperty("单点720图片")
    private List<DesignPlanEffectDiagramBO> single720Pic;

    @ApiModelProperty("多点720图片")
    private List<DesignPlanEffectDiagramBO> multi720Pic;

    @ApiModelProperty("视频图片")
    private List<DesignPlanEffectDiagramBO> videoPic;
}
