package com.sandu.api.house.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/28
 */
@Data
public class DrawBaseHouseSearch {

    @ApiModelProperty(name = "用户id", required = true)
    private Long userId;
    
    @ApiModelProperty("关键字")
    private String keyword;
    
    /**
     * 1 我的绘制
     * 0 审核列表
     */
    @ApiModelProperty("审核状态")
    private Integer checkStatus;

    @ApiModelProperty("第几页")
    private Integer pageNum;

    @ApiModelProperty("每页显示多少条")
    private Integer pageSize;

    @ApiModelProperty("审核状态")
    private Integer[] checkArgs;

    @ApiModelProperty("城市编码")
    private String areaCode;

    @ApiModelProperty("小区名称")
    private String livingName;

    @ApiModelProperty("小区编码")
    private Long livingId;

    @ApiModelProperty("用于判断户型是否公开")
    private Integer IsPublic;

    @ApiModelProperty("公司ID")
    private Integer companyId;

    /**
     * {@see com.sandu.common.constant.kechuang.PlatformType}
     */
    @ApiModelProperty("平台类型")
    private Integer platformType;
}
