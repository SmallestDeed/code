package com.sandu.api.res.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-02-07 20:48:38.359
 */

@Data
public class ResHousePic implements Serializable {
    
    /**
     * res_house_pic.id
     */
    private Long id;

    /**
     * 图片编码<p>
     * res_house_pic.pic_code
     */
    private String picCode;

    /**
     * 图片名称<p>
     * res_house_pic.pic_name
     */
    private String picName;

    /**
     * 图片文件名称<p>
     * res_house_pic.pic_file_name
     */
    private String picFileName;

    /**
     * 图片类型<p>
     * res_house_pic.pic_type
     */
    private String picType;

    /**
     * 图片大小<p>
     * res_house_pic.pic_size
     */
    private Integer picSize;

    /**
     * 图片长<p>
     * res_house_pic.pic_weight
     */
    private String picWeight;

    /**
     * 图片高<p>
     * res_house_pic.pic_high
     */
    private String picHigh;

    /**
     * 图片后缀<p>
     * res_house_pic.pic_suffix
     */
    private String picSuffix;

    /**
     * 图片级别<p>
     * res_house_pic.pic_level
     */
    private String picLevel;

    /**
     * 图片格式<p>
     * res_house_pic.pic_format
     */
    private String picFormat;

    /**
     * 图片路径<p>
     * res_house_pic.pic_path
     */
    private String picPath;

    /**
     * 图片描述<p>
     * res_house_pic.pic_desc
     */
    private String picDesc;

    /**
     * 图片排序<p>
     * res_house_pic.pic_ordering
     */
    private String picOrdering;

    /**
     * 系统编码<p>
     * res_house_pic.sys_code
     */
    private String sysCode;

    /**
     * 创建者<p>
     * res_house_pic.creator
     */
    private String creator;

    /**
     * res_house_pic.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * res_house_pic.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * res_house_pic.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * res_house_pic.is_deleted
     */
    private Integer isDeleted;

    /**
     * 字符备用1<p>
     * res_house_pic.file_key
     */
    private String fileKey;

    /**
     * res_house_pic.file_keys
     */
    private String fileKeys;

    /**
     * res_house_pic.business_ids
     */
    private String businessIds;

    /**
     * res_house_pic.small_pic_info
     */
    private String smallPicInfo;

    /**
     * res_house_pic.view_point
     */
    private Integer viewPoint;

    /**
     * res_house_pic.scene
     */
    private Integer scene;

    /**
     * 时间备用1<p>
     * res_house_pic.date_att1
     */
    private Date dateAtt1;

    /**
     * 时间备用2<p>
     * res_house_pic.date_att2
     */
    private Date dateAtt2;

    /**
     * 整数备用1<p>
     * res_house_pic.business_id
     */
    private Integer businessId;

    /**
     * 整数备用2<p>
     * res_house_pic.num_att2
     */
    private Integer numAtt2;

    /**
     * 数字备用1<p>
     * res_house_pic.num_att3
     */
    private BigDecimal numAtt3;

    /**
     * 数字备用2<p>
     * res_house_pic.num_att4
     */
    private BigDecimal numAtt4;

    /**
     * 备注<p>
     * res_house_pic.remark
     */
    private String remark;

    /**
     * res_house_pic.sequence
     */
    private Integer sequence;

    /**
     * res_house_pic.att1
     */
    private String att1;

    /**
     * res_house_pic.att2
     */
    private String att2;

    /**
     * res_house_pic.numa1
     */
    private Integer numa1;

    /**
     * res_house_pic.numa2
     */
    private Integer numa2;

    /**
     * 缩略图文件路径<p>
     * res_house_pic.thumbnail_path
     */
    private String thumbnailPath;

    /**
     * 大缩略图文件路径<p>
     * res_house_pic.large_thumbnail_path
     */
    private String largeThumbnailPath;

    private static final long serialVersionUID = 1L;
}