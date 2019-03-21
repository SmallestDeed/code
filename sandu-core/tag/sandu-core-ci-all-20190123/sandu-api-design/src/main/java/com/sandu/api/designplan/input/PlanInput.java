package com.sandu.api.designplan.input;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 4:57 2018/11/28 0028
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/11/28 0028PM 4:57
 */
@Data
public class PlanInput implements Serializable{
    private static final long serialVersionUID = -1757986045148997966L;
    private Integer designPlanId; //草图方案ID
    private String designPlanName; //草图方案名称
    private String designPlanDesc; //草图方案描述
    private Integer designStyleId; //草图方案风格
    private String renderPics; //渲染
}
