package com.sandu.api.proprietorinfo.output;

import lombok.Data;

import java.io.Serializable;

@Data
public class DecorateBudgetVo implements Serializable {
    /**
     * 装修预算
     */
    private String decorateBudget;
    /**
     * 装修预算(取字典类型decorateBudget的value)
     */
    private Integer decorateBudgetValue;
}
