package com.sandu.api.restexture.input;

import com.sandu.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class ResTextureQuery extends BaseQuery implements Serializable {


    @ApiModelProperty("长度")
    @Range(min = 0, max = 100000, message = "长度最小{min},最大{max}")
    private int length;

    @ApiModelProperty("宽度")
    @Range(min = 0, max = 100000, message = "宽度度最小{min},最大{max}")
    private int width;

    @ApiModelProperty("材质名称")
    @Length(min = 0, max = 30, message = "材质名称最长{max},最小{min}")
    private String textureName;

    @ApiModelProperty("材质编码")
    @Length(min = 0, max = 30, message = "材质编码最长{max},最小{min}")
    private String textureCode;
    /**
     * 材质(材料)
     */
    @ApiModelProperty("材质(材料)")
    @Range(min = 0, max = 100, message = "材质(材料)最长{max},最小{min}")
    private Integer texture;

    /**
     * 材质属性value
     */
    @ApiModelProperty("材质属性")
    @Length(min = 1, max = 30, message = "材质属性最长{max},最小{min}")
    private Integer textureAttr;

    @ApiModelProperty("公司id")
    @NotNull(message = "公司id不能为空")
    @Min(value = 1, message = "公司id最小 {min}")
    private Integer companyId;

    @ApiModelProperty("转换状态: ING 转换中,SUCCESS 转换成功，FAIL 转换失败")
    @Length(max = 30, message = "转换状态长度不能超过{max}")
    private String transStatus;

    @ApiModelProperty("材质类型，材质球:ball/材质:texture")
    @Length(max = 30, message = "转换状态长度不能超过{max}")
    private String textureType;

    @ApiModelProperty("材质型号")
    @Size(min = 0, max = 30, message = "材质型号最小{min}")
    private String modelNumber;

    @ApiModelProperty("插叙方式，只显示转换成功的材质/贴图:success ; 显示所有:不传即可")
    private String queryType;

    @ApiModelProperty("当前材质所属模型ID")
    private Integer curModelId = 0;
}
