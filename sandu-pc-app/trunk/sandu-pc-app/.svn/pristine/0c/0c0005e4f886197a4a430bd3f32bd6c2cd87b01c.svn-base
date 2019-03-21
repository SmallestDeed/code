package com.sandu.panorama.model.output;

import com.sandu.cityunion.model.UnionGroupDetails;
import com.sandu.product.model.output.BaseCompanyVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenm on 2018/8/1.
 */
@Data
public class UnionGroupVo implements Serializable {

    @ApiModelProperty(value="id",dataType = "int")
    private Integer id;
    @ApiModelProperty(value="联盟门店名称",dataType = "String")
    private String groupName ;
    @ApiModelProperty(value="联盟门店详情")
    private List<UnionGroupDetails> details;


    @ApiModelProperty(value="关联企业信息")
    private List<BaseCompanyVo> companyList;

}
