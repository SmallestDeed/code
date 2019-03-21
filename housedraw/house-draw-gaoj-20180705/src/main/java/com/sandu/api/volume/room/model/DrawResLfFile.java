package com.sandu.api.volume.room.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 户型绘制-文件资源库
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-06-06 13:36:09.016
 */

@Data
public class DrawResLfFile implements Serializable {
    
    /**
     * draw_res_lf_file.id
     */
    private Long id;

    /**
     * 对应生成的模型文件id<p>
     * draw_res_lf_file.ref_id
     */
    private String refId;

    /**
     * 文件编码<p>
     * draw_res_lf_file.file_code
     */
    private String fileCode;

    /**
     * 文件名称<p>
     * draw_res_lf_file.file_name
     */
    private String fileName;

    /**
     * 文件原名称<p>
     * draw_res_lf_file.file_original_name
     */
    private String fileOriginalName;

    /**
     * 文件类型<p>
     * draw_res_lf_file.file_type
     */
    private String fileType;

    /**
     * 文件大小<p>
     * draw_res_lf_file.file_size
     */
    private String fileSize;

    /**
     * 文件后缀<p>
     * draw_res_lf_file.file_suffix
     */
    private String fileSuffix;

    /**
     * 文件级别<p>
     * draw_res_lf_file.file_level
     */
    private String fileLevel;

    /**
     * 文件路径<p>
     * draw_res_lf_file.file_path
     */
    private String filePath;

    /**
     * 文件描述<p>
     * draw_res_lf_file.file_desc
     */
    private String fileDesc;

    /**
     * 文件排序<p>
     * draw_res_lf_file.file_ordering
     */
    private Integer fileOrdering;

    /**
     * 系统编码<p>
     * draw_res_lf_file.sys_code
     */
    private String sysCode;

    /**
     * 创建者<p>
     * draw_res_lf_file.creator
     */
    private String creator;

    /**
     * draw_res_lf_file.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * draw_res_lf_file.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * draw_res_lf_file.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * draw_res_lf_file.is_deleted
     */
    private Integer isDeleted;

    /**
     * 字符备用1<p>
     * draw_res_lf_file.file_key
     */
    private String fileKey;

    /**
     * draw_res_lf_file.file_keys
     */
    private String fileKeys;

    /**
     * draw_res_lf_file.business_ids
     */
    private String businessIds;

    /**
     * 字符备用4<p>
     * draw_res_lf_file.att4
     */
    private String att4;

    /**
     * 字符备用5<p>
     * draw_res_lf_file.att5
     */
    private String att5;

    /**
     * 字符备用6<p>
     * draw_res_lf_file.att6
     */
    private String att6;

    /**
     * 时间备用1<p>
     * draw_res_lf_file.date_att1
     */
    private Date dateAtt1;

    /**
     * 时间备用2<p>
     * draw_res_lf_file.date_att2
     */
    private Date dateAtt2;

    /**
     * 整数备用1<p>
     * draw_res_lf_file.business_id
     */
    private Integer businessId;

    /**
     * 整数备用2<p>
     * draw_res_lf_file.num_att2
     */
    private Integer numAtt2;

    /**
     * 数字备用1<p>
     * draw_res_lf_file.num_att3
     */
    private BigDecimal numAtt3;

    /**
     * 数字备用2<p>
     * draw_res_lf_file.num_att4
     */
    private BigDecimal numAtt4;

    /**
     * 备注<p>
     * draw_res_lf_file.remark
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}