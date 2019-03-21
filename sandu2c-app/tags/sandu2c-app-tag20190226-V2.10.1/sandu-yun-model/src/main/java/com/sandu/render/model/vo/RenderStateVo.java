package com.sandu.render.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RenderStateVo implements Serializable {
    private Integer state;

    private Integer businessId;

    private String vrUuid;
}
