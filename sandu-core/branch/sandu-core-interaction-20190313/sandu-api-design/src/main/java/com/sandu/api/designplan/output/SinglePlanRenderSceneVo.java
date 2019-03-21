package com.sandu.api.designplan.output;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:55 2018/11/27 0027
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/11/27 0027PM 3:55
 */
@Data
public class SinglePlanRenderSceneVo implements Serializable{
    private static final long serialVersionUID = 2241381590458403290L;
    private Integer sourceType;//方案来源类型(1:内部制作,2:装进我家,3:交付,4:分享,5:复制)'
    private Integer spaceType;//单空间效果图方案类型
    private String planName;//单空间效果图方案名称
    private String spaceName;//单空间效果图方案空间名称
    private String spaceAreas;//单空间效果图方案空间面积
    private Integer designPlanRenderSceneId;//单空间效果图方案ID
    private Integer planRenderResourceId;//效果图方案720ID
    private String planResourcPicPath;//单空间渲染封面图
    private Integer resRenderPicId;//单空间渲染资源ID
    private Date gmtCreateTime;//创建时间
    private Date gmtModifiedTime; //修改时间
    private String designPlanStyleName; //风格名称
    private Integer designTemplateId;
    private Integer houseId;
    private Integer livingId;
    private Integer designPlanStyleId; //风格ID


}
