package com.sandu.api.designTemplet.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 设计方案样板房表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-08-17 11:17:19.218
 */

@Data
public class DesignTemplet implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * design_templet.id
     */
    private Long id;

    /**
     * 方案编码<p>
     * design_templet.design_code
     */
    private String designCode;

    /**
     * 方案名称<p>
     * design_templet.design_name
     */
    private String designName;

    /**
     * 用户id<p>
     * design_templet.user_id
     */
    private Integer userId;

    /**
     * 方案来源类型<p>
     * design_templet.design_source_type
     */
    private String designSourceType;

    /**
     * 设计风格id<p>
     * design_templet.design_style_id
     */
    private Integer designStyleId;

    /**
     * 样板图片<p>
     * design_templet.pic_id
     */
    private Integer picId;

    /**
     * 配置文件id<p>
     * design_templet.config_file_id
     */
    private Integer configFileId;

    /**
     * 模型文件id<p>
     * design_templet.model_id
     */
    private Integer modelId;

    /**
     * design_templet.model_3d_id
     */
    private Integer model3dId;

    /**
     * design_templet.model_u3d_id
     */
    private Integer modelU3dId;

    /**
     * 通用空间id<p>
     * design_templet.space_common_id
     */
    private Integer spaceCommonId;

    /**
     * 是否为推荐空间布局<p>
     * design_templet.is_recommend
     */
    private Integer isRecommend;

    /**
     * design_templet.sys_code
     */
    private String sysCode;

    /**
     * 创建者<p>
     * design_templet.creator
     */
    private String creator;

    /**
     * design_templet.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * design_templet.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * design_templet.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * design_templet.is_deleted
     */
    private Integer isDeleted;

    /**
     * 备注<p>
     * design_templet.remark
     */
    private String remark;

    /**
     * design_templet.render_pic_ids
     */
    private String renderPicIds;

    /**
     * design_templet.effect_pic
     */
    private String effectPic;

    /**
     * design_templet.ipad_model_u3d_id
     */
    private Integer ipadModelU3dId;

    /**
     * design_templet.ios_model_u3d_id
     */
    private Integer iosModelU3dId;

    /**
     * design_templet.android_model_u3d_id
     */
    private Integer androidModelU3dId;

    /**
     * design_templet.macBookpc_model_u3d_id
     */
    private Integer macbookpcModelU3dId;

    /**
     * design_templet.pc_model_u3d_id
     */
    private Integer pcModelU3dId;

    /**
     * design_templet.web_model_u3d_id
     */
    private Integer webModelU3dId;

    /**
     * design_templet.evening_file_id
     */
    private Integer eveningFileId;

    /**
     * design_templet.dawn_file_id
     */
    private Integer dawnFileId;

    /**
     * design_templet.night_file_id
     */
    private Integer nightFileId;

    /**
     * design_templet.putaway_state
     */
    private Integer putawayState;

    /**
     * design_templet.cad_pic_id
     */
    private Integer cadPicId;

    /**
     * design_templet.effect_plan_ids
     */
    private String effectPlanIds;

    /**
     * design_templet.sync_status
     */
    private String syncStatus;

    /**
     * 上架操作修改时间<p>
     * design_templet.putaway_modified
     */
    private Date putawayModified;

    /**
     * 测试操作修改时间<p>
     * design_templet.testing_modified
     */
    private Date testingModified;

    /**
     * FBX贴图<p>
     * design_templet.fbx_texture
     */
    private String fbxTexture;

    /**
     * FBX配置文件ID<p>
     * design_templet.fbx_file_id
     */
    private Integer fbxFileId;

    /**
     * FBX处理状态 0未处理 1已处理<p>
     * design_templet.fbx_state
     */
    private Integer fbxState;

    /**
     * 样板房配置文件<p>
     * design_templet.config_id
     */
    private Integer configId;

    /**
     * 第一次上架时间<p>
     * design_templet.gmt_first_putaway
     */
    private Date gmtFirstPutaway;

    /**
     * 第一次设置为测试状态操作时间<p>
     * design_templet.gmt_first_testing
     */
    private Date gmtFirstTesting;

    /**
     * 天花布局标识<p>
     * design_templet.smallpox_identify
     */
    private Integer smallpoxIdentify;

    /**
     * 地面布局标识<p>
     * design_templet.ground_identify
     */
    private Integer groundIdentify;

    /**
     * 布局标识<p>
     * design_templet.smallpox_identify_str
     */
    private String smallpoxIdentifyStr;

    /**
     * 布局标识<p>
     * design_templet.ground_identify_str
     */
    private String groundIdentifyStr;

    /**
     * 空间布局类型<p>
     * design_templet.space_layout_type
     */
    private String spaceLayoutType;

    /**
     * 普通方案商家后台上架 TEMPLATE 模板方案<p>
     * design_templet.shelf_status
     */
    private String shelfStatus;

    /**
     * 是否包含公开产品<p>
     * design_templet.contains_not_open_product
     */
    private String containsNotOpenProduct;

    /**
     * 0、户型绘制员绘制的平台数据；1、用户个人数据<p>
     * design_templet.data_type
     */
    private Short dataType;

    /**
     * 数据来源：0、后台添加；1、绘制工具绘制<p>
     * design_templet.origin
     */
    private Short origin;

    /**
     * 是否有天花区域；0、否；1是<p>
     * design_templet.is_regional_ceiling
     */
    private Short isRegionalCeiling;

    /**
     * 修复类型；0、非修复数据（默认），1、修复橱柜<p>
     * design_templet.fix_type
     */
    private Byte fixType;

    // db以外的字段 ->start
    
    /**
     * 空间类型value(ref of sys_dictionary)
     */
    private Integer spaceFunctionId;
    
    /**
     * 实际面积(去除玄关,过道的面积)
     */
    private Double mainArea;
    
    /**
     * 总面积value(ref of sys_dictionary)
     */
    private Integer spaceAreas;
    
    /**
     * 对应空间编码
     */
    private String spaceCode;
    
    // db以外的字段 ->end
    
}