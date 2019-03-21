package com.nork.system.model.vo;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by yanghz on 2017-08-09.
 */
public class SysDictionaryVo {
    //数据字典name
    private String name;
    //数据字典value
    private Integer value;
    /**  排序  **/
    private Integer ordering;

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
