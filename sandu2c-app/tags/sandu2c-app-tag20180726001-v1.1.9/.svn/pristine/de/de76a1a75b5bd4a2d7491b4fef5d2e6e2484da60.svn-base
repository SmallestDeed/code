package com.sandu.analyze.entity.elasticseatch.dto;

import lombok.Data;
import org.elasticsearch.search.sort.SortMode;
import org.elasticsearch.search.sort.SortOrder;

import java.io.Serializable;

/**
 * 排序对象
 *
 * @date 2018/4/18
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Data
public class SortOrderObject implements Serializable {

    private static final long serialVersionUID = 2660091976498568656L;
    //字段名
    private String fieldName;
    //排序类型
    private SortOrder sortOrder;
    //排序模式
    private SortMode sortMode;

    public SortOrderObject(String fieldName, SortOrder sortOrder, SortMode sortMode) {
        this.fieldName = fieldName;
        this.sortOrder = sortOrder;
        this.sortMode = sortMode;
    }
}
