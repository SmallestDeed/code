package com.sandu.decorate.input;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-08-08 15:37
 */
@Data
public class PlanDecoratePriceUpdate implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer id;
        
        
    @ApiModelProperty(value = "报价类型(对应数据字典)")
    private Integer decoratePriceType;
        
        
    @ApiModelProperty(value = "报价区间(对应数据字典)")
    private Integer decoratePriceRange;
        
        
    @ApiModelProperty(value = "具体报价")
    private Integer decoratePrice;
        

        
    @ApiModelProperty(value = "修改人")
    private String modifier;
        
        
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
        
        
    @ApiModelProperty(value = "是否删除（0:否，1:是）")
    private Integer isDeleted;
        
        
    @ApiModelProperty(value = "备注")
    private String remark;
        
    
}
