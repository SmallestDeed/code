package com.sandu.api.proprietorinfo.output;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerTypeVo implements Serializable {
    /**
     * 客户类型
     */
    private String customerType;
    /**
     * 客户类型(取字典类型customerType的value)
     */
    private Integer customerTypeValue;
}
