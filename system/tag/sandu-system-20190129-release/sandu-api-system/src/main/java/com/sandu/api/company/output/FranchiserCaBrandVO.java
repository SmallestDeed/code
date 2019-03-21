package com.sandu.api.company.output;

import com.sandu.api.brand.output.CompanyBrandVO;
import com.sandu.api.category.output.CategoryListVO;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author chenqiang
 * @Description 经销商企业 默认可见产品范围与品牌
 * @Date 2018/6/20 0020 10:03
 * @Modified By
 */
@Data
public class FranchiserCaBrandVO implements Serializable{

    @ApiModelProperty(value = "经销商企业可见产品范围id")
    private String  productVisibilityRange;

    @ApiModelProperty(value = "经销商企业可见产品范围集合")
    private List<CategoryListVO> productVisibilityRangeNameList;

    @ApiModelProperty(value = "经销商企业可见产品范围父级集合")
    private List<CategoryListVO> productVisibilityRangeNameListP;

    @ApiModelProperty(value = "经销商品牌集合")
    private List<CompanyBrandVO> brandList;

    @ApiModelProperty(value = "经销商企业品牌ids")
    private String brandIds;

}
