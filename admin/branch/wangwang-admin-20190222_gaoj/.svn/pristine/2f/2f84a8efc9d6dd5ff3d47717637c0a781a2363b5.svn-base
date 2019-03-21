package com.sandu.api.resmodel.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
@Data

public class ModelAreaTextureRel implements Serializable {
    private Integer id;
    /**
     * 区域编码
     */
    @ApiModelProperty(value = "贴图区域编码")
    private String areaCode;

    @ApiModelProperty(value = "贴图区域ID")
    private Integer areaId;

    /**
     * 贴图ID
     */
    @ApiModelProperty(value = "贴图ID")
    private Integer textureId;

    /**
     * 材质对价格的影响值
     */
    @ApiModelProperty(value = "影响价格")
    private Integer affectPrice;

    /**
     * 是否为此区域默认贴图(1:是/0:否)
     */
    @ApiModelProperty(value = "是否为默认贴图:1是,0否")
    private Byte isDefault;

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

}