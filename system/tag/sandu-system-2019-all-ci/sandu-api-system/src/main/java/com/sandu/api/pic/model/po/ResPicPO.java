package com.sandu.api.pic.model.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片列表PO对象
 *
 * @auth xiaoxc
 * @data 2018-06-19
 */
@Data
public class ResPicPO implements Serializable {

    //资源ID
    private Long resId;
    //资源地址
    private String resPath;
}
