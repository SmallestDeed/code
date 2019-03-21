package com.sandu.banner.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告-图片资源
 * @author WangHaiLin
 * @date 2018/5/14  13:50
 */
@Data
public class ResBannerPic implements Serializable {
    /**
     * 组件Id
     */
    private Integer id;
    /**
     * 图片编码
     */
    private String picCode;
    /**
     * 图片名称
     */
    private String picName;
    /**
     * 图片文件名称
     */
    private String picFileName;
    /**
     * 图片类型
     */
    private String picType;
    /**
     * 图片大小
     */
    private Integer picSize;
    /**
     * 图片长
     */
    private String picWeight;
    /**
     * 图片高
     */
    private String picHigh;
    /**
     * 图片后缀
     */
    private String suffix;
    /**
     * 图片级别
     */
    private String level;
    /**
     * 图片格式
     */
    private String picFormat;
    /**
     * 图片路径
     */
    private String picPath;
    /**
     * 图片描述
     */
    private String picDesc;
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
     * 备注
     */
    private String remark;


}
