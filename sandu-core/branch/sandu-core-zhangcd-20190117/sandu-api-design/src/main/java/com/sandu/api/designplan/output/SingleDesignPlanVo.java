package com.sandu.api.designplan.output;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 1:56 2018/11/28 0028
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
 * @date 2018/11/28 0028PM 1:56
 */

@Data
public class SingleDesignPlanVo implements Serializable{
    private static final long serialVersionUID = 3556254637747649126L;

    private Integer designPlanId; //草图方案ID
    private String planName;//草图方案名称
    private String spaceAreas;//草图方案空间面积
    private String spaceName;//草图方案空间名称
    private Integer spaceFunctionId;//草图方案空间类型
    private String designPlanStyleName;//草图方案风格名称
    private String renderPicPath;//720渲染图片
    private String planCoverPicPath;//方案封面图
    private Date gmtCreateTime; //创建时间
    private Date gmtModifiedTime;//修改时间
    private Integer designTemplateId;

}
