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
public class BaseHouseQuery {

    @ApiModelProperty("用户id")
    private Long userId;
    
    @ApiModelProperty("关键字")
    private String keyword;

    @ApiModelProperty("第几页")
    private Integer pageNum;

    @ApiModelProperty("每页显示多少条")
    private Integer pageSize;
    
    // 状态
    private Integer[] checkArgs;
}
