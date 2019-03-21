package com.sandu.api.act.output;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * registration
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:31
 */
@Data
public class WxActBargainRegDashBoardResultVO implements Serializable {

        
    @ApiModelProperty(value = "参与人数")
    private List<WxActBargainRegCountResultVO> regList;
        
    @ApiModelProperty(value = "砍价成功人数")
    private List<WxActBargainRegCountResultVO> regSuccessList;
    
    @ApiModelProperty(value = "所有参与砍价的人数(包括自己,好友,装进我家)")
    private List<WxActBargainRegCountResultVO> cutList;
    
    
}
