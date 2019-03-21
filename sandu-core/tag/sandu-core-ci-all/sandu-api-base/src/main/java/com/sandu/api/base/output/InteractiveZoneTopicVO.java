package com.sandu.api.base.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 * @ClassName InteractiveZoneTopicVO
 * @date 2019/3/13-15:05$
 */
@Data
public class InteractiveZoneTopicVO implements Serializable {
	private Integer id;

	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("封面图")
	private String coverPicPath;

	@ApiModelProperty("发布时间")
	private Date releaseTime;

	@ApiModelProperty("浏览量")
	private Integer viewCount;

	@ApiModelProperty("虚拟浏览量")
	private Integer viewVirtualCount;

	@ApiModelProperty("评论量")
	private Integer reviewsCount;

	@ApiModelProperty("发布者用户ID")
	private String publishUserName;

	@ApiModelProperty("发布时间")
	private Date publishTime;

	@ApiModelProperty("发布状态")
	private Integer status;

	@ApiModelProperty("排序 ")
	private Integer ordering;

	@ApiModelProperty("几厅 ")
	private Integer livingRoomNum;

	@ApiModelProperty("几室 ")
	private Integer bedroomNum;

	@ApiModelProperty("面积 ")
	private Float houseArea;

	@ApiModelProperty("花费 ")
	private Float houseCost;

	@ApiModelProperty("风格 ")
	private Integer houseStytle;

	@ApiModelProperty("方案ID ")
	private Integer planId;

	@ApiModelProperty("户型ID ")
	private Integer houseId;

	@ApiModelProperty("方案封面 ")
	private Integer planCoverUrl;

	@ApiModelProperty("户型封面 ")
	private Integer houseCoverUrlId;

	@ApiModelProperty("用户头像")
	private String authorPic;
}
