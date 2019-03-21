package com.sandu.im.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DesignPlanInfo implements Serializable {

    private Integer id; //任务ID

    private Integer taskId; //任务ID

    private Integer operationSource; //0:我的设计,1:我的推荐方案

    private Integer operationUserId; //操作用户ID

    private Integer renderPic;//0,未渲染 1渲染中 2渲染成功 3渲染失败'

    private Integer render720;//0,未渲染 1渲染中 2渲染成功 3渲染失败'

    private Integer renderN720;//0,未渲染 1渲染中 2渲染成功 3渲染失败'

    private Integer renderVideo;//0,未渲染 1渲染中 2渲染成功 3渲染失败'

    private String  failReason; //失败原因

    private String creator; //创建者

    private Date gmtCreate;// 创建时间

    private Integer isDeleted;//是否删除

    private String designCode;//方案编码

    private String designName;//方案名称

    private String templateCode;//样板房编码

    private String newDesignCode;//新方案编码

    private Integer taskSource;//任务来源

    private String hostIp;//主机IP

    private String renderTypesStr;//'1、照片级 2、720 3、N720 4、video'

    private Integer taskType;//'0自动渲染 1替换渲染'

    private String renderTimeConsuming;//渲染耗时

    private Integer renderState;//渲染状态

    private Integer businessId;//新方案ID

    private String failType;//失败原因

    private Integer isValid;//是否有效

    private Integer platformId;//平台ID

    private Integer templateId;//样板房ID

    private Integer planHouseType;//方案空间类型（1：单空间方案，2：全屋方案）

    private Integer newFullHousePlanId;//装进我家后新的全屋方案id

    private String vrResourceUuid; // 全屋方案720UUID

    private Integer mainTaskId; //全屋方案主任务ID

    private String planStyleName; //方案风格名称

    private String planPicPath;//方案图片

    private Integer planRenderType;//方案渲染来源(1：单空间方案，2：全屋方案无户型 3:全屋方案有户型)

    private Integer houseId; //户型ID

    private Integer livingId;//小区ID

    private Integer isSuccess; //渲染状态

    private Integer state; //任务状态

    private String spaceArea;

    private Integer resRenderPicId;

    private String spaceTypeName;
}
