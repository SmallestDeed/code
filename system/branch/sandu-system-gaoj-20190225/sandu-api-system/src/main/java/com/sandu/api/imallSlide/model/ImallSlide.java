package com.sandu.api.imallSlide.model;

import lombok.Data;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author djc (yocome@gmail.com)
 * @datetime 2018/4/26 15:48
 */
@Data
public class ImallSlide implements Serializable {


    /**
     * id
     */
    private int id;

    /**
     * 名称
     */
    private String fileName;

    /**
     * 排序
     */
    private int order;

    private int type;

    private String creator;

    private String sysCode;

}
