package com.sandu.api.base.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 * @ClassName InteractiveZoneTopicUpdate
 * @date 2019/3/13-14:02$
 */
@Data
public class InteractiveZoneTopicUpdate implements Serializable {

	// @NotNull
	@ApiModelProperty(" 主题ID ")
	private Long id;

	@ApiModelProperty(" 标题")
	// @Length(max = 30, message = "标题不能超过30个字")
	private String title;

	private String content;

	private String picIds;

	@ApiModelProperty(" 版块(sys_dictionary的blockType)")
	private Integer blockType;

	@ApiModelProperty(" 封面图片ID")
	private String coverPicId;

	@ApiModelProperty(" 大咖分享类型(0-博文;1-案例)")
	private Integer shareType;

	@ApiModelProperty(" 客厅数量(发布案例用)")
	private Integer livingRoomNum;

	@ApiModelProperty(" 卧室数量(发布案例用)")
	private Integer bedroomNum;

	@ApiModelProperty(" 面积(发布案例用)")
	private Float houseArea;

	@ApiModelProperty(" 花费(发布案例用)")
	private Float houseCost;

	@ApiModelProperty(" 风格(发布案例用)")
	private Integer houseStytle;

	@ApiModelProperty(" 方案ID(设计改造用)")
	private Integer planId;

	@ApiModelProperty(" 户型ID(设计改造用)")
	private Integer houseId;

	private Integer planCoverId;

	private Integer houseCoverId;

	private Integer articleSource;

	private Integer status;

	private Integer articleId;

	private Integer projectCaseId;

	private Integer isOld;

	private Integer shopId;

	private Integer isTop;

	private Integer ordering;

	private Integer publishUserId;

	private Date publishTime;

	private Integer dataSource;

	private String author;

	private Integer isDeleted;

	private String jsonContent;
	/*******************不同于原model的字段*******************/
	@ApiModelProperty("虚拟浏览量")
	private Integer virtualViewNum;

	@ApiModelProperty("虚拟收藏量")
	private Integer virtualFavoriteNum;
}
