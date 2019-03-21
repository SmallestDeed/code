package com.sandu.api.designplan.input;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 4:46 2018/11/28 0028
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.api.fullhouse.input.FullHouseDesignPlanAdd;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/11/28 0028PM 4:46
 */
@Data
public class FullHousePlanInput implements Serializable{
    private static final long serialVersionUID = -7119405304770419728L;

    private Integer msgId;

    private String planName;//方案名称

    private Integer planId;//方案名称

    private Integer houseId;//户型ID

    private Integer planType;//3:单空间设计方案 4:全屋设计方案

    private List<PlanRenderSceneInput> planRenderSceneInputList;

    private List<PlanInput> planInputList;
}
