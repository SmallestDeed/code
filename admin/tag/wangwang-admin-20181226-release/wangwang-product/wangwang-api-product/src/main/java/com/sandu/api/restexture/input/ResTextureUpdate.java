package com.sandu.api.restexture.input;


import com.sandu.api.product.model.bo.TexturePicInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author by bvvy
 */
@Data
public class ResTextureUpdate implements Serializable {
    @ApiModelProperty(value = "贴图Id")
    @NotNull(message = "贴图id不能为空")
    private Integer textureId;
    /**
     * 贴图名称
     */
    @ApiModelProperty(value = "贴图名称", required = true)
    @NotNull(message = "不能为空")
    @Length(max = 50, message = "贴图名称最长{max}")
    private String textureName;


    /**
     * 图片宽
     */
    @ApiModelProperty("长度")
    @Range(min = 0, max = 1000000, message = "长度限{min}-{max}")
    @Pattern(regexp = "^(0|\\+?[1-9][0-9]*)$", message = "长度只能输入正整数")
    private String fileLength;

    /**
     * 图片高
     */
    @ApiModelProperty("宽度")
    @Range(min = 0, max = 1000000, message = "宽度限{min}-{max}")
    @Pattern(regexp = "^(0|\\+?[1-9][0-9]*)$", message = "宽度只能输入正整数")
    private String fileWidth;

    /**
     * 材质描述
     */
    @ApiModelProperty("备注")
    @Length(max = 200, message = "备注最长{max}")
    private String fileDesc="";

    /**
     * 材质(材料)
     */
    @ApiModelProperty(value = "材质(材料)", required = true)
    @Length(max = 30, message = "材质(材料)最长{max}")
    private String texture;

    /**
     * 材质属性value
     */
    @ApiModelProperty("材质属性")
//    @Min(value = 1, message = "材质属性最小{min}")
    @Builder.Default
    private Integer textureAttrValue;

    /**
     * 材质缩略图
     */
    @ApiModelProperty("缩略图文件id")
    @Min(value = 1, message = "缩略图文件id不合法")
    private Integer thumbnailId;

    @ApiModelProperty(value = "缩略图地址", required = true)
    @Length(max = 200, message = "缩略图地址最长{max}")
    private String thumbnailPath;

    /**
     * 法线贴图
     */
    @ApiModelProperty("法线贴图文件id")
    @Min(value = 1, message = "法线贴图文件id最小{min}")
    private Integer normalPicId;

    @ApiModelProperty("法线贴图地址")
    @Length(max = 200, message = "法线贴图地址最长{max}")
    private String normalPicPath;

    /**
     * 法线参数
     */
    @ApiModelProperty("法线参数")
    @Length(max = 6, message = "法线参数最长{max}位")
    private String normalParam;

    /**
     * 材质球文件Id
     */
    @ApiModelProperty("材质球文件id")
    @Min(value = 1, message = "材质球文件id最小{min}")
    private Integer textureballFileId;

    @ApiModelProperty("材质球文件地址")
    @Length(max = 200, message = "材质球文件地址最长{max}")
    private String textureBallPath;

    /**
     * 品牌id
     */
    @ApiModelProperty("品牌id")
    @Min(value = 1, message = "品牌id最小{min}")
    private Integer brandId;

    @ApiModelProperty("备注d")
    @Length(max = 200, message = "备注d最长{max}")
    private String remark;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "用户id不合法")
    private Integer userId;

    @ApiModelProperty("公司id")
    @NotNull(message = "公司不能为空")
    @Min(value = 0, message = "公司id不合法")
    private Integer companyId;

    @ApiModelProperty("材质型号")
    @Size(min = 0, max = 30, message = "材质型号最小{min}")
    private String modelNumber="";

    private List<TexturePicInfo> texturePicInfos;

}
