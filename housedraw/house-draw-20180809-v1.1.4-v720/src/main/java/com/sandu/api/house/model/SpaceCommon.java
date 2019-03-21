package com.sandu.api.house.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 通用空间表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-02-28 11:20:19.464
 */

@Data
public class SpaceCommon implements Serializable {
    
    /**
     * space_common.id
     */
    private Long id;

    /**
     * 系统编码<p>
     * space_common.sys_code
     */
    private String sysCode;

    /**
     * 通用房型编码<p>
     * space_common.space_code
     */
    private String spaceCode;

    /**
     * 通用房型名称<p>
     * space_common.space_name
     */
    private String spaceName;

    /**
     * 空间功能类型<p>
     * space_common.space_function_id
     */
    private Integer spaceFunctionId;

    /**
     * 主体长度<p>
     * space_common.main_length
     */
    private String mainLength;

    /**
     * 主体宽度<p>
     * space_common.main_width
     */
    private String mainWidth;

    /**
     * 空间面积<p>
     * space_common.space_areas
     */
    private String spaceAreas;

    /**
     * 空间形状<p>
     * space_common.space_shape
     */
    private String spaceShape;

    /**
     * 门的位置类型<p>
     * space_common.door_location_type
     */
    private String doorLocationType;

    /**
     * 门的位置id<p>
     * space_common.door_location_id
     */
    private Integer doorLocationId;

    /**
     * 缩略图id<p>
     * space_common.pic_id
     */
    private Integer picId;

    /**
     * 模型id<p>
     * space_common.model_id
     */
    private Integer modelId;

    /**
     * 空间描述<p>
     * space_common.space_desc
     */
    private String spaceDesc;

    /**
     * 位置数组<p>
     * space_common.location_arrays
     */
    private String locationArrays;

    /**
     * 是否包含过道<p>
     * space_common.is_include_way
     */
    private Integer isIncludeWay;

    /**
     * 过道位置<p>
     * space_common.way_location
     */
    private String wayLocation;

    /**
     * 使用次数<p>
     * space_common.user_num
     */
    private Integer userNum;

    /**
     * 搜索次数<p>
     * space_common.search_num
     */
    private Integer searchNum;

    /**
     * 空间计数<p>
     * space_common.space_num
     */
    private Integer spaceNum;

    /**
     * 占总空间的比例<p>
     * space_common.space_percent
     */
    private String spacePercent;

    /**
     * 来源id<p>
     * space_common.datasource_id
     */
    private Integer datasourceId;

    /**
     * 状态<p>
     * space_common.status
     */
    private Integer status;

    /**
     * 创建者<p>
     * space_common.creator
     */
    private String creator;

    /**
     * space_common.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * space_common.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * space_common.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * space_common.is_deleted
     */
    private Integer isDeleted;

    /**
     * space_common.model_3d_id
     */
    private String model3dId;

    /**
     * space_common.model_u3d_id
     */
    private String modelU3dId;

    /**
     * 字符备用1<p>
     * space_common.att1
     */
    private String att1;

    /**
     * 字符备用2<p>
     * space_common.att2
     */
    private String att2;

    /**
     * space_common.view3d_pic_id
     */
    private String view3dPicId;

    /**
     * 字符备用4<p>
     * space_common.att4
     */
    private String att4;

    /**
     * 字符备用5<p>
     * space_common.att5
     */
    private String att5;

    /**
     * 字符备用6<p>
     * space_common.att6
     */
    private String att6;

    /**
     * 发布操作修改时间<p>
     * space_common.publish_modified
     */
    private Date publishModified;

    /**
     * 时间备用2<p>
     * space_common.date_att2
     */
    private Date dateAtt2;

    /**
     * 风格id<p>
     * space_common.style_id
     */
    private Integer styleId;

    /**
     * space_common.cad_pic_id
     */
    private Integer cadPicId;

    /**
     * 数字备用1<p>
     * space_common.num_att3
     */
    private BigDecimal numAtt3;

