package com.sandu.system.model.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ResTexturePO implements Serializable {

	private static final long serialVersionUID = 6760434429799214324L;

	private Integer id;
    
    /**
     * 文件手动命名
     */
	private String name;
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 编码
	 */
	private String fileCode;
	
	/**
	 * 文件大小
	 */
	private Integer fileSize;
	
	/**
	 * 图片宽
	 */
	private Integer fileWidth;
	
	/**
	 * 图片高
	 */
	private Integer fileHeight;
	
	/**
	 * 关联图片id
	 */
	private Integer picId;
	
	/**
	 * 图片格式
	 */
	private String fileSuffix;
	
	/**
	 * 材质图片路径
	 */
	private String filePath;
	
	/**
	 * 材质描述
	 */
	private String fileDesc;
	
	/**
	 * 风格
	 */
	private String style;
	
	/**
	 * 材质(材料)
	 */
	private String texture;
	
	/**
	 * 系统编码
	 */
	private String sysCode;
	
	/**
	 * 创建者
	 */
	private String creator;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 修改人
	 */
	private String modifier;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * 是否删除
	 */
	private Integer isDeleted;
	
	/**
	 * 字符备用1
	 */
	private String att1;
	
	/**
	 * 字符备用2
	 */
	private String att2;
	
	/**
	 * 整数备用1
	 */
	private Integer numa1;
	
	/**
	 * 整数备用2
	 */
	private Integer numa2;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 颜色
	 */
	private String color;
	
	/**
	 * 光泽度
	 */
	private String gloss;
	
	/**
	 * 工艺
	 */
	private String craft;
	
	/**
	 * 表面效果
	 */
	private String effectSurface;
	
	/**
	 * 材质属性
	 */
	private Integer textureAttrValue;
	
	/**
	 * 铺设方式
	 */
	private String laymodes;
	
	/**
	 * 缩略图
	 */
	private Integer thumbnailId;
	
	/**
	 * 法线贴图
	 */
	private Integer normalPicId;
	
	/**
	 * 法线参数
	 */
	private String normalParam;
	
	/**
	 * 材质球文件Id
	 */
	private Integer textureBallFileId;
	
	/**
	 * 品牌id
	 */
	private Integer brandId;
	
	/**
	 * 材质编号
	 */
	private String textureCode;
	
	/**
	 * android端材质球文件Id
	 */
	private Integer androidTextureBallFileId;
	
	/**
	 * ios端材质球文件Id
	 */
	private Integer iosTextureBallFileId;
	
	/**
	 * 公司id
	 */
	private Integer companyId;
	
	/**
	 * 材质型号
	 */
	private String modelNumber;
	
}
