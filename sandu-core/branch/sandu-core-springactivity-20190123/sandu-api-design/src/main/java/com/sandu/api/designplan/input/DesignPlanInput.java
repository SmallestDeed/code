package com.sandu.api.designplan.input;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 4:06 2018/11/28 0028
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
 * @date 2018/11/28 0028PM 4:06
 */
@Data
public class DesignPlanInput implements Serializable{
    private static final long serialVersionUID = -8894833973610152647L;

    private Integer planId;

    private Integer designTemplateId;

    private String msgId;

    private Integer planType;//3:单空间我的设计方案,4:全屋我的设计方案

}
