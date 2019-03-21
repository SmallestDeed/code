package com.sandu.api.base.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangHaiLin
 * @date 2018/5/25  18:09
 */
@Data
public class SystemModel implements Serializable {
    /**
     * 系统编码
     */
    private String sysCode;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改者
     */
    private String modifier;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 是否删除
     */
    private Integer isDeleted;
}
