package com.sandu.api.task.refresh.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 2:39 2018/7/7 0007
 * @Modified By:
 */
@Data
public class JumpPosition implements Serializable {

    private Position position;
    private Position rotation;

}
