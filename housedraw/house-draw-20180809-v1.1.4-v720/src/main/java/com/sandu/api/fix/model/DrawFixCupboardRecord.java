package com.sandu.api.fix.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-07-05 14:12:39.093
 */

@Setter
@Getter
@ToString
public class DrawFixCupboardRecord implements Serializable {
    
    /**
     * draw_fix_cupboard_record.id
     */
    private Long id;

    /**
     * draw_fix_cupboard_record.house_id
     */
    private Long houseId;

    /**
     * draw_fix_cupboard_record.status
     */
    private Byte status;

    /**
     * draw_fix_cupboard_record.is_deleted
     */
    private Byte isDeleted;

    /**
     * draw_fix_cupboard_record.creator
     */
    private String creator;

    /**
     * draw_fix_cupboard_record.gmt_create
     */
    private Date gmtCreate;

    /**
     * draw_fix_cupboard_record.modifier
     */
    private String modifier;

    /**
     * draw_fix_cupboard_record.gmt_modified
     */
    private Date gmtModified;

    /**
     * draw_fix_cupboard_record.message
     */
    private String message;

    private static final long serialVersionUID = 1L;
}