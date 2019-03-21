package com.sandu.api.shop.input;

import com.sandu.api.shop.model.po.ShopPO;
import com.sandu.commons.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询店铺列表条件
 *
 * @auth xiaoxc
 * @data 2018-06-04
 */
@Data
public class CompanyShopQuery extends Mapper implements Serializable {

    @ApiModelProperty(value="店铺编码")
    private String shopCode;

    @ApiModelProperty(value="店铺名称")
    private String shopName;

    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    @ApiModelProperty(value = "区域省编码")
    private String provinceCode;

    @ApiModelProperty(value = "区域市编码")
    private String cityCode;

    @ApiModelProperty(value = "区域区编码")
    private String areaCode;

    @ApiModelProperty(value = "区域街道编码")
    private String streetCode;

    @ApiModelProperty(value = "企业Id")
    private Long companyId;

    @ApiModelProperty(value = "企业类型")
    private String companyType;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "是否拉黑")
    private Integer isBlacklist;



    /**
     * 转换PO对象
     * @param query
     * @return
     */
    public ShopPO getShopPOFromShopQuery(CompanyShopQuery query) {
        ShopPO shopPO = new ShopPO();
        if (query != null) {
            shopPO.setShopCode(query.getShopCode());
            shopPO.setShopName(query.getShopName());
            shopPO.setContactName(query.getContactName());
            shopPO.setContactPhone(query.getContactPhone());
            shopPO.setProvinceCode(query.getProvinceCode());
            shopPO.setCityCode(query.getCityCode());
            shopPO.setAreaCode(query.getAreaCode());
            shopPO.setStreetCode(query.getStreetCode());
            shopPO.setCompanyId(query.getCompanyId());
            shopPO.setCompanyType(query.getCompanyType());
            shopPO.setUserId(query.getUserId());
            shopPO.setIsBlacklist(query.getIsBlacklist());
            shopPO.setStart(query.getStart());
            shopPO.setLimit(query.getLimit());
        }
        return shopPO;
    }
}
