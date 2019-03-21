package com.sandu.api.house.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-05-23 15:16:21.019
 */

@Data
public class DesignTempletJumpPositionRel implements Serializable {
    
    /**
     * design_templet_jump_position_rel.id
     */
    private Long id;

    /**
     * 原点(所在的位置)样板房ID<p>
     * design_templet_jump_position_rel.origin_id
     */
    private Long originId;

    /**
     * 目的地样板房ID<p>
     * design_templet_jump_position_rel.target_id
     */
    private Long targetId;

    /**
     * 跳跃点的位置（坐标）<p>
     * design_templet_jump_position_rel.jump_position
     */
    private String jumpPosition;

    /**
     * 用于更新跳跃点位置，draw_design_templet_product的Id<p>
     * design_templet_jump_position_rel.draw_dtp_id
     */
    private Long drawDtpId;

    /**
     * design_templet_jump_position_rel.is_deleted
     */
    private Short isDeleted;

    /**
     * design_templet_jump_position_rel.creator
     */
    private String creator;

    /**
     * design_templet_jump_position_rel.gmt_create
     */
    private Date gmtCreate;

    /**
     * design_templet_jump_position_rel.modifier
     */
    private String modifier;

    /**
     * design_templet_jump_position_rel.gmt_modified
     */
    private Date gmtModified;

    /**
     * 备注<p>
     * design_templet_jump_position_rel.remark
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    /**
     * uniqueId
     */
    private String uniqueId;
}