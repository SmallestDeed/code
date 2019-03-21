package com.sandu.panorama.model;

import com.sandu.common.model.BaseModel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/***
 * 720打组制作详情表
 */
@Data
public class DesignPlanStoreReleaseDetails extends BaseModel implements Serializable {

	private static final long serialVersionUID = -8471415151618022919L;
	
	/** 720打组主表ID **/
    private Integer storeReleaseId;
    /** 样板房ID **/
    private Integer designTemplateId;
    /** 方案ID **/
    private Integer designPlanId;
    /**推荐方案id**/
    private Integer recommendDesignPlanId;
    /** 方案类型 **/
    private Integer designPlanType;
    /** 渲染类型（720、多点、图片、视频） **/
    private Integer renderingType;
    /** 资源ID **/
    private Integer resourceId;
    /** 是否为主资源(0:不是;1:是).打开720最先显示的资源 **/
    private Integer isMain;

    /**
     * 资源类型 1:720、2:多点、3:图片、4:视频
     */
    public class RenderingType{
        /** 普通720 **/
        public static final int PANORAMA_RENDER = 1;
        /** 多点720 **/
        public static final int ROAM_RENDER = 2;
        /** 图片 **/
        public static final int PIC = 3;
        /** 视频 **/
        public static final int VIDEO = 4;
    }

    /**
     * 方案类型
     */
    public class DesignPlanType{
        /** 草图方案 **/
        public static final int NORMAL = 1;
        /** 效果图方案 **/
        public static final int RENDER_SCENE = 2;
    }

    /**
     * 是否为主资源(0:不是;1:是)
     */
    public class IsMain{
        public static final int YES = 1;
        public static final int NO = 1;
    }

    @Override
    public String toString(){
        return "DesignPlanStoreReleaseDetails{" +
                "id : " + this.getId() +
                ",storeReleaseId : " + storeReleaseId +
                ",designPlanId : " + designPlanId +
                ",recommendDesignPlanId : " + (recommendDesignPlanId != null?recommendDesignPlanId : "-1") +
                ",designPlanType : " + designPlanType +
                ",renderingType : " + renderingType +
                ",resourceId : " + resourceId +
                "}";
    }
}


