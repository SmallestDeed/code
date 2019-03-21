package com.sandu.api.storage.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResFile implements Serializable {

    private Integer id;
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
     * 文件编码
     **/
    private String fileCode;
    /**
     * 文件名称
     **/
    private String fileName;
    /**
     * 文件原名称
     **/
    private String fileOriginalName;
    /**
     * 文件类型
     **/
    private String fileType;
    /**
     * 文件大小
     **/
    private String fileSize;
    /**
     * 文件后缀
     **/
    private String fileSuffix;
    /**
     * 文件级别
     **/
    private String fileLevel;
    /**
     * 文件路径
     **/
    private String filePath;
    /**
     * 文件描述
     **/
    private String fileDesc;
    /**
     * 文件排序
     **/
    private Integer fileOrdering;
    /**
     * key标识（多个）
     **/
    private String fileKeys;
    /**
     * 业务IDS
     **/
    private String businessIds;
    /**
     * 字符备用4
     **/
    private String att4;
    /**
     * 字符备用5
     **/
    private String att5;
    /**
     * 字符备用6
     **/
    private String att6;
    /**
     * 时间备用1
     **/
    private Date dateAtt1;
    /**
     * 时间备用2
     **/
    private Date dateAtt2;

    /**
     * 整数备用2
     **/
    private Integer numAtt2;
    /**
     * 数字备用1
     **/
    private Double numAtt3;
    /**
     * 归档状态
     **/
    private Double numAtt4;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 业务ID
     */
    private Integer businessId;

    /**
     * 业务标识
     */
    private String fileKey;

}
