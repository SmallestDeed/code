package com.sandu.api.house.output;

import java.io.Serializable;
import java.util.List;

import com.sandu.api.house.model.DrawResFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *  空间输出类
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2018/1/2
 */
@Data
public class DrawSpaceCommonDataVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("空间id")
    private Long spaceId;

    @ApiModelProperty("空间编码")
    private String spaceCode;

    @ApiModelProperty("空间名称")
    private String spaceName;

    @ApiModelProperty("空间类型")
    private String spaceType;

    @ApiModelProperty("空间功能类型")
    private Integer spaceFunctionId;

    @ApiModelProperty("空间长度")
    private String mainLength;

    @ApiModelProperty("空间宽度")
    private String mainWidth;

    @ApiModelProperty("空间面积")
    private String spaceAreas;

    @ApiModelProperty("空间形状")
    private String spaceShape;

    @ApiModelProperty("空间文件")
    private List<DrawResFile> spaceFile;

    @ApiModelProperty("空间下产品信息")
    private List<DrawProductDataVO> productData;
}