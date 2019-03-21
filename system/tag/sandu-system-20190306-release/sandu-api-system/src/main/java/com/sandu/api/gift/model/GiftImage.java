package com.sandu.api.gift.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author djc (yocome@gmail.com)
 * @datetime 2018/4/26 15:48
 */
@Data
public class GiftImage implements Serializable {
    /**
     * id
     */
    private int id;

    /**
     * giftId
     */
    private int giftId;

    /**
     * 图片文件名
     */
    private String fileName;

    /**
     * 排序
     */
    private int order;

    /**
     * 0不是封面1封面
     */
    private int cover;

    /**
     * 0默认1缩略图
     */
    private int small;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除
     */
    private int isDeleted;
}
