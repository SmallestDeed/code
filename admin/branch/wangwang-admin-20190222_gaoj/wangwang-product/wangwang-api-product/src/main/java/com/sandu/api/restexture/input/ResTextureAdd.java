package com.sandu.api.restexture.input;

import com.sandu.api.product.model.bo.TextureInfo;
import com.sandu.api.product.model.bo.TexturePicInfo;
import com.sandu.api.restexture.model.ResTexture;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResTextureAdd implements Serializable {

    /**
     * 贴图名称
     */
    @ApiModelProperty(value = "贴图名称", required = true)
    @NotEmpty(message = "贴图名称不能为空")
    @Length(max = 50, message = "贴图名称最多{max}字")
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
    @Length(max = 200, message = "描述最长{max}个字")
    private String fileDesc;

    /**
     * 材质(材料)
     */
    @ApiModelProperty(value = "材质(材料)")
    @Length(min = 1, message = "长度最小{min}")
    private String texture;


    /**
     * 材质属性value
     */
    @ApiModelProperty("材质属性")
//    @Min(value = 1, message = "材质属性不合法")
    private Integer textureAttrValue;

    /**
     * 材质缩略图
     */
    @ApiModelProperty("缩略图文件id")
    @Min(value = 1, message = "无效缩略图文件id")
    private Integer thumbnailId;

    @ApiModelProperty(value = "缩略图地址")
    @Length(min = 0, max = 200, message = "缩略图地址长度最小{min} 最大 {max}")
    private String thumbnailPath;

    /**
     * 法线贴图
     */
    @ApiModelProperty("法线贴图文件id")
    @Min(value = 1, message = "法线贴图文件id最小{min}")
    private Integer normalPicId;

    @ApiModelProperty("法线贴图地址")
    @Length(max = 200, message = "法线贴图地址长度最大{max}")
    private String normalPicPath;

    /**
     * 法线参数
     */
    @ApiModelProperty("法线参数")
    @Length(max = 6, message = "法线参数长度最长{max}位数")
    private String normalParam;

    /**
     * 材质球文件Id
     */
    @ApiModelProperty("材质球文件id")
    @Min(value = 1, message = "材质球文件id最小{min}")
    private Integer textureballFileId;

    @ApiModelProperty("材质球文件地址")
    @Length(max = 200, message = "材质球文件地址长度最大{max}")
    private String textureBallPath;

    /**
     * 品牌id
     */
    @ApiModelProperty("品牌id")
    @Min(value = 1, message = "品牌id最小{min}")
    private Integer brandId;

    @ApiModelProperty("材质属性")
    @Size(min = 0, message = "材质属性最小{min}")
    private List<String> textureAttrs;

    @ApiModelProperty("备注")
    @Length(max = 200, message = "备注长度最大{max}")
    private String remark = "";

    @ApiModelProperty("公司id")
    @NotNull(message = "公司不能为空")
    @Min(value = 0, message = "公司id不合法")
    private Integer companyId;

    @ApiModelProperty("材质型号")
    @Size(min = 0, max = 30, message = "材质型号最小{min}")
    private String modelNumber;
    
    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    @Range(min = 1, message = "用户ID最小为{min}")
    private Integer userId;

    private Integer initWithModelId;

    private List<TexturePicInfo> texturePicInfos;
}