    /**
     * 数字备用2<p>
     * space_common.num_att4
     */
    private BigDecimal numAtt4;

    /**
     * 备注<p>
     * space_common.remark
     */
    private String remark;

    /**
     * space_common.ipad_u3d_model_id
     */
    private String ipadU3dModelId;

    /**
     * space_common.ios_u3d_model_id
     */
    private String iosU3dModelId;

    /**
     * space_common.android_u3d_model_id
     */
    private String androidU3dModelId;

    /**
     * space_common.macBookPc_u3d_model_id
     */
    private String macbookpcU3dModelId;

    /**
     * space_common.windowsPc_u3d_model_id
     */
    private String windowspcU3dModelId;

    /**
     * space_common.total_usage_amount
     */
    private Integer totalUsageAmount;

    /**
     * space_common.pid
     */
    private Integer pid;

    /**
     * space_common.is_standard_space
     */
    private Integer isStandardSpace;

    /**
     * space_common.web_u3d_mode_id
     */
    private String webU3dModeId;

    /**
     * space_common.cad_address
     */
    private String cadAddress;

    /**
     * space_common.window_location_value
     */
    private Integer windowLocationValue;

    /**
     * space_common.view_plan_ids
     */
    private String viewPlanIds;

    /**
     * space_common.sync_status
     */
    private String syncStatus;

    /**
     * 场景模型文件（阳台等）<p>
     * space_common.scene3d_id
     */
    private Integer scene3dId;

    /**
     * 空间形状图片id<p>
     * space_common.space_shape_pic_id
     */
    private Integer spaceShapePicId;

    /**
     * 是否公开：1：公开、2：未公开<p>
     * space_common.open_status
     */
    private Integer openStatus;

    /**
     * 灯光白天u3d模型<p>
     * space_common.daylight_u3d_model_id
     */
    private Integer daylightU3dModelId;

    /**
     * 灯光黄昏u3d模型<p>
     * space_common.dusklight_u3d_model_id
     */
    private Integer dusklightU3dModelId;

    /**
     * 灯光黑夜u3d模型<p>
     * space_common.nightlight_u3d_model_id
     */
    private Integer nightlightU3dModelId;

    /**
     * 户型分析编码(空间)<p>
     * space_common.space_analyze_code
     */
    private String spaceAnalyzeCode;

    /**
     * 新U3D编码<p>
     * space_common.space_u3d_code
     */
    private String spaceU3dCode;

    /**
     * 空间长度<p>
     * space_common.space_main_length
     */
    private String spaceMainLength;

    /**
     * 空间宽度<p>
     * space_common.space_main_width
     */
    private String spaceMainWidth;

    /**
     * space_common.daylight_android_u3d_model_id
     */
    private Integer daylightAndroidU3dModelId;

    /**
     * space_common.dusklight_android_u3d_model_id
     */
    private Integer dusklightAndroidU3dModelId;

    /**
     * space_common.nightlight_android_u3d_model_id
     */
    private Integer nightlightAndroidU3dModelId;

    /**
     * space_common.daylight_ios_u3d_model_id
     */
    private Integer daylightIosU3dModelId;

    /**
     * space_common.dusklight_ios_u3d_model_id
     */
    private Integer dusklightIosU3dModelId;

    /**
     * space_common.nightlight_ios_u3d_model_id
     */
    private Integer nightlightIosU3dModelId;

    /**
     * 关联的draw_space_common=>id<p>
     * space_common.rel_draw_id
     */
    private Long relDrawId;

    /**
     * 修复类型(背锅类型)；0、非修复数据（默认），1、修复橱柜
     */
    private Integer fixType;

    /**
     * 主面积（空间面积）是实际房间面积减去玄关、过道等区域的面积，用户方案搜索优化
     */
    private String mainArea;

    /**
     * 实际房间面积是户型绘制出来时，整个房间的面积，用于计算造价
     */
    private String realArea;

    private static final long serialVersionUID = 1L;
}