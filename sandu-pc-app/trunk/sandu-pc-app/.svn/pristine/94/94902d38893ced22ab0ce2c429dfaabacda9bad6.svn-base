package com.sandu.panorama.model.input;

import com.sandu.cityunion.model.UnionGroupDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by chenm on 2018/7/30.
 */
@Data
public class UnionGroupAdd implements Serializable {

    @ApiModelProperty(value="联盟门店名称",dataType = "String",required = true)
    @NotBlank(message = "联盟门店名称不能为空")
    private String groupName;
    @ApiModelProperty(value="自定义联盟门店")
    List<UnionGroupDetails> groupDetailList ;

    @ApiModelProperty(value="关联企业Id集合",dataType = "String",required = true)
  /*  @NotBlank(message = "关联企业不能为空")*/
    private String companyIds;
}
