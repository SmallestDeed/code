package com.sandu.api.shop.output;

import com.sandu.api.shop.model.CompanyShop;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店铺管理列表展示Vo界面
 *
 * @auth xiaoxc
 * @data 2018-06-04
 */
@Data
public class CompanyShopVO implements Serializable {

    @ApiModelProperty(value="店铺Id")
    private Integer shopId;

    @ApiModelProperty(value="店铺编码")
    private String shopCode;

    @ApiModelProperty(value="店铺名称")
    private String shopName;

    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    @ApiModelProperty(value = "区域区名称")
    private String areaName;

    @ApiModelProperty(value = "发布平台名称")
    private String releasePlatformName;

    @ApiModelProperty(value = "发布状态 1:发布，0:未发布")
    private Integer displayStatus;

    @ApiModelProperty(value = "是否被拉黑 1:被拉黑，0:未被拉黑")
    private Integer isBlacklist;

    @ApiModelProperty(value = "店铺类型：1.企业店铺，2.个人店铺")
    private Integer shopType;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建人账号")
    private String creatorAccount;

    @ApiModelProperty(value = "创建人手机")
    private String creatorPhone;

    @ApiModelProperty(value = "店铺创建者Id")
    private Integer userId;

    @ApiModelProperty(value = "发布状态 1:发布，0:取消发布(未发布)")
    private Integer releaseStatus;

    @ApiModelProperty("服务区域")
    private String serviceArea;
    @ApiModelProperty("企业店铺装修方式:1.半包,2.全包,3.包软装,4.清水")
    private String businessShopDecorationType;
    @ApiModelProperty("从业年限")
    private Integer workingYears;

    /**
     * 转换vo对象
     *
     * @param shop
     * @return
     */
    public CompanyShopVO getCompanyShopVOFromCompanyShop(CompanyShop shop) {
        CompanyShopVO shopVO = new CompanyShopVO();
        if (shop != null) {
            shopVO.setShopId(shop.getId());
            shopVO.setShopCode(shop.getShopCode());
            shopVO.setShopName(shop.getShopName());
            shopVO.setContactName(shop.getContactName());
            shopVO.setContactPhone(shop.getContactPhone());
            shopVO.setDisplayStatus(shop.getDisplayStatus());
            shopVO.setIsBlacklist(shop.getIsBlacklist());
			shopVO.setShopType(shop.getShopType());
			shopVO.setCreatorAccount(shop.getCreatorAccount());
            shopVO.setCreatorPhone(shop.getCreatorPhone());
            shopVO.setCreator(shop.getCreator());
            shopVO.setUserId(shop.getUserId());
            shopVO.setServiceArea(shop.getServiceArea());
            shopVO.setBusinessShopDecorationType(shop.getBusinessShopDecorationType());
            shopVO.setWorkingYears(shop.getWorkingYears());
        }
        return shopVO;
    }

}
