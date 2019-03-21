package com.sandu.api.resmodel.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
@Data

public class ModelAreaRel implements Serializable {
    /**
     * 区域ID
     */
    private Integer id;

    /**
     * 模型ID
     */
    private Integer modelId;

    /**
     * 区域编码
     */
    private String code;

    /**
     * 区域名称
     */
    private String name;

    private Byte isDeleted;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建人
     */
    private String creator;

    private static final long serialVersionUID = 1L;

    /**
     * 区域默认高度
     */
    private Integer height;
    /**
     * 区域默认宽度
     */
    private Integer width;

}