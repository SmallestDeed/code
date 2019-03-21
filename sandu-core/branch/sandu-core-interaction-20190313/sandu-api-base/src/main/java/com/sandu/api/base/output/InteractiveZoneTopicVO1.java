package com.sandu.api.base.output;

import com.sandu.api.base.model.InteractiveZoneTopic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class InteractiveZoneTopicVO1 extends InteractiveZoneTopic implements Serializable {


    @ApiModelProperty("发布者用户昵称")
    private String publishUserName;

    @ApiModelProperty("评论量")
    private Integer reviewsCount;




    @ApiModelProperty("封面图片")
    private String coverPicPath;

    @ApiModelProperty("用户头像")
    private String authorPic;


    @ApiModelProperty("发布时间")
    private Date releaseTime;

    @ApiModelProperty("浏览量")
    private Integer viewCount;

    @ApiModelProperty("虚拟浏览量")
    private Integer viewVirtualCount;


//    @ApiModelProperty("大咖分享类型")
//    private Integer shareType;
//
//    @ApiModelProperty("文章来源")
//    private Integer articleSource;
//
    @ApiModelProperty("设计改造类型")
    private String designType;





}