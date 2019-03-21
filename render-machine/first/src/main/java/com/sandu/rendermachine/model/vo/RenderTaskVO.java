package com.sandu.rendermachine.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 10:08 2018/4/18 0018
 * @Modified By:
 */
@Data
public class RenderTaskVO implements Serializable{

    private Integer maxSize;
    private String msgId;
    private Integer preTaskId;
    private String renderMachineIp;
    private Integer renderLevel;
    private String renderProgramVersion;

}
