package com.sandu.util.excel;

import lombok.Data;

import java.io.Serializable;

@Data
public class TextureImportBean implements Serializable {
    @Row("序号")
    private Integer id;

    @Row("*长度（cm）")
    private Integer width;

    @Row(value = "*材质贴图名称", maxLength = 50)
    private String name;

    @Row("*宽度（cm）")
    private Integer length;

    @Row(value = "*贴图图片", picFlag = true)
    private String texturePicPath;

    @Row(value = "材质型号", maxLength = 50)
    private String textureNumber;

    @Row("材质品牌")
    private String brandName;

    @Row("材质（材料）")
    private String texture;

    @Row("材质表面属性")
    private String textureAttrValue;

    @Row(value = "法线贴图", picFlag = true)
    private String textureParamPicPath;

    @Row("法线参数")
    private Double textureParamNum;

    @Row(value = "备注", maxLength = 200)
    private String remark;
}
