package com.sandu.api.banner.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MiniProIndexDialog implements Serializable {

    private Long id;

    /**
     * 弹窗名称
     */
    private String dialogName;

    /**
     * 弹窗跳转链接
     */
    private String url;

    /**
     * 弹窗资源图片id
     */
    private String dialogImageResPicId;

    /**
     * 弹窗是否开启
     */
    private Integer isEnable;

    /**
     * 弹窗所属平台编码
     */
    private String dialogCode;

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


    /**
     * 弹窗图片路径
     */
    private String picPath;
}
