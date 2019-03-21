package com.sandu.api.dictionary.input;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class SysDictionaryQuery implements Serializable {
    private static final long serialVersionUID = 8798943482344210255L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 类型
     */
    private String type;

    /**
     * 唯一标示
     */
    private String valuekey;

    /**
     * 值
     */
    private Integer value;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer ordering;

    /**
     * 用户类型
     */
    private String att1;


}
