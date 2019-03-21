package com.sandu.product.model.input;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 4:04 2018/6/27 0027
 * @Modified By:
 */
@Data
public class PlanProductModel extends Mapper implements Serializable {

    private Integer recommendedId;

    private Integer renderSceneId;

    private Integer designPlanId;

    private Integer platformId;

}
