package com.sandu.api.restexture.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bvvy
 * @date 2018/4/9
 */
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResTexture implements Serializable{
    /** */
    private Long id;

    /** 文件手动命名*/
    private String name;

    /** 文件名*/
    private String fileName;

    /** 类型*/
    private String type;

    /** 编码*/
    private String fileCode;

    /** 文件大小*/
    private Integer fileSize;

    /** 图片宽*/
    private String fileWidth;

    /** 图片高*/
    private String fileHeight;

    /** 关联图片id*/
    private Integer picId;

    /** 图片格式*/
    private String fileSuffix;

    /** 材质图片路径*/
    private String filePath;

    /** 材质描述*/
    private String fileDesc;

    /** 风格*/
    private String style;

    /** 材质(材料)*/
    private String texture;

    /** */
    private String sysCode;

    /** 创建者*/
    private String creator;

    /** 创建时间*/
    private Date gmtCreate;

    /** 修改人*/
    private String modifier;

    /** 修改时间*/
    private Date gmtModified;

    /** 是否删除*/
    private Integer isDeleted;

    /** 字符备用1*/
    private String att1;

    /** 字符备用2*/
    private String att2;

    /** 整数备用1*/
    private Integer numa1;

    /** 整数备用2*/
    private Integer numa2;

    /** 备注*/
    private String remark;

    /** 颜色*/
    private String color;

    /** 光泽度*/
    private String gloss;

    /** 工艺*/
    private String craft;

    /** 表面效果*/
    private String effectSurface;

    /** 材质属性value*/
    private Integer textureAttrValue;

    /** 铺设方法*/
    private String layModes;

    /** 材质缩略图*/
    private Integer thumbnailId;

    /** 法线贴图*/
    private Integer normalPicId;

    /** 法线参数*/
    private String normalParam;

    /** 材质球文件Id*/
    private Integer textureballFileId;

    /** 品牌id*/
    private Integer brandId;

    /** 材质编号*/
    private String textureCode;

    private Integer companyId;
    /**
     * 材质型号
     */
    private String modelNumber;


    private Integer initWithModelId;

}