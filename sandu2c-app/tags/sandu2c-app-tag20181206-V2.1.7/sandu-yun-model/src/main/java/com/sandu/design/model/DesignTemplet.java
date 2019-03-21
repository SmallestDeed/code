package com.sandu.design.model;

import com.sandu.common.model.Mapper;
import com.sandu.designplan.model.DesignPlanRecommendedResult;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: DesignTemplet.java
 * @Package com.sandu.design.model
 * @Description:设计模块-设计方案样板房表
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 */
@Data
public class DesignTemplet extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 方案编码
     **/
    private String designCode;
    /**
     * 方案名称
     **/
    private String designName;
    /**
     * 用户id
     **/
    private Integer userId;
    /**
     * 方案来源类型
     **/
    private String designSourceType;
    /**
     * 设计风格id
     **/
    private Integer designStyleId;
    /**
     * 通用空间id
     **/
    private Integer spaceCommonId;

    private List<Integer> spaceCommonIds;

    private String spaceCode;
    /**
     * 样板图片id(空间设计图)
     **/
    private Integer picId;
    // 空间布局图缩略图id
    private Integer picSmallId;
    // 空间设计图
    private String designPicPath;
    /**
     * 配置文件路径id
     **/
    private Integer configFileId;
    private Integer eveningFileId;
    private Integer dawnFileId;
    private Integer nightFileId;
    private Integer putawayState;
    /**
     * 模型文件id
     **/
    private Integer modelId;
    /**
     * 3d模型id
     **/
    private Integer model3dId;
    /**
     * u3d模型id
     **/
    private Integer webModelU3dId;
    private Integer iosModelU3dId;
    private Integer androidModelU3dId;
    private Integer pcModelU3dId;
    private Integer ipadModelU3dId;
    private Integer macBookpcModelU3dId;
    private Integer newPcModelU3dId;
    /**
     * 渲染图片ids
     **/
    private String renderPicIds;
    /**
     * 是否为推荐空间布局
     **/
    private Integer isRecommend;
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
     * 备注
     **/
    private String remark;
    /**
     * cad图id
     **/
    private Integer cadPicId;
    /**
     * fbx文件ID
     **/
    private Integer fbxFileId;
    /**
     * fbx贴图多个
     **/
    private String fbxTexture;
    private Integer fbxState;
    // 风格名称
    private String designStyleName;
    // 通用空间名称
    private String spaceCommonName;
    // 图片路径(渲染效果图path)
    private String picPath;
    // 图片路径(渲染效果缩略图)
    private String smallpicPath;
    // 面积
    private String spaceAreas;
    // 空间功能类型
    private Integer spaceFunctionId;
    // 空间形状
    private String spaceShape;
    /**
     * 效果图列表
     **/
    private String effectPic;
    // 效果图缩略图id
    private Integer effecPicSmallId;
    /**
     * 效果图列表路径
     **/
    private String[] effectPicListPath;
    /**
     * 效果图列表缩略图，取列表第一个
     **/
    private String effectSmallPicPath;
    /* 平面效果图ids */
    private String effectPlanIds;
    /* 平面效果缩略图id */
    private Integer effectPlanSmallId;
    /* 平面效果图url */
    private String effectPlanUrl;
    /* 平面效果图对应的缩略图url */
    private String effectPlanSmallUrl;
    /**
     * 场景缩略图
     **/
    private Integer thumbnailId;
    /* 产品配置文件 */
    private Integer configId;
    /**
     * 空间的模型id
     */
    private String spaceCommonCode;
    private String iosU3dModelId;
    private String modelU3dId;
    private String macBookPcU3dModelId;
    private String windowsPcU3dModelId;
    private String ipadU3dModelId;
    private String androidU3dModelId;
    private Integer houseTypeValue;
    /**
     * 样板房适合的推荐方案，方案组合功能添加
     **/
    private DesignPlanRecommendedResult designPlanRecommended;
    
    private Double mainArea;
    
    /**
     * 该样板房匹配上的推荐方案id
     * add by huangsongbo 2018.10.12
     */
    private Integer matchRecommendedId;

    /**
     * 获取对象的copy
     **/
    public DesignTemplet copy() {
        DesignTemplet obj = new DesignTemplet();
        obj.setDesignCode(this.designCode);
        obj.setDesignName(this.designName);
        obj.setUserId(this.userId);
        obj.setDesignSourceType(this.designSourceType);
        obj.setDesignStyleId(this.designStyleId);
        obj.setSpaceCommonId(this.spaceCommonId);
        obj.setConfigFileId(this.configFileId);
        obj.setModelId(this.modelId);
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setRemark(this.remark);

        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map toMap() {
        Map map = new HashMap();
        map.put("designCode", this.designCode);
        map.put("designName", this.designName);
        map.put("userId", this.userId);
        map.put("designSourceType", this.designSourceType);
        map.put("designId", this.designStyleId);
        map.put("spaceCommonId", this.spaceCommonId);
        map.put("configFileId", this.configFileId);
        map.put("modelId", this.modelId);
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("remark", this.remark);

        return map;
    }

}
