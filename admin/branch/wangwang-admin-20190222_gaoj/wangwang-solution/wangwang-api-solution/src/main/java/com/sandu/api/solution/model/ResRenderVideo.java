package com.sandu.api.solution.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResRenderVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 设计方案
     **/
    private Integer businessId;
    /**
     * 视频编码
     **/
    private String videoCode;
    /**
     * 视频名称
     **/
    private String videoName;
    /**
     * 视频文件名称
     **/
    private String videoFileName;
    /**
     * 视频类型
     **/
    private String videoType;
    /**
     * 视频大小
     **/
    private Integer videoSize;
    /**
     * 视频后缀
     **/
    private String videoSuffix;
    /**
     * 视频级别
     **/
    private Integer videoLevel;
    /**
     * 视频格式
     **/
    private String videoFormat;
    /**
     * 视频路径
     **/
    private String videoPath;
    /**
     * 视频描述
     **/
    private String videoDesc;
    /**
     * 视频排序
     **/
    private Integer videoOrdering;
    /**
     * key标识
     **/
    private String fileKey;
    /**
     * key标识（多个）
     **/
    private String fileKeys;
    /**
     * 视频对应的封面图id信息
     **/
    private String smallVideoInfo;
    /**
     * 渲染类型
     **/
    private Integer renderingType;
    /**
     * 视频文件排序序号
     **/
    private Integer sequence;
    /**
     * 渲染任务创建时间
     **/
    private Date taskCreateTime;
    /**
     * 关联封面图Id
     **/
    private Integer sysTaskPicId;
    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 字符备用1
     **/
    private String att1;
    /**
     * 字符备用2
     **/
    private String att2;
    /**
     * 整数备用1
     **/
    private Integer numa1;
    /**
     * 整数备用2
     **/
    private Integer numa2;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 平台Id
     * add by chenm 20180509
     */
    private Integer platformId;
}
