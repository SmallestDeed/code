package com.sandu.api.product.output;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class DesignPlanStyleVO implements Serializable {
    Long styleId;
    String styleName;
    String groupId;

    List<DesignPlanStyleVO> styles;
}
