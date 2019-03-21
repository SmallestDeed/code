package com.sandu.api.designplan.output;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DesignPlanRenderSceneVo implements Serializable {

   private static final long serialVersionUID = 6782232776355230318L;
   private Integer fullHousePlanId; //所属全屋方案ID
   private String  fullHousePlanName;//所属全屋方案名称
   private List<SinglePlanRenderSceneVo> singlePlanRenderSceneList;//单空间效果图方案列表
   private Integer msgId;
}