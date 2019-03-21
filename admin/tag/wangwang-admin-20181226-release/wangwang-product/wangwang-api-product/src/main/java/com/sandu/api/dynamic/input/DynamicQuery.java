package com.sandu.api.dynamic.input;

import com.sandu.common.BaseQuery;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 15:39 2018/10/24
 */

@Data
public class DynamicQuery extends BaseQuery implements Serializable {

    private Integer type;

    private String dynamicName;
}
