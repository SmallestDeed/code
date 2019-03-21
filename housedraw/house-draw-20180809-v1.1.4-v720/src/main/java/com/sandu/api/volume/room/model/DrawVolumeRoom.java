package com.sandu.api.volume.room.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 量房数据表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-06-06 14:32:00.572
 */

@Data
public class DrawVolumeRoom implements Serializable {
    
    /**
     * draw_volume_room.id
     */
    private Long id;

    /**
     * 量房数据文件<p>
     * draw_volume_room.zip_file_id
     */
    private Long zipFileId;

    /**
     * draw_volume_room.lf_file_id
     */
    private Long lfFileId;

    /**
     * sdkj文件<p>
     * draw_volume_room.sdkj_file_id
     */
    private Long sdkjFileId;

    /**
     * 户型图<p/>
     * draw_volume_room.house_pic_id
     */
    private Long housePicId;

    /**
     * 户型名
     * draw_volume_room.house_name
     */
    private String houseName;

    /**
     *  状态：0、待解析(defaults)；1、解析中；2、解析成功；3、已完<p/>
     *  draw_volume_room.status
     */
    private Integer status;

    /**
     * 项目key,科创的唯一标识<p>
     * draw_volume_room.project_key
     */
    private String projectKey;

    /**
     * house_uuid<p>
     * draw_volume_room.house_uuid
     */
    private String houseUuid;

    /**
     * draw_base_house => id<p>
     * draw_volume_room.draw_house_id
     */
    private Long drawHouseId;

    /**
     * 省<p>
     * draw_volume_room.province
     */
    private String province;

    /**
     * 市<p>
     * draw_volume_room.city
     */
    private String city;

    /**
     * 区<p>
     * draw_volume_room.dist
     */
    private String dist;

    /**
     * 小区名
     * draw_volume_room.living_name
     */
    private String livingName;

    /**
     * 手机号码<p>
     * draw_volume_room.mobile
     */
    private String mobile;

    /**
     * draw_volume_room.is_deleted
     */
    private Short isDeleted;

    /**
     * draw_volume_room.creator
     */
    private String creator;

    /**
     * draw_volume_room.gmt_create
     */
    private Date gmtCreate;

    /**
     * draw_volume_room.modifier
     */
    private String modifier;

    /**
     * draw_volume_room.gmt_modified
     */
    private Date gmtModified;

    /**
     * 解析成功时间
     * draw_volume_room.gmt_parse
     */
    private Date gmtParse;

    /**
     * 完善成功时间
     * draw_volume_room.gmt_perfect
     */
    private Date gmtPerfect;

    private static final long serialVersionUID = 1L;
}