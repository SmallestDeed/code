package com.sandu.api.shop.input;

import com.sandu.api.shop.model.CompanyShop;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 新增企业店铺
 *
 * @auth xiaoxc
 * @data 2018-06-04
 */
@Data
public class CompanyShopAdd implements Serializable {

    private static final long serialVersionUID = 6569747046941898171L;

    @ApiModelProperty(value = "店铺名称", required = true)
    @NotBlank(message = "店铺名称不能为空")
    @Size(min = 1, max = 50, message = "店铺名称长度度限{min}-{max}个字符")
    private String shopName;

    @ApiModelProperty(value = "联系人姓名", required = true)
    @NotBlank(message = "联系人姓名不能为空")
    @Size(min = 1, max = 50, message = "联系人姓名长度度限{min}-{max}个字符")
    private String contactName;

    @ApiModelProperty(value = "联系人电话", required = true)
    @NotBlank(message = "联系人电话不能为空")
    @Pattern(regexp = "^\\b800-[0-9]{6,7}\\b|\\b400-[0-9]{6,7}\\b|\\b((0[0-9]{2,3}-)([1-9][0-9]{6,7}))\\b|\\b(((13[0-9])|(14[5|7])|(15([0-9]))|(17[0-3,5-8])|(18[0-9])|166|198|199)\\d{8}\\b)$",message = "号码格式错误")
    private String contactPhone;

    @ApiModelProperty(value = "区域省编码", required = true)
    @NotBlank(message = "区域信息不能为空")
    private String provinceCode;

    @ApiModelProperty(value = "区域市编码", required = true)
    @NotBlank(message = "请完整选择店铺区域信息")
    private String cityCode;

    @ApiModelProperty(value = "区域区编码", required = true)
    @NotBlank(message = "请完整选择店铺区域信息")
    private String areaCode;

    @ApiModelProperty(value = "区域街道编码")
    private String streetCode;

    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "店铺详细地址不能为空")
    @Size(min = 1, max = 100, message = "banner名称长度度限{min}-{max}个字符")
    private String shopAddress;

    @ApiModelProperty(value = "店铺logo图片Id")
    private Integer logoPicId;

    @ApiModelProperty(value = "店铺封面资源Ids")
    private String coverResIds;

    @ApiModelProperty(value = "店铺封面资源类型1:图片列表,2:全景图,3:视频")
    private Integer coverResType;

    @ApiModelProperty(value = "富文本店铺介绍内容")
    private String shopIntroduced;

    @ApiModelProperty(value = "富文本店铺介绍图片ids")
    private String introducedPicIds;

    @ApiModelProperty(value = "分类ids 设计师-擅长风格，施工单位-施工类型，家居建材-产品分类等")
    private String categoryIds;

    @ApiModelProperty(value = "发布平台类型 1:小程序2:选装网3:同城联盟")
    private String platformValues;

    @ApiModelProperty(value = "企业ID", required = true)
    @NotNull(message = "企业信息不能为空")
    private Long companyId;

    //add by wangHaiLin
    @ApiModelProperty(value = "经度")
    private Double longitude;
    @ApiModelProperty(value = "纬度")
    private Double latitude;
    @ApiModelProperty(value = "设计最低价格")
    private Double designFeeStarting;
    @ApiModelProperty(value = "设计最高价格")
    private Double designFeeEnding;

    @ApiModelProperty(value = "装修报价区间")
    private Double decorationPriceStart;
    @ApiModelProperty(value = "装修报价区间")
    private Double decorationPriceEnd;
    @ApiModelProperty("装修方式:0.半包,1.全包")
    private Integer decorationType;
    @ApiModelProperty("从业年限")
    private Integer workingYears;
    @ApiModelProperty("店铺类型：1.企业店铺，2.个人店铺")
    private Integer shopType;

    /**
     * 转换model对象
     *
     * @return
     */
    public CompanyShop getCompanyShop() {
        CompanyShop companyShop = new CompanyShop();
        companyShop.setShopName(this.shopName);
        companyShop.setContactName(this.contactName);
        companyShop.setContactPhone(this.contactPhone);
        companyShop.setProvinceCode(this.provinceCode);
        companyShop.setCityCode(this.cityCode);
        companyShop.setAreaCode(this.areaCode);
        companyShop.setStreetCode(this.streetCode);
        companyShop.setShopAddress(this.shopAddress);
        companyShop.setCategoryIds(this.categoryIds);
        companyShop.setReleasePlatformValues(this.platformValues);
        companyShop.setLogoPicId(this.logoPicId);
        companyShop.setCoverResIds(this.coverResIds);
        companyShop.setCoverResType(this.coverResType);
        companyShop.setShopIntroduced(this.shopIntroduced);
        companyShop.setIntroducedPicIds(this.introducedPicIds);
        companyShop.setCompanyId(this.companyId.intValue());
        companyShop.setLongitude(this.longitude);
        companyShop.setLatitude(this.latitude);
        companyShop.setShopType(this.shopType);
        if (null!=this.designFeeStarting){
            companyShop.setDesignFeeStarting(this.designFeeStarting);
        }
        if (null!=this.designFeeEnding){
            companyShop.setDesignFeeEnding(this.designFeeEnding);
        }
        if (workingYears != null){
            companyShop.setWorkingYears(this.workingYears);
        }
        if (decorationPriceStart != null){
            companyShop.setDecorationPriceStart(decorationPriceStart);
        }
        if (decorationPriceEnd != null){
            companyShop.setDecorationPriceEnd(decorationPriceEnd);
        }
        if (decorationType != null){
            companyShop.setDecorationType(this.decorationType);
        }
        return companyShop;
    }

}
